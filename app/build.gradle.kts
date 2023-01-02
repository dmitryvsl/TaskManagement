plugins {
    id("taskmanagement.android.application")
    id("taskmanagement.android.application.compose")
    id("taskmanagement.dagger")
}

android {
    namespace = "com.example.taskmanagement"

    defaultConfig {
        applicationId = "com.example.taskmanagement"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        val release by getting {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))

    //features
    implementation(project(":feature:onboarding"))
    implementation(project(":feature:auth"))

    implementation(libs.compose.activity)
    implementation(libs.compose.navigation)
}