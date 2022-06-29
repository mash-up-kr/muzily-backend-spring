dependencies {
    implementation(project(":ladder-domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // Spring Session Redis
    implementation("org.springframework.session:spring-session-data-redis")

    // TODO : remove this
    implementation("org.webjars:webjars-locator-core")
    implementation("org.webjars:sockjs-client:1.0.2")
    implementation("org.webjars:stomp-websocket:2.3.3")
    implementation("org.webjars:bootstrap:3.3.7")
    implementation("org.webjars:jquery:3.1.1-1")

    // rest-assured
    testImplementation("io.rest-assured:rest-assured:4.4.0")

    implementation("org.springframework.boot:spring-boot-starter-webflux")
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
