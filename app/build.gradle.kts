plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(ProjectConfig.compileSdk)

    defaultConfig {
        applicationId = "zj.app.taipeizootour"
        minSdkVersion(ProjectConfig.minSdk)
        targetSdkVersion(ProjectConfig.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(project(":api"))
    implementation(project(":db"))

    implementation(fileTree( mapOf("dir" to "libs", "include" to listOf("*.jar")) ))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${ProjectConfig.kotlinVersion}")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.fragment:fragment-ktx:1.2.5")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.google.android.material:material:1.1.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")
    implementation("io.coil-kt:coil:0.11.0")

    // RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.2.0-alpha05")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

}