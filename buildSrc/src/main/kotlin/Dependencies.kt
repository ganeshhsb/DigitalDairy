import Dependencies.hiltAndroid
import Dependencies.hiltCompiler
import Dependencies.hiltCompilerTesting
import Dependencies.hiltTesting
import Dependencies.navigationTesting
import org.gradle.api.artifacts.dsl.DependencyHandler

object Dependencies {
    // -----------  common ----------
    const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    const val appCompat = "androidx.appcompat:appcompat:1.6.1"
    const val navigationRuntime = "androidx.navigation:navigation-runtime-ktx:2.6.0"
    const val kotlinScriptRuntime = "org.jetbrains.kotlin:kotlin-script-runtime:1.8.10"
    val commonDependencyList = arrayListOf(
        lifeCycleRuntime, appCompat, navigationRuntime,
        kotlinScriptRuntime
    )

    // ---------- Compose ----------
    const val activityCompose = "androidx.activity:activity-compose:${Versions.composeVersion}"
    const val composeBom = "androidx.compose:compose-bom:${Versions.bomVersion}"
    const val composeUI = "androidx.compose.ui:ui:${Versions.composeVersion}"
    const val composeUIGraphics = "androidx.compose.ui:ui-graphics"
    const val composeUIToolingPreview = "androidx.compose.ui:ui-tooling-preview"
    const val composeMaterial3 = "androidx.compose.material3:material3"
    const val composeCoreKtx = "androidx.core:core-ktx:1.9.0"
    const val composeUiTooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"
    const val composeRuntimeLiveData =
        "androidx.compose.runtime:runtime-livedata:${Versions.composeVersion}"
    const val composeCoil = "io.coil-kt:coil-compose:2.4.0"
    const val navigationCompose = "androidx.navigation:navigation-compose:2.7.2"
    const val material3 = "androidx.compose.material3:material3:1.1.1"
    const val lifecycleViewmodelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:2.4.0"
    const val composeMaterial = "androidx.compose.material:material:${Versions.composeVersion}"
    const val composeUITooling = "androidx.compose.ui:ui-tooling:${Versions.composeVersion}"

    //    const val composeLifCycle =
//        "androidx.lifecycle:lifecycle-runtime-compose-android:2.6.2"
    val composeDependencyList = arrayListOf(
        activityCompose,
        composeBom,
        composeUI,
        composeUIGraphics,
        composeUIToolingPreview,
        composeMaterial3,
        composeCoreKtx,
        composeRuntimeLiveData,
        composeCoil,
        navigationCompose,
        material3,
        lifecycleViewmodelCompose,
        composeMaterial,
        composeUITooling,
//        composeLifCycle
    )

    //  ---------- Compose Unit testing  ----------
    const val navigationTesting = "androidx.navigation:navigation-testing:2.8.9"
    const val composeUIUnitTest = "androidx.compose.ui:ui-test-junit4"
    const val composeUITestManifest = "androidx.compose.ui:ui-test-manifest"

    //  ---------- Unit testing  ----------
    const val junit = "junit:junit:4.13.2"

    // Coroutines Testing
    const val kotlinCoroutineUT = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3"

    // MockK for mocking dependencies
    const val mockkUT = "io.mockk:mockk:1.13.8"

    // AndroidX Core Testing (for InstantTaskExecutorRule)
    const val androidxCoreUT = "androidx.arch.core:core-testing:2.2.0"

    // Turbine for Flow testing
    const val turbineUTForFlow = "app.cash.turbine:turbine:1.0.0"
    val unitTestDependencyList =
        arrayListOf(junit, kotlinCoroutineUT, mockkUT, androidxCoreUT, turbineUTForFlow)
    const val androidxJunit = "androidx.test.ext:junit:1.1.5"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.0"
    val androidUnitTestDependencyList = arrayListOf(androidxJunit, espressoCore)

    // -----------  hilt -----------
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"

