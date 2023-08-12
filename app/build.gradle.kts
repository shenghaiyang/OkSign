plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

apply<LibraryPlugin>()

android {
    namespace = "com.shenghaiyang.oksign"
    compileSdk = 34


    defaultConfig {
        applicationId = "com.shenghaiyang.oksign"
        minSdk = 21
        targetSdk = 34
        versionCode = 3
        versionName = "1.2.0-snapshot"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        resourceConfigurations.addAll(listOf("zh-rCN", "xxhdpi"))
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            isCrunchPngs = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false
            isShrinkResources = false
            resValue("string", "app_name", "OkSign-Debug")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/kotlin/**"
        }
    }
}


dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2023.08.00")
    implementation(composeBom)
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    debugImplementation("androidx.compose.ui:ui-tooling")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")

    val nav_version = "2.7.0"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    val accompanist_version = "0.31.6-rc"
    implementation("com.google.accompanist:accompanist-systemuicontroller:$accompanist_version")

    implementation("com.squareup.okio:okio:3.5.0")

    debugImplementation("com.squareup.leakcanary:leakcanary-android:2.12")

}
