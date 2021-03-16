import java.util.Properties

plugins {
    applyAll(
        Plugin.androidApplication,
        Plugin.kotlinAndroid
    )
}

fun getOutputFileName(
    applicationId: String,
    versionName: String,
    fileExtension: String
): String {
    return applicationId +
        "-" + versionName +
        "." + fileExtension
}

val appName = "Green robot widget ScheduleView sample"
val androidProjectName = name

android {
    commonConfig {
        applicationId = Application.Id.sample
        versionName = Version.Name.sample
        versionCode = VersionUtil.codeByName(versionName)
        vectorDrawables.useSupportLibrary = true
        multiDexEnabled = true
    }

    signingConfigs {
        val properties = Properties().also {
            File(projectDir, "signing.properties").inputStream().use(it::load)
        }
        setOf(
            getByName(BuildType.debug)
        ).forEach { it.defaultConfig(project, properties) }
    }

    buildTypes {
        getByName(BuildType.debug) {
            signingConfig = signingConfigs.getByName(name)
            applicationIdSuffix = ".$name"
            versionNameSuffix = "-$name"
            manifestPlaceholders["appNamePrefix"] = appName
            manifestPlaceholders["appNameBuildTypeSuffix"] = " $name"
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    applicationVariants.all {
        outputs.forEach { output ->
            check(output is com.android.build.gradle.internal.api.ApkVariantOutputImpl)
            output.versionCodeOverride = versionCode
            val outputFileName = getOutputFileName(
                applicationId = applicationId,
                versionName = versionName!!,
                fileExtension = "apk"
            )
            output.outputFileName = outputFileName
        }
    }
}

dependencies {
    implementationProject(":lib")

    implementation(Dependency.kotlinStdlib)
}
