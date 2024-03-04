pluginManagement {
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

rootProject.name = "DigitalDairy"
include(":app")
//include(":Labour")
//include(":LandDetails")
//include(":Schedules")
//include(":vault")
//include(":test")
