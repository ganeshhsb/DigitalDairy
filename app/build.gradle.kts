plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
//    id( "kotlin-kapt")
//    id("kotlin-android-extensions")
    id("kotlin-android")
//    id ("dagger.hilt.android.plugin")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
    //id("com.android.dynamic-feature")
}

android {
    namespace = "com.digitaldairy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.digitaldairy"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
//    dynamicFeatures += setOf(":test")
    kotlinOptions {
        jvmTarget = "17"
    }
//    dynamicFeatures += setOf(":Labour", ":LandDetails", ":Schedules", ":vault")
}

dependencies {
//implementation(project(mapOf("path" to ":vault")))
//    implementation(project(mapOf("path" to ":Schedules")))
//    implementation(project(mapOf("path" to ":LandDetails")))
//    implementation(project(mapOf("path" to ":Labour")))
    //    implementation(project(mapOf("path" to ":vault")))
//    implementation(project(mapOf("path" to ":Labour")))
//    implementation(project(mapOf("path" to ":LandDetails")))
//    implementation(project(mapOf("path" to ":Schedules")))
//    implementation(project(mapOf("path" to ":test")))
//    val compose_version = "1.6.2"
//    val hilt_version = "2.44"
//    val room_version = "2.5.2"
//
////    implementation("androidx.core:core-ktx:1.9.0")
//    implementation(Dependencies.composeCoreKtx)
////    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
//    implementation(Dependencies.lifeCycleRuntime)
//    implementation("androidx.activity:activity-compose:1.8.2")
//    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
//    implementation("androidx.compose.ui:ui")
//    implementation("androidx.compose.ui:ui-graphics")
//    implementation("androidx.compose.ui:ui-tooling-preview")
//    implementation("androidx.compose.material3:material3")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
//    debugImplementation("androidx.compose.ui:ui-tooling")
//    debugImplementation("androidx.compose.ui:ui-test-manifest")
//
//
//    implementation("androidx.core:core-ktx:1.10.1")
//    implementation("androidx.appcompat:appcompat:1.6.1")
////    implementation("com.google.android.material:material:1.9.0")
//    implementation("androidx.compose.ui:ui:$compose_version")
//    implementation("androidx.compose.material:material:$compose_version")
//    implementation("androidx.compose.ui:ui-tooling-preview:$compose_version")
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
//    implementation("androidx.activity:activity-compose:1.7.2")
//    implementation("androidx.navigation:navigation-runtime-ktx:2.6.0")
////    implementation("androidx.compose.material3:material3-android:1.2.0-alpha03")
//    testImplementation("junit:junit:4.+")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
//    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
//    debugImplementation("androidx.compose.ui:ui-tooling:$compose_version")
//    implementation("org.jetbrains.kotlin:kotlin-script-runtime:1.8.10")
//
//
//    implementation("androidx.room:room-runtime:$room_version")
////    annotationProcessor("androidx.room:room-compiler:$room_version")
//
//    // To use Kotlin annotation processing tool (kapt)
//    kapt("androidx.room:room-compiler:$room_version")
//    // To use Kotlin Symbol Processing (KSP)
////    ksp("androidx.room:room-compiler:$room_version")
//
//    // optional - Kotlin Extensions and Coroutines support for Room
//    implementation("androidx.room:room-ktx:$room_version")
//
//    // optional - RxJava2 support for Room
////    implementation("androidx.room:room-rxjava2:$room_version")
//
//    // optional - RxJava3 support for Room
////    implementation("androidx.room:room-rxjava3:$room_version")
//
//    // optional - Guava support for Room, including Optional and ListenableFuture
////    implementation("androidx.room:room-guava:$room_version")
//
//    // optional - Test helpers
//    testImplementation("androidx.room:room-testing:$room_version")
//
//    // optional - Paging 3 Integration
//    implementation("androidx.room:room-paging:$room_version")
//    implementation("com.google.dagger:hilt-android:$hilt_version")
//    kapt("com.google.dagger:hilt-android-compiler:$hilt_version")
////    implementation("com.google.dagger:hilt-android:$hilt_version")
////    kapt "com.google.dagger:hilt-android-compiler:$hilt_version"
////    implementation("androidx.compose.runtime:runtime-livedata:1.4.3'
//    implementation ("androidx.compose.runtime:runtime-livedata:$compose_version")
//    implementation("io.coil-kt:coil-compose:2.4.0")
//    //val nav_version =("2.6.0"
//
//    implementation("androidx.navigation:navigation-compose:2.7.2")
//    implementation("androidx.compose.material3:material3:1.1.1")
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0")
////    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha07")
////    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:$hilt_version")
////    implementation("androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03")
//    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
////    implementation  'androidx.hilt:hilt-lifecycle-viewmodel:x.x.x'
////    implementation(project(":LandDetails"))
    commonDependency()
    compose()
    room()
    hilt()
    composeTesting()
    roomTesting()
    unitTest()
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}