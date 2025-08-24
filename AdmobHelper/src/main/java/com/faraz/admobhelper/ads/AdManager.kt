package com.faraz.admobhelper.ads

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.FrameLayout
import com.faraz.admobhelper.R
import com.google.ads.mediation.admob.AdMobAdapter
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.VideoOptions
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdOptions


class AdManager {

    private val TAG = "AdManager"

    fun init(activity: Activity) {
        MobileAds.initialize(activity) { }
    }

    fun showInterstitial(activity: Activity, onDismissed: () -> Unit) {
        InterstitialAd.load(activity,
            activity.getString(R.string.interstitial),
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    interstitialAd.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                onDismissed()
                            }
                        }
                    interstitialAd.show(activity)
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                }
            })
    }

    private fun loadBanner(
        activity: Activity, frameLayout: FrameLayout
    ) {
        try {
            val adView = AdView(activity)
            adView.adUnitId = activity.getString(R.string.banner)
            frameLayout.addView(adView)
            adView.setAdSize(getAdSize(activity))
            val extras = Bundle()
            extras.putString("collapsible", "bottom")

            val adRequest =
                AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter::class.java, extras).build()
            adView.loadAd(adRequest)
            adView.adListener = object : AdListener() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    frameLayout.visibility = View.GONE
                }

                override fun onAdLoaded() {
                    frameLayout.visibility = View.VISIBLE
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getAdSize(activity: Activity): AdSize {
        val defaultDisplay = activity.windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        defaultDisplay.getMetrics(displayMetrics)
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
            activity, (displayMetrics.widthPixels.toFloat() / displayMetrics.density).toInt()
        )
    }

    fun loadNativeAd(context: Context) {
        AdLoader.Builder(context, context.getString(R.string.native_ad))
            .forNativeAd { nativeAd: NativeAd ->
                populateUnifiedNativeAdView(context, nativeAd)
            }.withAdListener(object : AdListener() {}).withNativeAdOptions(
                NativeAdOptions.Builder()
                    .setVideoOptions(VideoOptions.Builder().setStartMuted(true).build()).build()
            ).build().loadAd(
                adRequest
            )
    }

    private fun populateUnifiedNativeAdView(context: Context, nativeAd: NativeAd) {

    }

    private val adRequest: AdRequest
        get() {
            val builder = AdRequest.Builder()
            return builder.build()
        }

    enum class Type {
        SMALL, MEDIUM
    }

}