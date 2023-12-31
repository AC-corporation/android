pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}
buildscript{
    repositories{
        google()
        jcenter()
    }
    dependencies{
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
    }
}

rootProject.name = "AllClear"
include(":app")
