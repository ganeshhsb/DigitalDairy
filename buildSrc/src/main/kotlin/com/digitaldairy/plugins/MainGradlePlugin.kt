package com.digitaldairy.plugins

import ProjectConfig
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.DynamicFeatureAndroidComponentsExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

import org.gradle.api.JavaVersion
import org.gradle.kotlin.dsl.getByType

//import org.gradle.api.Project

class MainGradlePlugin : Plugin<Project> {
    override fun apply(projet: Project) {
        applyPlugin(projet)
        setProjectConfig(projet)

    }

    private fun applyPlugin(project: Project) {
        project.apply {
            plugin("org.jetbrains.kotlin.android")
            plugin("kotlin-android")
        }
    }

    private fun setProjectConfig(project: Project) {
        project.android().apply {
            compileSdk = ProjectConfig.compileSdk
//            compileSdkVersion = ProjectConfig.compileSdk.toString()
            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.4.3"
            }

            buildFeatures {
                compose = true
            }
        }
    }

    private fun Project.android(): LibraryExtension {
        return extensions.getByType<LibraryExtension>() // (LibraryExtension::class.java)
    }
}