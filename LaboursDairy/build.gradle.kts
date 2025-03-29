import com.digitaldairy.plugins.MainGradlePlugin

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}
apply<MainGradlePlugin>()
android {
    namespace = "com.digitaldairy"
    buildFeatures {
        buildConfig = false
    }
    defaultConfig {
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "androidx.benchmark.junit4.AndroidBenchmarkRunner"
        testInstrumentationRunnerArguments["androidx.benchmark.suppressErrors"] = "EMULATOR,DEBUGGABLE"

        buildTypes{
            create("benchmark"){
                initWith(getByName("release"))
                signingConfig = signingConfigs.getByName("debug")
            }
        }
    }
}

dependencies {
    implementation(project(":composecomponents"))
    implementation("com.google.firebase:firebase-firestore-ktx:25.1.2")
    implementation("androidx.navigation:navigation-testing:2.8.9")
    implementation("com.google.ar:core:1.48.0")
    implementation("androidx.compose.ui:ui-android:1.6.2")
    implementation("androidx.benchmark:benchmark-macro-junit4:1.3.4")
    commonDependency()
    compose()
    room()
    hilt()
    hiltTesting()
    composeTesting()
    unitTest()
    prefDatastore()
    implementation("com.squareup:javapoet:1.13.0")// Ensure latest version
    benchmark()
    jankMonitor()
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//    androidTestImplementation ("androidx.test.ext:junit:1.1.5")

}
configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}