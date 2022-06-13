dependencies {
    implementation(project(":ladder-domain"))

    // spring
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // rest-assured
    testImplementation("io.rest-assured:rest-assured:4.4.0")
}

jib {
    from {
        image = "adoptopenjdk/openjdk11:alpine-jre"
    }
    to {
        image = System.getProperty("image", "ghcr.io/seungh0/ladder-api")
        tags = setOf(System.getProperty("tag", "latest"))
    }
    container {
        jvmFlags = listOf(
            "-Duser.timezone=Asia/Seoul"
        )
        ports = listOf("8000")
    }
}
