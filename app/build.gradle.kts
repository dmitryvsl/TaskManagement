plugins {
    id("taskmanagement.android.application")
    id("taskmanagement.android.application.compose")
    id("taskmanagement.android.hilt")
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
    implementation(project(":core:domain"))
    implementation(project(":core:data"))

    //features
    implementation(project(":feature:auth"))
    implementation(project(":feature:dashboard"))
    implementation(project(":feature:chat"))
    implementation(project(":feature:notification"))
    implementation(project(":feature:settings"))

    //Compose
    implementation(libs.compose.activity)

    // Accompanist
    implementation(libs.accompanist.systemui)
    implementation(libs.accompanist.navigation)
}