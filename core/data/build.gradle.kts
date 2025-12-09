plugins {
    id("lib")
}

android {
    namespace = "com.drodobyte.core.data"
}

dependencies {
    implementation(libs.gson)
    implementation(project(":core:data-local"))
    implementation(project(":core:data-remote"))
}
