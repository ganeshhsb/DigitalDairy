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
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")
    implementation("androidx.navigation:navigation-testing:2.8.9")
    implementation("com.google.ar:core:1.48.0")
    implementation("androidx.compose.ui:ui-android:1.6.2")
    commonDependency()
    compose()
    room()
    hilt()
    hiltTesting()
    composeTesting()
    unitTest()
    prefDatastore()
    implementation("com.squareup:javapoet:1.13.0")// Ensure latest version


}
configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}