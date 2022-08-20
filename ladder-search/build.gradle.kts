dependencies {
    implementation(project(":ladder-common"))

    // Spring Webflux
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    // Spring Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    // Micrometer Registry prometheus
    implementation("io.micrometer:micrometer-registry-prometheus:1.9.0")

    // swagger
    implementation("io.springfox:springfox-boot-starter:3.0.0")
    implementation("io.springfox:springfox-swagger-ui:3.0.0")
    implementation("io.springfox:springfox-spring-webflux:3.0.0")
}

jib {
    from {
        image = "adoptopenjdk/openjdk11:alpine-jre"
    }
    to {
        image = System.getProperty("image", "mashupladder/ladder-search")
        tags = setOf(System.getProperty("tag", "latest"))
    }
    container {
        jvmFlags = listOf(
            "-Duser.timezone=Asia/Seoul"
        )
        ports = listOf("9001")
    }
}
