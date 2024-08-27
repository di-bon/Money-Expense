plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)

    kotlin("kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.ilyaemeliyanov.mx_frontend"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ilyaemeliyanov.mx_frontend"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.firebase.auth.ktx)

    // Navigation
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("androidx.compose.material:material:1.6.8")

    // Material UI 3
    val materialui_ext_version = "1.6.8"
    implementation("androidx.compose.material:material-icons-extended:$materialui_ext_version")

    // TODO: clean up
    // ROOM
    val room_version = "2.4.3"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")

    // Firebase
    val firebase_version = "32.1.1"
    implementation("com.google.firebase:firebase-bom:$firebase_version")

    // Firestore
    val firestore_version = "25.0.0"
    implementation("com.google.firebase:firebase-firestore:$firestore_version")

    // Firebase auth
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.8.0"))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")

    // Animation
    val animation_version = "1.5.0"
    implementation("androidx.compose.animation:animation:$animation_version")

    // Date picker
    implementation("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")
    implementation("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")

    // HTTP Client
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0") // Optional: For logging

    // For secure storage of key-value pairs in Jetpack Compose
    implementation("androidx.security:security-crypto:1.1.0-alpha04")

    // Nice charts
    implementation("co.yml:ycharts:2.1.0")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}