plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.feature.dashboard"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Compose
    implementation(libs.compose.livedata)
    implementation(libs.compose.hilt.navigation)

    // Accompanist
    implementation(libs.accompanist.navigation)
    implementation(libs.accompanist.pager)

    //Rxjava2
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
}