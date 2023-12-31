import org.jetbrains.kotlin.cli.jvm.main

plugins {
    id("com.android.application")
    "kotlin-android"
}

android {
    namespace = "com.example.allclear"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.allclear"
        minSdk = 21
        targetSdk = 34
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
        buildFeatures{
            viewBinding = true
        }
    }
    sourceSets["main"].java {
        srcDir("src/main/kotlin")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dataBinding {
        enable = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation ("com.github.islandparadise14:Mintable:1.5.1")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.0.0")
}