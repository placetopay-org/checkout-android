import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.placetopay.p2pr"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.placetopay.p2pr"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes.all {
        val props = Properties().apply {
            load(file("src/${this@all.name}/assets/private.properties").inputStream())
        }

        buildConfigField("String", "loginId", props.getProperty("loginId"))
        buildConfigField("String", "secretKey", props.getProperty("secretKey"))
        buildConfigField("String", "URL_CHECKOUT", props.getProperty("urlEnvironment"))
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
        }
        release {
            isMinifyEnabled = true
            isDebuggable = false
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
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    val activityCompose = "1.7.2"
    val chucker = "4.0.0"
    val composeBom = "2023.03.00"
    val coreKtx = "1.9.0"
    val dagger = "2.44.2"
    val espresso = "3.5.1"
    val glide = "1.0.0-alpha.5"
    val hiltNavigationCompose = "1.0.0"
    val junit = "4.13.2"
    val junitExt = "1.1.5"
    val navigation = "2.5.3"
    val retrofit = "2.9.0"
    val runtimeKtx = "1.1.5"
    val viewModelCompose = "2.5.1"

    kapt("com.google.dagger:hilt-android-compiler:$dagger")

    implementation("androidx.core:core-ktx:$coreKtx")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$runtimeKtx")
    implementation("androidx.activity:activity-compose:$activityCompose")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelCompose")
    implementation("androidx.navigation:navigation-compose:$navigation")
    implementation("androidx.hilt:hilt-navigation-compose:$hiltNavigationCompose")
    implementation(platform("androidx.compose:compose-bom:$composeBom"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.runtime:runtime-livedata")

    implementation("com.github.bumptech.glide:compose:$glide")
    implementation("com.google.dagger:hilt-android:$dagger")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit")
    implementation("com.squareup.retrofit2:retrofit:$retrofit")

    debugImplementation("com.github.chuckerteam.chucker:library:$chucker")

    testImplementation("junit:junit:$junit")
    androidTestImplementation("androidx.test.ext:junit:$junitExt")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espresso")
    androidTestImplementation(platform("androidx.compose:compose-bom:$composeBom"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}