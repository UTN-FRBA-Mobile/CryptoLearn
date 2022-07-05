package com.mobile.test

import android.app.AlertDialog
import android.content.Context
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.test.api.RetrofitClient
import com.mobile.test.api.*
import com.mobile.test.biometrics.*
import com.mobile.test.databinding.FragmentLoginBinding
import com.mobile.test.api.LoginRequest
import com.mobile.test.api.LoginResponse
import com.mobile.test.api.SessionManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private val TAG = "EnableBiometricLogin"
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var biometricPrompt: BiometricPrompt
    private val cryptographyManager = CryptographyManager()
    private val ciphertextWrapper
        get() = cryptographyManager.getCiphertextWrapperFromSharedPrefs(
            requireContext(),
            BIOMETRICS_SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            CIPHERTEXT_WRAPPER_KEY
        )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val canAuthenticate = BiometricManager.from(requireContext()).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            if (ciphertextWrapper != null) {
                showBiometricPromptForDecryption()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        val canAuthenticate = BiometricManager.from(requireContext()).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            if (ciphertextWrapper != null) {
                showBiometricPromptForDecryption()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.isEnabled = false

        _binding?.loginButton?.setOnClickListener{
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            when{
                email.isEmpty() or password.isEmpty() -> {
                    val toast = Toast.makeText(context, "Llená los  campos mi rey", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 190);
                    toast.show()
                }
                else ->{
                    RetrofitClient.service.login(LoginRequest(email, password))
                        .enqueue(object: Callback<LoginResponse> {
                            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                                if (response.isSuccessful) {
                                    var imm: InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                    imm.hideSoftInputFromWindow(view.windowToken,0)
                                    SessionManager.getInstance(requireContext()).saveAuthToken(response.body()?.token!!)
                                    val canAuthenticate = BiometricManager.from(requireContext()).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                                    if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
                                        if (ciphertextWrapper == null) {
                                            showActivateBiometricsDialog()
                                        }
                                    } else {
                                        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
                                    }
                                } else {
                                    val toast = Toast.makeText(context, resources.getString(R.string.bad_login), Toast.LENGTH_LONG)
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                                    toast.show()
                                }
                            }
                            override fun onFailure(call: Call<LoginResponse>, error: Throwable) {
                                val toast = Toast.makeText(context, resources.getString(R.string.error_occurred), Toast.LENGTH_LONG)
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                                toast.show()
                            }
                        })
                }
            }
        }

        binding.email.doAfterTextChanged {
            checkRequiredFields()
        }

        binding.password.doAfterTextChanged {
            checkRequiredFields()
        }

        _binding?.forgotPassword?.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment)
        }

        _binding?.signUp?.setOnClickListener{
            findNavController().navigate(R.id.action_loginFragment_to_signupFragment)
        }
    }

    private fun checkRequiredFields() {
        binding.loginButton.isEnabled =
            binding.email.text.toString().isNotEmpty() && binding.password.text.toString().isNotEmpty()
    }

    private fun showActivateBiometricsDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle("Iniciar sesion con huella")
            .setMessage("¿Quiere activar el inicio de sesion con huella?")
            .setPositiveButton("Si!") { _, _ ->
                showBiometricPromptForEncryption()
            }
            .setNegativeButton("No, gracias"){ _, _ ->
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
            }
            .show()
    }

    private fun showBiometricPromptForEncryption() {
        val canAuthenticate = BiometricManager.from(requireContext()).canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
        if (canAuthenticate == BiometricManager.BIOMETRIC_SUCCESS) {
            val secretKeyName = getString(R.string.secret_key_name)
            val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
            val biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(this, ::encryptAndStoreServerToken)
            val promptInfo = BiometricPromptUtils.createPromptInfo()
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

    private fun encryptAndStoreServerToken(authResult: BiometricPrompt.AuthenticationResult) {
        authResult.cryptoObject?.cipher?.apply {
            SessionManager.getInstance(requireContext()).fetchAuthToken().let { token ->
                val encryptedServerTokenWrapper = cryptographyManager.encryptData(token!!, this)
                cryptographyManager.persistCiphertextWrapperToSharedPrefs(
                    encryptedServerTokenWrapper,
                    requireContext(),
                    BIOMETRICS_SHARED_PREFS_FILENAME,
                    Context.MODE_PRIVATE,
                    CIPHERTEXT_WRAPPER_KEY
                )
            }
        }
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun showBiometricPromptForDecryption() {
        ciphertextWrapper?.let { textWrapper ->
            val secretKeyName = getString(R.string.secret_key_name)
            val cipher = cryptographyManager.getInitializedCipherForDecryption(
                secretKeyName, textWrapper.initializationVector
            )
            biometricPrompt =
                BiometricPromptUtils.createBiometricPrompt(
                    this,
                    ::decryptServerTokenFromStorage
                )
            val promptInfo = BiometricPromptUtils.createPromptInfo()
            biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
        }
    }

    private fun decryptServerTokenFromStorage(authResult: BiometricPrompt.AuthenticationResult) {
        ciphertextWrapper?.let { textWrapper ->
            authResult.cryptoObject?.cipher?.let {
                val plaintextToken =
                    cryptographyManager.decryptData(textWrapper.ciphertext, it)
                SessionManager.getInstance(requireContext()).saveAuthToken(plaintextToken)
            }
        }
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() = LoginFragment()
    }
}