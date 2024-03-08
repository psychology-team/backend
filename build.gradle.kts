plugins {
    java
    id("org.springframework.boot") version "3.2.3"
    id("io.spring.dependency-management") version "1.1.4"
    id("io.freefair.lombok") version "8.4"
}

group = "com.psychology.product"
version = "0.0.1"

springBoot {
    mainClass.set("com.psychology.product.ProductApplication")
}

repositories {
    mavenCentral()
}

sourceSets {
    val dev by creating {
        compileClasspath += main.get().output
        runtimeClasspath += main.get().output
    }
}

val devImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    // Spring
    implementation(platform(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES))
    implementation("org.springframework.boot", "spring-boot-starter-jdbc")
    implementation("org.springframework.boot", "spring-boot-starter-data-jpa")
    implementation("org.springframework.boot", "spring-boot-starter-security")
    implementation("org.springframework.boot", "spring-boot-starter-web")
    implementation("org.springframework.boot", "spring-boot-starter-tomcat")
    implementation("org.springframework", "spring-context-support")
    implementation("org.springdoc", "springdoc-openapi-starter-webmvc-ui", "2.2.0")
    developmentOnly("org.springframework.boot", "spring-boot-devtools")
    developmentOnly("org.springframework.boot", "spring-boot-docker-compose")
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.springframework.security", "spring-security-test")

    // Validation
    implementation("jakarta.validation", "jakarta.validation-api", "3.1.0-M1")

    // Database
    implementation("org.postgresql", "postgresql", "42.7.1")
    runtimeOnly("org.postgresql", "postgresql")

    // Utilities
    implementation("org.jetbrains", "annotations", "24.1.0")
    implementation("org.springdoc",  "springdoc-openapi-starter-webmvc-ui",  "2.3.0")

    // Testing
    testImplementation("org.springframework.boot", "spring-boot-starter-test")
    testImplementation("org.springframework.boot", "spring-boot-testcontainers")
    testImplementation("org.testcontainers", "postgresql", "1.19.3")
    testImplementation("org.testcontainers", "junit-jupiter", "1.19.3")
    testImplementation("org.junit.jupiter", "junit-jupiter-api", "5.10.1")
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", "5.10.1")
    testImplementation("org.mockito", "mockito-junit-jupiter", "5.8.0")
    implementation("org.testng", "testng", "7.7.0")

    devImplementation("org.testcontainers", "postgresql", "1.19.3")
}

tasks {
    compileJava {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    bootJar {
        archiveFileName.set("server.jar")
    }

    create("stage") {
        dependsOn("bootJar")
    }

    create<org.springframework.boot.gradle.tasks.run.BootRun>("bootDevRun") {
        group = "application"
        mainClass.set("com.psychology.product.DevelopmentLauncher")
        classpath(sourceSets["dev"].runtimeClasspath)
        jvmArgs("-Dspring.profiles.active=development")
    }

    test {
        useJUnitPlatform()
        jvmArgs("-Dspring.profiles.active=testing")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
