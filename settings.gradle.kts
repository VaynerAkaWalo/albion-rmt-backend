dependencyResolutionManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/VaynerAkaWalo/shared-assets")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }

    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}

pluginManagement {
    repositories {
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
        gradlePluginPortal()
    }
}

rootProject.name = "albion-rmt-backend"
