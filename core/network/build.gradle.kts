import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("taskmanagement.android.library")
    id("taskmanagement.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

val baseUrl: String = gradleLocalProperties(rootDir).getProperty("baseUrl")

android {
    namespace = "com.example.network"

    defaultConfig {
        buildConfigField("String", "BASE_URL", baseUrl)
    }
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