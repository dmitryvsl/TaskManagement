plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.feature.auth"
}

dependencies {

    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    // Compose
    implementation(libs.compose.livedata)
    implementation(libs.compose.hilt.navigation)

    //Accompanist
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)

    implementation(libs.accompanist.navigation)

    //Rxjava2
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
}