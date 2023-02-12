plugins {
    id("taskmanagement.android.library")
    id("taskmanagement.android.hilt")
}

android {
    namespace = "com.example.core.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:cache"))
}