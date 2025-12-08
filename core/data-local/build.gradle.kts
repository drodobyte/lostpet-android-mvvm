plugins {
    id("lib")
}

android {
    namespace = "com.drodobyte.core.data.local"
}

dependencies {
    implementation(libs.gson)
    implementation(libs.androidx.datastore)
}
