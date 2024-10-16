import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    object Android {
        const val application = "com.android.application"
        const val library = "com.android.library"
        const val classpath = "com.android.tools.build:gradle:7.3.1"
        const val coreKtx = "androidx.core:core-ktx:1.9.0"
        const val material = "com.google.android.material:material:1.9.0"
        const val startup = "androidx.startup:startup-runtime:1.0.0"
        const val appCompat = "androidx.appcompat:appcompat:1.6.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val room = "androidx.room:room-compiler:2.4.3"
    }

    object Lifecycle {
        const val lifecycleVersion = "2.5.1"
        const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifeCycle = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
    }

    object Kotlin {
        const val kotlinVersion = "1.7.10"
        const val classpath = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
        const val reflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        const val test = "org.jetbrains.kotlin:kotlin-test:$kotlinVersion"
        const val android = "kotlin-android"
        const val kapt = "kotlin-kapt"
        const val parcelize = "kotlin-parcelize"
        const val jetbrains = "org.jetbrains.kotlin.android"
        const val compilerExtensionVersion = "1.3.1"
        const val dataStore = "androidx.datastore:datastore-preferences:1.0.0"
    }

    object Hilt {
        private const val hiltVersion = "2.48"
        const val plugin = "dagger.hilt.android.plugin"
        const val classpath = "com.google.dagger:hilt-android-gradle-plugin:$hiltVersion"
        const val android = "com.google.dagger:hilt-android:$hiltVersion"
        const val kapt = "com.google.dagger:hilt-android-compiler:$hiltVersion"
    }

    object Recycler {
        private const val recyclerViewVersion = "1.3.2"
        private const val recyclerViewSelectionVersion = "1.1.0"
        const val recyclerView = "androidx.recyclerview:recyclerview:$recyclerViewVersion"
        const val recyclerViewSelection = "androidx.recyclerview:recyclerview-selection:$recyclerViewSelectionVersion"
    }

    object Retrofit {
        const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val gson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    }

    object Timber {
        private const val timberVersion = "5.0.1"
        const val timber = "com.jakewharton.timber:timber:$timberVersion"
    }
}

fun DependencyHandler.libs() {
    implementation(Dependencies.Android.coreKtx)
    implementation(Dependencies.Android.appCompat)
    implementation(Dependencies.Android.material)
    implementation(Dependencies.Android.constraintLayout)
}

fun DependencyHandler.hilt() {
    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.kapt)
    kapt(Dependencies.Android.room)
}

fun DependencyHandler.lifeCycle() {
    implementation(Dependencies.Lifecycle.runtimeKtx)
    implementation(Dependencies.Lifecycle.lifeCycle)
    implementation(Dependencies.Lifecycle.liveData)
}

fun DependencyHandler.recycleView() {
    implementation(Dependencies.Recycler.recyclerView)
    implementation(Dependencies.Recycler.recyclerViewSelection)
}

fun DependencyHandler.retrofit() {
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.gson)
}

fun DependencyHandler.timber() {
    implementation(Dependencies.Timber.timber)
}
