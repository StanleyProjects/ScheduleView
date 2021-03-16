buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpathAll(
            Dependency.androidToolsBuildGradle,
            Dependency.kotlinGradlePlugin
        )
    }
}

task<DefaultTask>("verifyLicense") {
    doLast {
        val file = File(rootDir, "LICENSE")
        val text = file.requireFilledText()
        // todo
    }
}

task<DefaultTask>("verifyReadme") {
    doLast {
        val file = File(rootDir, "README.md")
        val text = file.requireFilledText()
        val projectCommon = MarkdownUtil.table(
            heads = listOf("Android project common", "version"),
            dividers = listOf("-", "-:"),
            rows = listOf(
                listOf("build gradle", "`${Version.Android.toolsBuildGradle}`"),
                listOf("compile sdk", "`${Version.Android.compileSdk}`"),
                listOf("build tools", "`${Version.Android.buildTools}`"),
                listOf("min sdk", "`${Version.Android.minSdk}`"),
                listOf("target sdk", "`${Version.Android.targetSdk}`")
            )
        )
        setOf(projectCommon).forEach {
            check(text.contains(it)) { "File by path ${file.absolutePath} must contains \"$it\"!" }
        }
        val lines = text.split(SystemUtil.newLine)
        val versionBadge = MarkdownUtil.image(
            text = "version",
            url = BadgeUtil.url(
                label = "version",
                message = VersionUtil.fullByName(Version.Name.lib),
                color = "2962ff"
            )
        )
        val bintrayBadge = MarkdownUtil.url(
            text = MarkdownUtil.image(
                text = "",
                url = "https://api.bintray.com/packages/${MavenUtil.groupId}/${Application.Id.lib}/images/download.svg"
            ),
            url = "https://bintray.com/${MavenUtil.groupId}/${Application.Id.lib}/_latestVersion"
        )
        setOf(
            versionBadge,
            bintrayBadge
        ).forEach {
            check(lines.contains(it)) { "File by path ${file.absolutePath} must contains \"$it\" line!" }
        }
    }
}

task<DefaultTask>("verifyService") {
    doLast {
        val file = File(rootDir, "buildSrc/build.gradle.kts")
        val text = file.requireFilledText()
        listOf(
            Dependency.androidToolsBuildGradle.notation(),
            "id(\"${Plugin.kotlinDsl.name}\") version \"${Plugin.kotlinDsl.version}\""
        ).forEach {
            check(text.contains(it)) { "File by path ${file.absolutePath} must contains \"$it\"!" }
        }

        val forbiddenFileNames = setOf(".DS_Store")
        rootDir.onFileRecurse {
            if (!it.isDirectory) {
                check(!forbiddenFileNames.contains(it.name)) {
                    "File by path ${it.absolutePath} must not be exists!"
                }
            }
        }
    }
}

task<Delete>("clean") {
    delete = setOf(rootProject.buildDir, File(rootDir, "buildSrc/build"))
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}
