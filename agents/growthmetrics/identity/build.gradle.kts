plugins {
    id("genesis.android.library.hilt")
}

android {
    namespace = "dev.aurakai.auraframefx.agents.growthmetrics.identity"
}

dependencies {
    implementation(libs.androidx.datastore.preferences)
}
