plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.auth"
}

dependencies {

    implementation(project(":core:designsystem"))

    implementation(libs.compose.navigation)
}