import com.digitaldairy.plugins.MainGradlePlugin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}
apply<MainGradlePlugin>()
android {
    namespace = "com.digitaldairy.labour"
}

dependencies {

    implementation(project(":composecomponents"))
    commonDependency()
    compose()
    room()
    hilt()
    composeTesting()
    roomTesting()
    unitTest()
}