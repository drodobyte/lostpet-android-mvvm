plugins {
    id("lib")
}

android {
    namespace = "com.drodobyte.core.data.remote"
}

dependencies {
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp.cookie)
}
