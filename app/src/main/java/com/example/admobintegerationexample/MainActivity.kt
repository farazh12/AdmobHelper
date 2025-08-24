package com.example.admobintegerationexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.faraz.admobhelper.ads.AdManager
import com.faraz.admobhelper.ads.AppOpenManager
import com.faraz.admobhelper.ads.RewardedAdManager
import com.faraz.admobhelper.consent.ConsentManager

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
        } else {
            Toast.makeText(this, "User can not Show Ads", Toast.LENGTH_SHORT).show()
        }
    }

    fun OnShowBanner(view: View) {}
    fun OnShowInterstitial(view: View) {
        AdManager().showInterstitial(this) {

        }
    }

    fun OnShowNative(view: View) {}
    fun OnShowAppOpen(view: View) {
        val appOpenManager = AppOpenManager()
        appOpenManager.init(application)
        appOpenManager.showAppOpenAd(this)
    }

    fun OnShowRewardedInterstitial(view: View) {
        val rewardedAdManager = RewardedAdManager(this)
        rewardedAdManager.showForceRewardedInterstitialAd(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    fun OnShowRewardedVideo(view: View) {
        val rewardedAdManager = RewardedAdManager(this)
        rewardedAdManager.showForceRewardedAd(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }
}