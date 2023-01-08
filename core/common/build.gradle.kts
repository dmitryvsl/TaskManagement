plugins {
    id("taskmanagement.android.library")
    id("taskmanagement.android.hilt")
    id("taskmanagement.android.library.compose")
}

android {
    namespace = "com.example.core.common"
}
dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))
    implementation(libs.android.annotation)
}
