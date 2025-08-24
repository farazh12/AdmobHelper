plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.admobintegerationexample"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.admobintegerationexample"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            resValue ("string", "appid", "ca-app-pub-3940256099942544~3347511713")
            resValue ("string", "banner", "ca-app-pub-3940256099942544/6300978111")
            resValue ("string", "interads", "ca-app-pub-3940256099942544/1033173712")
            resValue ("string", "nativeads", "ca-app-pub-3940256099942544/2247696110")
            resValue ("string", "appopen", "ca-app-pub-3940256099942544/3419835294")
            resValue ("string", "rewarded_intersiial_ad", "ca-app-pub-3940256099942544/5354046379")
            resValue ("string", "rewarded_ad", "ca-app-pub-3940256099942544/5224354917")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
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
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(project(":AdmobHelper"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}