plugins {
    id("lib")
}

android {
    namespace = "com.drodobyte.core.data"
}

dependencies {
    implementation(project(":core:data-local"))
    implementation(project(":core:data-remote"))
}
