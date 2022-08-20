dependencies {
    implementation(project(":ladder-domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // Spring Session Redis
    implementation("org.springframework.session:spring-session-data-redis")

    // rest-assured
    testImplementation("io.rest-assured:rest-assured:4.4.0")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Spring Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Micrometer Registry prometheus
    implementation("io.micrometer:micrometer-registry-prometheus:1.9.0")

    // swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")

    // Sentry
    implementation("io.sentry:sentry-spring-boot-starter:6.4.0")
    implementation("io.sentry:sentry-logback:6.4.0")
}

jib {
    from {
        image = "adoptopenjdk/openjdk11:alpine-jre"
    }
    to {
        image = System.getProperty("image", "mashupladder/ladder-api")
        tags = setOf(System.getProperty("tag", "latest"))
    }
    container {
        jvmFlags = listOf(
            "-Duser.timezone=Asia/Seoul"
        )
        ports = listOf("9000")
    }
}
