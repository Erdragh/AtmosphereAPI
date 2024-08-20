import groovy.json.StringEscapeUtils
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.utils.loadPropertyFromResources

plugins {
    java
    idea
    id("maven-publish")
    alias(libs.plugins.kotlin)
    alias(libs.plugins.moddev)
}

tasks.named<Wrapper>("wrapper") {
    // Define wrapper values here so as to not have to always do so when updating gradlew.properties.
    // Switching this to Wrapper.DistributionType.ALL will download the full gradle sources that comes with
    // documentation attached on cursor hover of gradle classes and methods. However, this comes with increased
    // file size for Gradle. If you do switch this to ALL, run the Gradle wrapper task twice afterwards.
    // (Verify by checking gradle/wrapper/gradle-wrapper.properties to see if distributionUrl now points to `-all`)
    distributionType = Wrapper.DistributionType.BIN
}

val modId: String by project
val modVersion: String by project
val modGroupId: String by project

version = modVersion
group = modGroupId

repositories {
    mavenLocal()
    maven {
        name = "Kotlin for Forge"
        setUrl("https://thedarkcolour.github.io/KotlinForForge/")
    }
}

base {
    archivesName = modId
}

// Mojang ships Java 21 to end users starting in 1.20.5, so mods should target Java 21.
java.toolchain.languageVersion = JavaLanguageVersion.of(21)

kotlin.compilerOptions {
    jvmTarget.set(JvmTarget.JVM_21)
    freeCompilerArgs.add("-Xjvm-default=all")
}

neoForge {
    // Specify the version of NeoForge to use.
    version = libs.versions.neoforge

    parchment.mappingsVersion = libs.versions.parchment
    parchment.minecraftVersion = "1.21" // TODO: revert to libs.versions.minecraft when parchment updates

    // This line is optional. Access Transformers are automatically detected
    // accessTransformers = project.files("src/main/resources/META-INF/accesstransformer.cfg")

    // Default run configurations.
    // These can be tweaked, removed, or duplicated as needed.
    runs {
        register("client") {
            client()

            // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("server") {
            server()
            programArgument("--nogui")
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        // This run config launches GameTestServer and runs all registered gametests, then exits.
        // By default, the server will crash when no gametests are provided.
        // The gametest system is also enabled by default for other run configs under the /test command.
        register("gameTestServer") {
            type = "gameTestServer"
            systemProperty("neoforge.enabledGameTestNamespaces", modId)
        }

        register("data") {
            data()

            // example of overriding the workingDirectory set in configureEach above, uncomment if you want to use it
            // gameDirectory = project.file("run-data")

            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            programArguments.addAll("--mod", modId, "--all", "--output", file("src/generated/resources/").getAbsolutePath(), "--existing", file("src/main/resources/").getAbsolutePath())
        }
    }

    mods {
        // define mod <-> source bindings
        // these are used to tell the game which sources are for which mod
        // mostly optional in a single mod project
        // but multi mod projects should define one per mod
        register(modId) {
            sourceSet(sourceSets.main.get())
        }
    }
}



// Sets up a dependency configuration called "localRuntime".
// This configuration should be used instead of "runtimeOnly" to declare
// a dependency that will be present for runtime testing but that is
// "optional", meaning it will not be pulled by dependents of this mod.
//configurations {
//    val localRuntime by creating {}
//    named("runtimeClasspath").extendsFrom(localRuntime)
//}

dependencies {
    // Since Kotlin is its own JVM language with its own standard library, a custom language loader needs to be used, i.e. KFF
    implementation(libs.kff)
}

// This block of code expands all declared replace properties in the specified resource targets.
// A missing property will result in an error. Properties are expanded using ${} Groovy notation.
val generateModMetadata = tasks.register<ProcessResources>("generateModMetadata") {
    val modName: String by project
    val modLicense: String by project
    val modAuthors: String by project
    val modDescription: String by project
    val replaceProperties = mapOf(
            "minecraft_version"       to libs.versions.minecraft,
            "minecraft_version_range" to libs.versions.minecraftRange,
            "neo_version"             to libs.versions.neoforge,
            "neo_version_range"       to libs.versions.neoforgeRange,
            "kff_version"             to libs.versions.kff,
            "kff_version_range"       to libs.versions.kffRange,
            "loader_version_range"    to libs.versions.loaderRange,
            "mod_id"                  to modId,
            "mod_name"                to modName,
            "mod_license"             to modLicense,
            "mod_version"             to modVersion,
            "mod_authors"             to modAuthors,
            "mod_description"         to modDescription
    )
    inputs.properties(replaceProperties)
    expand(replaceProperties)
    from("src/main/templates")
    into("build/generated/sources/modMetadata")
}

sourceSets {
    main {
        resources {
            // Include resources generated by data generators.
            srcDir("src/generated/resources")
            // Include the output of "generateModMetadata" as an input directory for the build
            // this works with both building through Gradle and the IDE.
            srcDir(generateModMetadata)
        }
    }
}
// To avoid having to run "generateModMetadata" manually, make it run on every project reload
neoForge.ideSyncTask(generateModMetadata)

// Example configuration to allow publishing using the maven-publish plugin
publishing {
    publications {
        register<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        maven(url = "file://${project.projectDir}/repo")
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8" // Use the UTF-8 charset for Java compilation
}

// IDEA no longer automatically downloads sources/javadoc jars for dependencies, so we need to explicitly enable the behavior.
idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}
