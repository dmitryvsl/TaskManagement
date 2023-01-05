plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
    id("taskmanagement.android.firebase")

}

android {
    namespace = "com.example.auth"
}

dependencies {

    implementation(project(":core:designsystem"))

    implementation(libs.compose.navigation)
    implementation(libs.compose.material.icons.extended)

    implementation(libs.firebase.auth)
}