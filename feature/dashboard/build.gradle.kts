plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.feature.dashboard"
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))

    // Compose
    implementation(libs.compose.livedata)
    implementation(libs.compose.hilt.navigation)
    implementation(libs.compose.material.icons.extended)

    // Accompanist
    implementation(libs.accompanist.navigation)

    //Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    //Rxjava2
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
}