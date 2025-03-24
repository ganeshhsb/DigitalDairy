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
//    hiltTesting()
    // For Robolectric tests.
    testImplementation("com.google.dagger:hilt-android-testing:2.51") {
        exclude(group = "com.squareup", module = "javapoet")
    }
    // ...with Kotlin.
    kaptTest ("com.google.dagger:hilt-android-compiler:2.51") {
        exclude(group = "com.squareup", module = "javapoet")
    }
    // ...with Java.
//    testAnnotationProcessor("com.google.dagger:hilt-android-compiler:2.51.1")


    // For instrumented tests.
    androidTestImplementation ("com.google.dagger:hilt-android-testing:2.51") {
        exclude(group = "com.squareup", module = "javapoet")
    }
    // ...with Kotlin.
    kaptAndroidTest ("com.google.dagger:hilt-android-compiler:2.51")  {
        exclude(group = "com.squareup", module = "javapoet")
    }
    // ...with Java.
//    androidTestAnnotationProcessor 'com.google.dagger:hilt-android-compiler:2.51.1")
    composeTesting()
//    roomTesting()
    unitTest()
    prefDatastore()
    implementation("com.squareup:javapoet:1.13.0")// Ensure latest version
}
configurations.all {
    resolutionStrategy {
        force("com.squareup:javapoet:1.13.0")
    }
}