plugins {
    id("taskmanagement.android.library")
}

android {
    namespace = "com.example.core.common"
}

dependencies {

    implementation(libs.rxjava)
    implementation(libs.rxandroid)
    implementation(libs.android.lifecycle.viewmodel)
    implementation(libs.android.lifecycle.livedata)
}