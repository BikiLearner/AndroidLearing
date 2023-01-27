package com.thul.samplebiometric

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.thul.biometriclibrary.BiometricCallback
import com.thul.biometriclibrary.BiometricManager

class MainActivity : AppCompatActivity(), BiometricCallback {

    var tv_fingerprint: TextView? = null
    var mBiometricManager: BiometricManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_fingerprint = findViewById(R.id.tv_fingerprint)

        tv_fingerprint?.setOnClickListener {
            mBiometricManager = BiometricManager.BiometricBuilder(this@MainActivity)
                .setTitle(resources.getString(R.string.biometric_title))
                .setScreen(resources.getString(R.string.biometric_screen_center)) // fullscreen || bottom || center
//                  .setTitleBarText(false) // set true if titlebar recquired
                .setSubtitle(resources.getString(R.string.biometric_subtitle))
                .setDescription(resources.getString(R.string.biometric_description))
                .setNegativeButtonText(resources.getString(R.string.cancel_button))
                .build()

            mBiometricManager!!.authenticate(this@MainActivity)
        }

    }

    override fun onSdkVersionNotSupported() {
        Toast.makeText(applicationContext, getString(R.string.biometric_sdk_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotSupported() {
        Toast.makeText(applicationContext, getString(R.string.biometric_hardware_not_supported), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationNotAvailable() {
        Toast.makeText(applicationContext, getString(R.string.biometric_fingerprint_not_available), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(applicationContext, getString(R.string.biometric_permission_not_granted), Toast.LENGTH_LONG).show()
    }

    override fun onBiometricAuthenticationInternalError(error: String?) {
        Toast.makeText(applicationContext, error, Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationFailed() {
                Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationCancelled() {
        Toast.makeText(applicationContext, getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show()
        mBiometricManager!!.cancelAuthentication()
    }

    override fun onAuthenticationSuccessful() {
        Toast.makeText(applicationContext, getString(R.string.biometric_success), Toast.LENGTH_LONG).show()
    }

    override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
        Toast.makeText(applicationContext, errString, Toast.LENGTH_LONG).show()
    }
}
