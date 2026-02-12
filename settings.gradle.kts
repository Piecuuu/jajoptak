import java.util.Locale

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://repo.papermc.io/repository/maven-public/")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

if (!file(".git").exists()) {
    // Leaf start - project setup // piecuu start - Rebrand
    val errorText = """
        
        =====================[ ERROR ]=====================
         The jajoptak project directory is not a properly cloned Git repository.
         
         In order to build jajoptak from source you must clone
         the jajoptak repository using Git, not download a code
         zip from GitHub.
         
         See https://github.com/PaperMC/Paper/blob/main/CONTRIBUTING.md
         for further information on building and modifying Paper forks.
        ===================================================
    """.trimIndent()
    // Leaf end - project setup // piecuu end - Rebrand
    error(errorText)
}

rootProject.name = "leaf"

for (name in listOf("leaf-api", "leaf-server")) {
    val projName = name.lowercase(Locale.ENGLISH)
    include(projName)
}
