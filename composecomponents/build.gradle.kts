import com.digitaldairy.plugins.MainGradlePlugin

plugins {
    id("com.android.library")
//    id("org.jetbrains.kotlin.android") apply false
}
apply<MainGradlePlugin>()
android {
    namespace = "com.digitaldairy.compose.appcomponents"
    compileSdk = 34
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    commonDependency()
    compose()
    composeTesting()
}