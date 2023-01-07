plugins {
    id("taskmanagement.android.library.compose")
    id("taskmanagement.android.feature")
}

android {
    namespace = "com.example.feature.onboarding"

}

dependencies {

    implementation(project(":core:designsystem"))
    implementation(project(":core:common"))

    implementation(libs.compose.navigation)
    implementation(libs.accompanist.pager)
    implementation(libs.accompanist.pager.indicators)
}