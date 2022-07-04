package com.mobile.test

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.mobile.test.api.*
import com.mobile.test.databinding.FragmentLoginBinding
import com.mobile.test.databinding.FragmentSignupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [signupFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class signupFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

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
        _binding = FragmentSignupBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding?.login?.setOnClickListener{
            findNavController().navigate(R.id.action_signupFragment_to_loginFragment)
        }

        _binding?.signUpButton?.setOnClickListener {
            val name = _binding!!.name.text.toString()
            val lastName = binding.lastName.text.toString()
            val email = binding.email.text.toString().trim()
            val password = binding.password.text.toString().trim()

            when {
                validateFields() -> {
                    sessionManager = SessionManager.getInstance(requireContext())
                    RetrofitClient.service.signup(SignupRequest(name, lastName, email, password))
                        .enqueue(object : Callback<String> {
                            override fun onResponse(
                                call: Call<String>,
                                response: Response<String>
                            ) {
                                if (response.isSuccessful) {
                                    val toast = Toast.makeText(
                                        context,
                                        resources.getString(R.string.account_created),
                                        Toast.LENGTH_LONG
                                    )
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                                    toast.show()
                                    val action = R.id.action_signupFragment_to_loginFragment
                                    findNavController().navigate(action)
                                } else {
                                    val toast = Toast.makeText(
                                        context,
                                        resources.getString(R.string.error_occurred),
                                        Toast.LENGTH_LONG
                                    )
                                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                                    toast.show()
                                }
                            }

                            override fun onFailure(
                                call: Call<String>,
                                error: Throwable
                            ) {
                                val toast = Toast.makeText(
                                    context,
                                    resources.getString(R.string.error_occurred),
                                    Toast.LENGTH_LONG
                                )
                                toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                                toast.show()
                            }
                        })
                }
            }
        }
    }

    private fun validateFields(): Boolean {
        val name = _binding!!.name.text.toString()
        val lastName = binding.lastName.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val confirmPassword = binding.confirmPassword.text.toString()

        if (name.isEmpty() or lastName.isEmpty() or email.isEmpty() or password.isEmpty() or confirmPassword.isEmpty()) {
            val toast =
                Toast.makeText(context, getString(R.string.fill_fields), Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 190);
            toast.show()
            return false
        } else {
            if (confirmPassword != password) {
                val toast =
                    Toast.makeText(context, getString(R.string.fill_fields), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 190);
                toast.show()
                return false
            }
        }
        return true
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment signupFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            signupFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}