package com.mobile.test

import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import com.mobile.test.api.*
import com.mobile.test.databinding.FragmentResetPasswordBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResetPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResetPasswordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _binding: FragmentResetPasswordBinding? = null
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
        _binding = FragmentResetPasswordBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendRecoveryLink.isEnabled = false

        _binding?.sendRecoveryLink?.setOnClickListener{
            val email = binding.email.text.toString()

            if (!email.isEmpty()) {
                sessionManager = SessionManager.getInstance(requireContext())
                RetrofitClient.service.reset_password(ResetPasswordRequest(email))
                    .enqueue(object: Callback<String> {
                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            val toast = Toast.makeText(context, getString(R.string.recovery_email_sent, email), Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                            toast.show()
                            var imm: InputMethodManager = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(view.windowToken,0)
                            findNavController().navigate(R.id.action_resetPasswordFragment_to_loginFragment)
                        }
                        override fun onFailure(call: Call<String>, error: Throwable) {
                            val toast = Toast.makeText(context, resources.getString(R.string.error_occurred), Toast.LENGTH_LONG)
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, -150);
                            toast.show()
                        }
                    })
            }
        }

        binding.email.doAfterTextChanged {
            checkRequiredFields()
        }
    }

    private fun checkRequiredFields() {
        binding.sendRecoveryLink.isEnabled =
            binding.email.text.toString().isNotEmpty()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ResetPasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResetPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}