tasks.bootJar {
    enabled = false
}

tasks.jar {
    enabled = true
}

allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}

dependencies {
    // jpa
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // h2
    runtimeOnly("com.h2database:h2")
}
