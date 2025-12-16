plugins {
    id("lib")
}

android {
    namespace = "com.drodobyte.core.data.remote"
}

dependencies {
    realImplementation(libs.retrofit)
    realImplementation(libs.retrofit.gson)
    realImplementation(libs.cookie.store)
    realImplementation(libs.cookie.store.http)
}
