package com.mobile.test

import android.R.attr.name
import android.os.Bundle
import android.text.TextWatcher
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.test.api.RetrofitClient
import com.mobile.test.databinding.FragmentLoginBinding
import com.mobile.test.model.LoginRequest
import com.mobile.test.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
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
                                    val bundle = bundleOf("Token" to response.body()?.token)
                                    val action = R.id.action_loginFragment_to_homeFragment
                                    findNavController().navigate(action, bundle)
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
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}