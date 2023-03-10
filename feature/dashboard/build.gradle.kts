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
    implementation(project(":core:common"))

    // Compose
    implementation(libs.compose.hilt.navigation)

    // Accompanist
    implementation(libs.accompanist.navigation)
    implementation(libs.accompanist.pager)
}