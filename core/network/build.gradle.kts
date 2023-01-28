plugins {
    id("taskmanagement.android.library")
    id("taskmanagement.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.network"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(libs.rxjava)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)

    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.converter)


}