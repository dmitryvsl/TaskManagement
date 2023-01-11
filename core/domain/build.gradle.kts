plugins {
    id("taskmanagement.android.library")
}

android {
    namespace = "com.example.core.domain"
}


dependencies {
    implementation(libs.rxjava)
}