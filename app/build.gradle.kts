plugins {
    id(Dependencies.Kotlin.android)
    id(Dependencies.Kotlin.kapt)
    kotlin("kapt")
    id(Dependencies.Android.application)
    id(Dependencies.Kotlin.parcelize)
//    id(Dependencies.Hilt.plugin)
    id("com.google.dagger.hilt.android")
    id(Dependencies.Kotlin.jetbrains)
}

android {
    namespace = "com.example.baseandroidproject"
    compileSdk = Versions.compileSdkVersion

    defaultConfig {
        applicationId = Versions.applicationId
        minSdk = Versions.minSdkVersion
        targetSdk = Versions.targetSdkVersion
        versionCode = Versions.versionCode
        versionName = Versions.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
    }
}

dependencies {
    libs()
    hilt()
    lifeCycle()
    recycleView()
    retrofit()
    timber()

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}