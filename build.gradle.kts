val libModules = listOf("api", "db")
val appModule = "app"

// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${ProjectConfig.kotlinVersion}")
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.28-alpha")

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

subprojects {
    println("project= ${this.name}")
    val isLibModule = this.name in libModules
    val isAppModule = this.name == appModule
    when {
        isLibModule -> configAndroidModule(this, "com.android.library")
        isAppModule -> configAndroidModule(this, "com.android.application")
        else -> {}
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

fun configAndroidModule(project: Project, pluginName: String) {
    project.apply(plugin = pluginName)
    project.configure<com.android.build.gradle.BaseExtension> {
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }
}