@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.vanniktech.mavenPublish)
    alias(libs.plugins.kotlin.serialization)
    signing
}

group = "com.parodison"
version = "0.1.0"

kotlin {
    jvm()
    androidLibrary {
        namespace = "com.parodison.orbit.core"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava() // enable java compilation support
        withHostTestBuilder {}.configure {}
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }

        compilerOptions {
            jvmTarget = JvmTarget.JVM_11
        }
    }
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
    js {
        binaries.library()
        useEsModules()
        nodejs()
        browser()
        generateTypeScriptDefinitions()
    }
    wasmJs {
        browser()
        binaries.library()
    }


    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()

    signAllPublications()

    coordinates(group.toString(), "orbit-core", version.toString())

    pom {
        name = "Orbit Core"
        description = "Kotlin Multiplatform library for satellite orbit propagation (SGP4/SDP4) with pass prediction and GeoJSON output"
        inceptionYear = "2026"
        url = "https://github.com/Parodison/orbit-core"
        licenses {
            license {
                name = "Apache-2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "repo"
            }
        }
        developers {
            developer {
                id = "parodison"
                name = "Rodrigo Parodi"
                url = "https://parodison.com"
            }
        }
        scm {
            url = "https://github.com/Parodison/orbit-core"
            connection = "scm:git:git://github.com/Parodison/orbit-core.git"
            developerConnection = "scm:git:ssh://git@github.com/Parodison/orbit-core.git"
        }
    }
}

signing {
    useGpgCmd()
}
