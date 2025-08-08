plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "org.drinkless.td.libcore.telegram"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testOptions.targetSdk = 35

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    signingConfigs {
        create("nf") {
            storeFile = file("./loja.jks")
            //noinspection GroovyAssignabilityCheck
            keyAlias = "LojaKey"
            //noinspection GroovyAssignabilityCheck
            storePassword = "loja0221"
            //noinspection GroovyAssignabilityCheck
            keyPassword = "loja0221"
            enableV2Signing = true
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("nf")
            isMinifyEnabled = true
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
        debug {
            signingConfig = signingConfigs.getByName("nf")
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(fileTree("libs"))
    implementation(libs.bundles.all)
}