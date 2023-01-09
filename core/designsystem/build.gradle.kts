plugins {
    id("taskmanagement.android.library")
    id("taskmanagement.android.library.compose")
}

android {
    namespace = "com.example.core.designsystem"
}

dependencies {

    api(libs.compose.material)
    api(libs.compose.ui)
    implementation(libs.android.material)

    debugImplementation(libs.compose.ui.tooling)
    debugImplementation(libs.compose.ui.tooling.preview)

}