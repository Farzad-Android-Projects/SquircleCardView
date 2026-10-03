plugins {
    alias(libs.plugins.android.library)
    `maven-publish`
}

android {
    namespace = "io.github.farzadski.squirclecardview"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 16
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    implementation("androidx.annotation:annotation:1.11.0")
}

publishing {
    publications {
        register<MavenPublication>("release") {
            groupId = "com.github.Farzad-Android-Projects.SquircleCardView"
            artifactId = "squirclecardview"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }
}