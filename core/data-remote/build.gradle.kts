plugins {
    id("lib")
}

android {
    namespace = "com.drodobyte.core.data.remote"
}

dependencies {
    releaseImplementation(libs.retrofit)
    releaseImplementation(libs.retrofit.gson)
    releaseImplementation(libs.okhttp.cookie)
}
