plugins {
    id("lib")
}

android {
    namespace = "com.drodobyte.core.data.local"
}

dependencies {
    implementation(libs.androidx.datastore)
}
