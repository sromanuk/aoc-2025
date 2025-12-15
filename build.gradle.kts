plugins {
    kotlin("jvm") version "2.2.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "9.2.1"
    }
    withType<JavaExec> {
        jvmArgs = listOf("-Xmx64g")
    }
}
