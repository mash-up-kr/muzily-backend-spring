import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.0"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
    kotlin("plugin.jpa") version "1.6.21"
}

repositories {
    mavenCentral()
}

java.sourceCompatibility = JavaVersion.VERSION_11

/** Version */
val kotestVersion = "4.4.3"
val springMockkVersion = "3.1.1"

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

    dependencies {
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        testImplementation("org.springframework.boot:spring-boot-starter-test")

        // kotest
        testImplementation("io.kotest:kotest-runner-junit5:${kotestVersion}")
        testImplementation("io.kotest:kotest-extensions-spring:${kotestVersion}")

        // Spring MockK
        testImplementation("com.ninja-squad:springmockk:${springMockkVersion}")
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

