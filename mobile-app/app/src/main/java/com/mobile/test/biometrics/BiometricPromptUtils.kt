package com.mobile.test.biometrics

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

// Since we are using the same methods in more than one Activity, better give them their own file.
object BiometricPromptUtils {
    private const val TAG = "BiometricPromptUtils"
    fun createBiometricPrompt(
        fragment: Fragment,
        processSuccess: (BiometricPrompt.AuthenticationResult) -> Unit
    ): BiometricPrompt {
        val executor = ContextCompat.getMainExecutor(fragment.requireContext())

        val callback = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {

            override fun onAuthenticationError(errCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errCode, errString)
                Log.d(TAG, "errCode is $errCode and errString is: $errString")
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                processSuccess(result)
            }
        }
        return BiometricPrompt(fragment, executor, callback)
    }

    fun createPromptInfo(): BiometricPrompt.PromptInfo =
        BiometricPrompt.PromptInfo.Builder().apply {
            setTitle("CryptoLearn")
            setSubtitle("Autenticación biométrica")
            setDescription("Por favor, autenticate para iniciar sesión")
            setConfirmationRequired(false)
            setNegativeButtonText("Cancelar")
            // TODO: No supe como hacer para acceder a resources desde esta clase q esta fuera de un fragment/activity
//            setTitle(getString(R.string.prompt_info_title))
//            setSubtitle(getString(R.string.prompt_info_subtitle))
//            setDescription(getString(R.string.prompt_info_description))
//            setConfirmationRequired(false)
//            setNegativeButtonText(getString(R.string.prompt_info_use_app_password))
        }.build()
}