plugins {
    kotlin("jvm") version "2.1.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:6.1.0")
    implementation("io.github.kotlin-telegram-bot.kotlin-telegram-bot:telegram:x.y.z")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(22)
}