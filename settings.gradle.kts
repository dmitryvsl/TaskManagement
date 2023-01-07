pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "TaskManagement"
include (":app")
include (":core")
include (":feature")
include(":core:designsystem")
include(":feature:auth")
include(":core:domain")
include(":core:data")
include(":core:common")
