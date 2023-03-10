plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.feature.notification"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Compose
    implementation(libs.compose.hilt.navigation)

    //Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
    implementation(libs.accompanist.navigation)
}