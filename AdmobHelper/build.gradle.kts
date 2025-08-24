plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.faraz.admobhelper"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        debug {
            resValue ("string", "appid", "ca-app-pub-3940256099942544~3347511713")
            resValue ("string", "banner", "ca-app-pub-3940256099942544/6300978111")
            resValue ("string", "interstitial", "ca-app-pub-3940256099942544/1033173712")
            resValue ("string", "native_ad", "ca-app-pub-3940256099942544/2247696110")
            resValue ("string", "appopen", "ca-app-pub-3940256099942544/3419835294")
            resValue ("string", "rewarded_intersiial", "ca-app-pub-3940256099942544/5354046379")
            resValue ("string", "rewarded_video", "ca-app-pub-3940256099942544/5224354917")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.play.services.ads)
    implementation(libs.user.messaging.platform)

    implementation(libs.androidx.lifecycle.extensions)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    annotationProcessor(libs.androidx.lifecycle.compiler)
}