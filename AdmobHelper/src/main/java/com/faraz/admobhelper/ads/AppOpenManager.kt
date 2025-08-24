package com.faraz.admobhelper.ads

import android.app.Activity
import android.app.Application
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.appopen.AppOpenAd.AppOpenAdLoadCallback
import java.util.Date

open class AppOpenManager {

    private var myApplication: Application? = null
    private var loadCallback: AppOpenAdLoadCallback? = null
    private var loadTime: Long = 0
    private var adUnitId = ""
    var isReturnFromWeb = false
    private var isShowingAd = false

    fun init(application: Application) {
        this.myApplication = application
    }

    fun setAdUnitId(adUnitId: String) {
        this.adUnitId = adUnitId
    }

    fun setIsReturnFromWeb(isReturnFromWeb: Boolean) {
        this.isReturnFromWeb = isReturnFromWeb
    }

    fun getIsReturnFromWeb(): Boolean {
        return isReturnFromWeb
    }

    fun showAppOpenAd(activity: Activity) {
        // We will implement this below.
        // Have unused ad, no need to fetch another.
        loadCallback = object : AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                super.onAdLoaded(ad)
                loadTime = Date().time
                ad.show(activity)
            }

        }
        val request = adRequest
        myApplication?.let {
            AppOpenAd.load(
                it, adUnitId, request, loadCallback as AppOpenAdLoadCallback
            )
        }
    }

    private val adRequest: AdRequest
        get() = AdRequest.Builder().build()
}