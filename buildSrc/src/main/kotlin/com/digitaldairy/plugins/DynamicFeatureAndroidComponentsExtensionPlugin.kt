//package com.digitaldairy.plugins
//
//import com.android.build.api.variant.DynamicFeatureAndroidComponentsExtension
//import org.gradle.api.JavaVersion
//import org.gradle.api.Plugin
//import org.gradle.api.Project
//import org.gradle.kotlin.dsl.getByType
//
//class DynamicFeatureAndroidComponentsExtensionPlugin : Plugin<Project> {
//    override fun apply(projet: Project) {
//        applyPlugin(projet)
//        setProjectConfig(projet)
//    }
//
//    private fun applyPlugin(project: Project) {
//        project.apply {
//            plugin("org.jetbrains.kotlin.android")
//            plugin("kotlin-android")
//        }
//    }
//
//    private fun setProjectConfig(project: Project) {
//        project.android().apply {
//            compileSdk = ProjectConfig.compileSdk
////            compileSdkVersion = ProjectConfig.compileSdk.toString()
//            defaultConfig {
//                minSdk = ProjectConfig.minSdk
//                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//            }
//
//            compileOptions {
//                sourceCompatibility = JavaVersion.VERSION_17
//                targetCompatibility = JavaVersion.VERSION_17
//            }
//
//            composeOptions {
//                kotlinCompilerExtensionVersion = "1.4.3"
//            }
//
//            buildFeatures {
//                compose = true
//            }
//        }
//    }
//
//    private fun Project.android(): DynamicFeatureAndroidComponentsExtension {
//        return extensions.getByType<DynamicFeatureAndroidComponentsExtension>() // (LibraryExtension::class.java)
//    }
//}