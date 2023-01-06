plugins {
    id("taskmanagement.android.library")
}

android {
    namespace = "com.example.core.domain"
}


dependencies {
    implementation(libs.rxjava)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
}