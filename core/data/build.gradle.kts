plugins {
    id("taskmanagement.android.library")
    id("taskmanagement.android.hilt")
}

android {
    namespace = "com.example.core.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.rxjava)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
}