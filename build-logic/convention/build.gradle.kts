plugins {
    `kotlin-dsl`
}
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "taskmanagement.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "taskmanagement.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "taskmanagement.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "taskmanagement.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "taskmanagement.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidDagger") {
            id = "taskmanagement.dagger"
            implementationClass = "AndroidDaggerConventionPlugin"
        }
        register("androidFirebase") {
            id = "taskmanagement.android.firebase"
            implementationClass = "AndroidFirebaseConventionPlugin"
        }

    }
}