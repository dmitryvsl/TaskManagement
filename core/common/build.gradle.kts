plugins {
    id("taskmanagement.android.library")
}

android {
    namespace = "com.example.core.common"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.lifecycle.livedata)
}