pluginManagement {
    repositories {
        if(System.getenv("USE_MIRROR") == "true"){
            maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        if(System.getenv("USE_MIRROR") == "true"){
            maven { url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/") }
        }
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io") }
        jcenter()
    }
}

rootProject.name = "td"
include(":libtd")
 