    //    androidTestImplementation "com.google.dagger:hilt-android-testing:2.50"
//    kaptAndroidTest "com.google.dagger:hilt-android-compiler:2.50"
    const val hiltTesting = "com.google.dagger:hilt-android-testing:${Versions.hiltVersion}"
    const val hiltCompilerTesting =
        "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
//    kaptAndroidTest "com.google.dagger:hilt-android-compiler:2.50"

    val hiltDependencyList = arrayListOf(hiltAndroid, hiltNavigationCompose)

    // -----------  Room-------------
    const val roomRuntime = "androidx.room:room-runtime:${Versions.roomVersion}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.roomVersion}"

    // optional - Kotlin Extensions and Coroutines support for Room
    const val roomKtx = "androidx.room:room-ktx:${Versions.roomVersion}"

    // optional - Paging 3 Integration
    const val roomPaging = "androidx.room:room-paging:${Versions.roomVersion}"
    val roomDependencyList = arrayListOf(roomRuntime, roomKtx, roomPaging)

    // optional - Test helpers
    const val roomTesting = "androidx.room:room-testing:${Versions.roomVersion}"

    val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.rxJavaVersion}"
    val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.rxJavaVersion}"
    val rxJavaDependencyList = arrayListOf(rxJava, rxAndroid)

    //Mockito
    var mockitoKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    var mockito = "org.mockito:mockito-inline:${Versions.mockitoVersion}"
    val mockitoDependencyList = arrayListOf(mockitoKotlin, mockito)

    // coroutine testing

    // preference datastore
    var preferenceDataStore =
        "androidx.datastore:datastore-preferences:${Versions.prefDataStoreVersion}"

    var benchmarkJunit = "androidx.benchmark:benchmark-junit4:1.1.0"
    var benchmarkMacro = "androidx.benchmark:benchmark-macro:1.1.0"

    var jankMonitor = "androidx.test:monitor:1.4.0"
}

fun DependencyHandler.jankMonitor() {
    androidTestImplementation(Dependencies.jankMonitor)
}

fun DependencyHandler.benchmark() {
    androidTestImplementation(Dependencies.benchmarkMacro)
    androidTestImplementation(Dependencies.benchmarkJunit)
}

fun DependencyHandler.commonDependency() {
    Dependencies.commonDependencyList.forEach {
        implementation(it)
    }
}

fun DependencyHandler.compose() {
    Dependencies.composeDependencyList.forEach {
        implementation(it)
    }
    implementationWithDependency(platform(Dependencies.composeBom))
    debugImplementation(Dependencies.composeUiTooling)
}

fun DependencyHandler.composeTesting() {
    implementation(navigationTesting)
    implementation(Dependencies.composeUIUnitTest)
    implementation(Dependencies.composeUITestManifest)
}

fun DependencyHandler.unitTest() {
    Dependencies.unitTestDependencyList.forEach {
        testImplementation(it)
    }
    Dependencies.androidUnitTestDependencyList.forEach {
        androidTestImplementation(it)
    }
}

fun DependencyHandler.room() {
    Dependencies.roomDependencyList.forEach {
        implementation(it)
    }
    kapt(Dependencies.roomCompiler)
}

fun DependencyHandler.roomTesting() {
    testImplementation(Dependencies.roomTesting)
}

fun DependencyHandler.hilt() {
    Dependencies.hiltDependencyList.forEach {
        implementation(it)
    }

    kapt(hiltCompiler)
}

fun DependencyHandler.hiltTesting() {
    androidTestImplementation(hiltTesting)
    kaptAndroidTest(hiltCompilerTesting)
}

fun DependencyHandler.rxjava() {
    Dependencies.rxJavaDependencyList.forEach {
        implementation(it)
    }
}

fun DependencyHandler.mockito() {
    Dependencies.mockitoDependencyList.forEach {
        testImplementation(it)
    }
}

fun DependencyHandler.prefDatastore() {
    implementation(Dependencies.preferenceDataStore)
}



