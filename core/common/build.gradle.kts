plugins {
    id("taskmanagement.android.library")
    id("taskmanagement.android.hilt")
}

android {
    namespace = "com.example.core.common"
}
dependencies {
    implementation(project(":core:domain"))
    implementation(libs.android.annotation)
}
