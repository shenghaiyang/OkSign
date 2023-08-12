plugins {
    kotlin("jvm") version "1.8.10"
}

repositories {
    google()
    mavenCentral()
}


dependencies {
    implementation("com.android.tools.build:gradle-api:8.1.0")
    implementation(kotlin("stdlib"))
    gradleApi()
}
