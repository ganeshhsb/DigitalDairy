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
        composeUITooling
    )

    //  ---------- Compose Unit testing  ----------
    const val composeUIUnitTest = "androidx.compose.ui:ui-test-junit4"
    const val composeUITestManifest = "androidx.compose.ui:ui-test-manifest"

    //  ---------- Unit testing  ----------
    const val junit = "junit:junit:4.13.2"
    const val androidxJunit = "androidx.test.ext:junit:1.1.5"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
    val unitTestDependencyList = arrayListOf(androidxJunit, espressoCore)

    // -----------  hilt -----------
    const val hiltAndroid = "com.google.dagger:hilt-android:${Versions.hiltVersion}"
    const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.hiltVersion}"
    const val hiltNavigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
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
    implementation(Dependencies.composeUIUnitTest)
    implementation(Dependencies.composeUITestManifest)
}

fun DependencyHandler.unitTest() {
    testImplementation(Dependencies.junit)
    Dependencies.unitTestDependencyList.forEach {
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
    kapt(Dependencies.hiltCompiler)
}



