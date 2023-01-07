plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.feature.auth"
}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    implementation(libs.compose.navigation)
    implementation(libs.compose.livedata)
    implementation(libs.compose.hilt.navigation)
    implementation(libs.compose.material.icons.extended)

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
}