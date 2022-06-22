import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
    id("com.google.cloud.tools.jib") version "3.2.1"
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_11

/** Version */
val springMockkVersion = "3.1.1"
val kotlinLoggingVersion = "2.1.20"
val microMeterVersion = "1.9.0"

subprojects {
    group = "kr.mashup.ladder"

    repositories {
        mavenCentral()
    }

    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java-library")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "com.google.cloud.tools.jib")

    dependencies {
        // kotlin
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

        // kotlin logging
        implementation("io.github.microutils:kotlin-logging-jvm:${kotlinLoggingVersion}")

        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("io.micrometer:micrometer-registry-prometheus:${microMeterVersion}")

        // Spring Test
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        // Spring MockK
        testImplementation("com.ninja-squad:springmockk:${springMockkVersion}")
    }

    tasks.create("deploy") {
        dependsOn("jib")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

