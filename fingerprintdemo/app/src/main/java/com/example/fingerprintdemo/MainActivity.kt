package com.example.fingerprintdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.fingerprintdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    private lateinit var biometricPrompt:BiometricPrompt
    private lateinit var promptInfo:BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val executor=ContextCompat.getMainExecutor(this)

        biometricPrompt=
            BiometricPrompt(this@MainActivity,executor,object : BiometricPrompt.AuthenticationCallback(){
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(this@MainActivity,"Setup fingerPrint",Toast.LENGTH_LONG).show()
                    binding.mainLayout.visibility= View.VISIBLE
                }

            })


        promptInfo=BiometricPrompt.PromptInfo.Builder().setTitle("To access")
            .setDescription("Practicing the biometric in app")
            .setNegativeButtonText("Use account password")
            .setAllowedAuthenticators(BIOMETRIC_STRONG or BIOMETRIC_WEAK)
            .setSubtitle("Use your fingers print")
            .build()
if(biometricAvailable()) {
    biometricPrompt.authenticate(promptInfo)
}


    }
    private fun biometricAvailable():Boolean{
       val biometricManager=BiometricManager.from(this)//to check if i had biomatirc or not

        return when(biometricManager.canAuthenticate(BIOMETRIC_STRONG or BIOMETRIC_WEAK)){
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE->{
                Toast.makeText(this,"Device do not have fingerprint",Toast.LENGTH_LONG).show()
                false
            }

            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->{
                Toast.makeText(this,"Device Not working",Toast.LENGTH_LONG).show()
                false
            }
            BiometricManager.BIOMETRIC_SUCCESS->{
                Toast.makeText(this,"Setup fingerPrint",Toast.LENGTH_LONG).show()
                true
            }
            else -> {false}
        }

    }
}