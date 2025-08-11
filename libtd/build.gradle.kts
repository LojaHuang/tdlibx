plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

val jarName = "libtd-nf-1.8.52.0.jar" //x.x.x is td so version

android {
    namespace = "org.drinkless.td.libcore"
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
            //signingConfig = signingConfigs.getByName("nf")
            isMinifyEnabled = true
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
        debug {
            //signingConfig = signingConfigs.getByName("nf")
            isMinifyEnabled = false
        }
    }

    sourceSets["main"].jniLibs.srcDirs("src/main/jniLibs") // 确保识别jniLibs目录

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

tasks.register<Jar>("buildReleaseJar") {
    archiveBaseName.set("libtd-nf")
    archiveVersion.set("1.8.52.0")

    // 依赖 Java 和 Kotlin 编译
    val javaCompile = tasks.getByName("compileReleaseJavaWithJavac") as JavaCompile
    val kotlinCompile = tasks.findByName("compileReleaseKotlin")
    dependsOn(javaCompile, kotlinCompile)

    // 添加 class 文件
    from(javaCompile.destinationDirectory)
    kotlinCompile?.outputs?.files?.let { from(it) }

    // 包含依赖库（生成 FatJar）
//    from(configurations.compileClasspath.get().map {
//        if (it.isDirectory) it else zipTree(it)
//    })

    // 排除签名文件
    exclude("**/*.RSA", "**/*.SF")

    // 设置输出路径
    destinationDirectory.set(layout.buildDirectory.dir("release-jars"))
}

dependencies {
    implementation(fileTree("libs"))
    implementation(libs.bundles.all)
}