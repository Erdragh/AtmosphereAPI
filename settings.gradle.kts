enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "AtmosphereAPI"

pluginManagement {
    repositories {
        mavenLocal()
        gradlePluginPortal()
        maven(url = "https://maven.neoforged.net/releases")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs")
    }
}