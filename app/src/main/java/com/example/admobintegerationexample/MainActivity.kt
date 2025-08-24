package com.example.admobintegerationexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faraz.admobhelper.ConsentManager

class MainActivity : AppCompatActivity(), ConsentManager.ConsentCallback {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun OnShowConsentForm(view: View) {
        val consentManager = ConsentManager()
        consentManager.setUserConsent(this)
    }

    override fun onConsentComplete(consentStatus: ConsentManager.ConsentStatus) {
        if (consentStatus == ConsentManager.ConsentStatus.OBTAINED || consentStatus == ConsentManager.ConsentStatus.NOT_REQUIRED) {
            Toast.makeText(this, "User can Show Ads", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this, "User can not Show Ads", Toast.LENGTH_SHORT).show()
        }
    }
}