plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.onboarding"

}

dependencies {

    implementation(project(":core:designsystem"))

    implementation(libs.compose.navigation)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
}