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
include(":core:domain")
include(":core:data")
include(":feature:dashboard")
include(":feature:auth")
include(":feature:chat")
include(":feature:notification")
include(":feature:settings")
include(":core:common")
include(":core:network")
include(":core:cache")
