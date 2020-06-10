object Plugins {
    const val android = "com.android.tools.build:gradle:3.5.0"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72"
    const val androidExtensions = "org.jetbrains.kotlin:kotlin-android-extensions:1.3.72"
    const val cocoapods = "org.jetbrains.kotlin:kotlin-android-extensions:1.3.50"
    const val serialization = "org.jetbrains.kotlin:kotlin-serialization:1.3.72"
}

val plugins: Map<String, String> = mapOf(
    "com.android.library" to Plugins.android,
    "org.jetbrains.kotlin.multiplatform" to Plugins.kotlin,
    "kotlin-android" to Plugins.kotlin,
    "kotlin-android-extensions" to Plugins.androidExtensions,
    "org.jetbrains.kotlin.native.cocoapods" to Plugins.kotlin,
    "org.jetbrains.kotlin.plugin.serialization" to Plugins.serialization
)

pluginManagement {
    repositories {
        jcenter()
        google()
        mavenCentral()
    }
    resolutionStrategy.eachPlugin {
        useModule(plugins[requested.id.id] ?: return@eachPlugin)
    }
}
enableFeaturePreview("GRADLE_METADATA")
rootProject.name = "planet-multiplatform"
