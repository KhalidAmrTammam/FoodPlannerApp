plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services") // Add this line
}

android {
    namespace = "com.iti.java.foodplannerbykhalidamr"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.iti.java.foodplannerbykhalidamr"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

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
}

dependencies {
    // Android Core
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // RX-Java
    implementation(libs.rxjava)
    implementation(libs.rxandroid)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.adapter.rxjava3)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.rxjava3)
    annotationProcessor(libs.room.compiler)

    // Firebase
    implementation(libs.firebase.auth)
    implementation(libs.play.services.auth) // For Google Sign-In

    // Lottie
    implementation(libs.lottie)

    // Glide
    implementation(libs.glide)
    annotationProcessor(libs.compiler) // Glide annotation processor

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}