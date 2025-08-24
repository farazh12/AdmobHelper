package com.faraz.admobhelper.consent

import android.app.Activity
import android.content.Context
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentForm
import com.google.android.ump.ConsentInformation
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform

class ConsentManager {
    private lateinit var consentCallback: ConsentCallback
    private lateinit var consentInformation: ConsentInformation
    private lateinit var consentForm: ConsentForm

    fun setUserConsent(context: Context) {
        consentCallback = context as ConsentCallback
        val debugSettings = ConsentDebugSettings.Builder(context)
            .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
            .addTestDeviceHashedId("35BCC14C0C1485A7D5392028B38493A9").build()

        val params = ConsentRequestParameters.Builder()
            .setConsentDebugSettings(debugSettings)
            .setTagForUnderAgeOfConsent(false).build()

        consentInformation = UserMessagingPlatform.getConsentInformation(context)
        consentInformation.requestConsentInfoUpdate(context as Activity, params, {
            // The consent information state was updated.
            // You are now ready to check if a form is available.
            if (consentInformation.isConsentFormAvailable) {
//                    loadForm(context, consentCallback)
                loadLatestForm(context, consentCallback)
            } else {
                consentCallback.onConsentComplete(ConsentStatus.NOT_REQUIRED)
            }
        }, {
            // Handle the error.
            consentCallback.onConsentComplete(ConsentStatus.FORM_ERROR)
        })
    }

    val isPrivacyOptionsRequired: Boolean
        get() = consentInformation.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.REQUIRED

    private fun showPrivacyOptions(activity: Activity, onConsentCallback: ConsentCallback) {
        UserMessagingPlatform.showPrivacyOptionsForm(activity) {
            if (consentInformation.privacyOptionsRequirementStatus == ConsentInformation.PrivacyOptionsRequirementStatus.NOT_REQUIRED) {
                onConsentCallback.onConsentComplete(ConsentStatus.NOT_REQUIRED)
            }
        }
    }

    private fun loadLatestForm(context: Context, consentCallback: ConsentCallback) {
//        if (isPrivacyOptionsRequired) {
//            showPrivacyOptions(context as Activity, object : ConsentCallback {
//                override fun onConsentComplete(consentStatus: ConsentStatus) {
//                    showLatestForm(context, consentCallback)
//                }
//            })
//        } else {
        showLatestForm(context, consentCallback)
//        }
    }

    private fun showLatestForm(context: Context, consentCallback: ConsentCallback) {
        UserMessagingPlatform.loadAndShowConsentFormIfRequired(context as Activity) {
            when (consentInformation.consentStatus) {
                ConsentInformation.ConsentStatus.OBTAINED -> {
                    // App can start requesting ads.
                    consentCallback.onConsentComplete(ConsentStatus.OBTAINED)
                }

                ConsentInformation.ConsentStatus.NOT_REQUIRED -> {
                    consentCallback.onConsentComplete(ConsentStatus.NOT_REQUIRED)
                }

                else -> {
                    consentCallback.onConsentComplete(ConsentStatus.FORM_ERROR)
                }
            }
        }
    }

    private fun loadForm(context: Context, consentCallback: ConsentCallback) {
        // Loads a consent form. Must be called on the main thread.
        UserMessagingPlatform.loadConsentForm(context, {
            this.consentForm = it
            if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.REQUIRED) {
                consentForm.show(
                    context as Activity
                ) {
                    if (consentInformation.consentStatus == ConsentInformation.ConsentStatus.OBTAINED) {
                        // App can start requesting ads.
                        consentCallback.onConsentComplete(ConsentStatus.OBTAINED)
                    } else {
                        // Handle dismissal by reloading form.
                        loadForm(context, consentCallback)
                    }
                }
            } else {
                consentCallback.onConsentComplete(ConsentStatus.NOT_REQUIRED)
            }
        }, {
            // Handle the error.
            consentCallback.onConsentComplete(ConsentStatus.FORM_ERROR)
        })
    }

    fun resetConsentInformation() {
        consentInformation.reset()
    }

    interface ConsentCallback {
        fun onConsentComplete(consentStatus: ConsentStatus)
    }

    enum class ConsentStatus {
        OBTAINED, NOT_REQUIRED, FORM_ERROR
    }
}