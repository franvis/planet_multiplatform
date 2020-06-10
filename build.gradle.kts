plugins {
    kotlin("multiplatform") version "1.3.72"
    id("org.jetbrains.kotlin.native.cocoapods") version "1.3.72"
    kotlin("plugin.serialization") version "1.3.72"
    id("maven-publish")
    id("com.android.library")
}

repositories {
    mavenCentral()
    google()
    jcenter()
}

group = "ar.com.planets"
version = "0.0.5"

android {
    compileSdkVersion(29)

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
    }

    sourceSets {
        getByName("main").java.srcDirs("src/androidMain/kotlin")
        getByName("main").res.srcDirs("src/androidMain/resources")
        getByName("main").manifest.srcFile("src/androidMain/AndroidManifest.xml")
        getByName("androidTest").java.srcDirs("src/androidTest/kotlin")
        getByName("androidTest").res.srcDirs("src/androidTest/resources")
    }
}

kotlin {
    jvm("android")
    cocoapods {
        // Configure fields required by CocoaPods.
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"

        // The name of the produced framework can be changed.
        // The name of the Gradle project is used here by default.
        frameworkName = "planet-multiplatform"
    }
    // This is for iPhone simulator
    // Switch here to iosArm64 (or iosArm32) to build library for iPhone device
    val buildForDevice = project.findProperty("kotlin.native.cocoapods.target") == "ios_arm"
    if (buildForDevice) {
        iosArm64("ios64")
        iosArm32("ios32")

        val iOSMain by sourceSets.creating
        sourceSets["ios64Main"].dependsOn(iOSMain)
        sourceSets["ios32Main"].dependsOn(iOSMain)
    } else {
        iosX64("ios")
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:1.3.6")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:0.20.0")
                implementation("io.ktor:ktor-client-core:1.3.2")
                implementation("io.ktor:ktor-client-json:1.3.2")
                implementation("io.ktor:ktor-client-serialization:1.3.2")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(kotlin("stdlib"))
                implementation("org.jetbrains.kotlin:kotlin-stdlib")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0")
                implementation("io.ktor:ktor-client-android:1.3.2")
                implementation("io.ktor:ktor-client-json-jvm:1.3.2")
                implementation("io.ktor:ktor-client-serialization-jvm:1.3.2")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-junit"))
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:1.3.6")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:0.20.0")
                implementation("io.ktor:ktor-client-ios:1.3.2")
                implementation("io.ktor:ktor-client-json-native:1.3.2")
                implementation("io.ktor:ktor-client-serialization-native:1.3.2")
            }
        }
        val iosTest by getting {
        }
    }
}
