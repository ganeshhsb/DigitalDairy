import com.digitallibrary.plugins.MainGradlePlugin

plugins {
    id("com.android.library")
//    id("org.jetbrains.kotlin.android") apply false
}
apply<MainGradlePlugin>()
android {
    namespace = "com.digitallibrary.compose.appcomponents"
    compileSdk = 34
}

dependencies {
    commonDependency()
    compose()
    composeTesting()
}