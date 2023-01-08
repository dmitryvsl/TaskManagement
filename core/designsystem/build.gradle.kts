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
    api(libs.android.material)

}