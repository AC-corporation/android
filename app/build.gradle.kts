import java.util.Properties;

plugins {
    id("com.android.application")
    "kotlin-android"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
}

var properties:Properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())
var serverURl = properties.getProperty("BASE_URL")

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

        buildConfigField("String", "BASE_URL", serverURl)
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        buildFeatures {
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

    repositories {
        maven {
            url = uri("https://jitpack.io")
        }
    }
    dataBinding {
        enable = true
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-common:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("com.github.islandparadise14:Mintable:1.5.1")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")


    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.10.0"))
    implementation("com.squareup.okhttp3:okhttp")
    implementation("com.squareup.okhttp3:logging-interceptor")
    implementation("com.google.code.gson:gson:2.10")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:(latest version)")
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    implementation ("com.github.JakeWharton:ViewPagerIndicator:2.4.1")

}