plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(ProjectConfig.compileSdk)

    defaultConfig {
        minSdkVersion(ProjectConfig.minSdk)
        targetSdkVersion(ProjectConfig.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    kapt {
        arguments {
            arg("room.schemaLocation", "$rootDir/schemas")
        }
    }
}

dependencies {
    implementation(fileTree( mapOf("dir" to "libs", "include" to listOf("*.jar")) ))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${ProjectConfig.kotlinVersion}")
    implementation("androidx.core:core-ktx:1.3.1")
    implementation("androidx.appcompat:appcompat:1.1.0")

    api("androidx.room:room-runtime:${Dependency.roomVersion}")
    kapt("androidx.room:room-compiler:${Dependency.roomVersion}")

    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:${Dependency.roomVersion}")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
}