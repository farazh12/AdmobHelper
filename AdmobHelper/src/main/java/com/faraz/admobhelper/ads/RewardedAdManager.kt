package com.faraz.admobhelper.ads

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.faraz.admobhelper.R
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAd
import com.google.android.gms.ads.rewardedinterstitial.RewardedInterstitialAdLoadCallback

class RewardedAdManager(val context: Context) {
    private var rewardedInterstitialAd: RewardedInterstitialAd? = null
    private var rewardedAd: RewardedAd? = null

    fun loadRewardedInterstitialAd() {
        val adRequest = AdRequest.Builder().build()

        RewardedInterstitialAd.load(context,
            context.getString(R.string.rewarded_intersiial),
            adRequest,
            object : RewardedInterstitialAdLoadCallback() {

                override fun onAdLoaded(rewardedInterstitial: RewardedInterstitialAd) {
                    super.onAdLoaded(rewardedInterstitial)
                    rewardedInterstitialAd = rewardedInterstitial
                    rewardedInterstitialAd?.let {
                        it.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                rewardedInterstitialAd = null
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                super.onAdFailedToShowFullScreenContent(p0)
                                rewardedInterstitialAd = null
                            }
                        }
                    }
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.d("TAG", "onAdFailedToLoad: " + p0.message)
                }
            })
    }

    fun loadRewardedAd() {
        RewardedAd.load(context,
            context.getString(R.string.rewarded_video),
            AdManagerAdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(mRewardedAd: RewardedAd) {
                    super.onAdLoaded(mRewardedAd)
                    rewardedAd = mRewardedAd
                    rewardedAd.let {
                        it?.fullScreenContentCallback = object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent()
                                rewardedAd = null
                            }

                            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                                super.onAdFailedToShowFullScreenContent(p0)
                                rewardedAd = null
                            }
                        }
                    }
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Log.d("TAG", "onAdFailedToLoad: " + p0.message)
                }
            })
    }

    fun showForceRewardedInterstitialAd(
        context: Context, onUserEarnReward: (String) -> Unit
    ) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Video Ad is Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        val adRequest = AdRequest.Builder().build()
        RewardedInterstitialAd.load(context,
            context.getString(R.string.rewarded_intersiial),
            adRequest,
            object : RewardedInterstitialAdLoadCallback() {
                override fun onAdLoaded(mRewardedInterstitial: RewardedInterstitialAd) {
                    super.onAdLoaded(mRewardedInterstitial)
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }

                    val activity = context as Activity
                    mRewardedInterstitial.fullScreenContentCallback =
                        object : FullScreenContentCallback() {}
                    mRewardedInterstitial.show(context, object: OnUserEarnedRewardListener{
                        override fun onUserEarnedReward(p0: RewardItem) {
                            onUserEarnReward("Congrats!! You Earned Something!!!")
                        }
                    })
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    Toast.makeText(
                        context,
                        "Failed to load video ad!!! Try Again after 5 seconds",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun showForceRewardedAd(context: Context, onUserEarnReward: (String) -> Unit) {
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Video Ad is Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
        RewardedAd.load(context,
            context.getString(R.string.rewarded_video),
            AdManagerAdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(mRewardedAd: RewardedAd) {
                    super.onAdLoaded(mRewardedAd)
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    val activity = context as Activity
                    mRewardedAd.fullScreenContentCallback = object : FullScreenContentCallback() {}
                    mRewardedAd.show(context, object : OnUserEarnedRewardListener{
                        override fun onUserEarnedReward(p0: RewardItem) {
                            onUserEarnReward("Congrats!! you earned something from video!!!!")
                        }
                    })
                }

                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    if (progressDialog.isShowing) {
                        progressDialog.dismiss()
                    }
                    Toast.makeText(
                        context,
                        "Failed to load video ad!!! Try Again after 5 seconds",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }

    fun isAdLoaded(): Boolean {
        return rewardedInterstitialAd != null
    }

    fun isRewardedAdLoaded(): Boolean {
        return rewardedAd != null
    }

    fun showRewardedInterstitialAd(
        activity: Activity, earnedRewardListener: OnUserEarnedRewardListener
    ) {
        rewardedInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                rewardedInterstitialAd = null
            }
        }
        rewardedInterstitialAd?.show(activity, earnedRewardListener)
    }

    fun showRewardedAd(activity: Activity, earnedRewardListener: OnUserEarnedRewardListener) {
        rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                rewardedAd = null
            }
        }
        rewardedAd?.show(activity, earnedRewardListener)
    }

}