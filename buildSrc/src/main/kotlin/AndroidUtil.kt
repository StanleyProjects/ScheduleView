import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import com.android.build.gradle.internal.dsl.SigningConfig
import java.io.File
import java.util.Properties
import org.gradle.api.Project

fun BaseExtension.defaultConfig(
    compileSdkVersion: Int,
    buildToolsVersion: String,
    minSdkVersion: Int,
    targetSdkVersion: Int,
    block: DefaultConfig.() -> Unit
) {
    compileSdkVersion(compileSdkVersion)
    buildToolsVersion(buildToolsVersion)
    defaultConfig {
        minSdkVersion(minSdkVersion)
        targetSdkVersion(targetSdkVersion)
        block()
    }
    sourceSets.all {
        java.srcDir("src/$name/kotlin")
    }
}

fun BaseExtension.commonConfig(
    block: DefaultConfig.() -> Unit = {}
) {
    defaultConfig(
        compileSdkVersion = Version.Android.compileSdk,
        buildToolsVersion = Version.Android.buildTools,
        minSdkVersion = Version.Android.minSdk,
        targetSdkVersion = Version.Android.targetSdk,
        block = block
    )
}

fun SigningConfig.defaultConfig(project: Project, properties: Properties) {
    val file = File(project.projectDir, "$name.jks")
    require(file.exists()) { "Keystore file must be exists by full path ${file.absolutePath}!" }
    val storePasswordKey = name + "_store_password"
    val keyAliasKey = name + "_key_alias"
    val keyPasswordKey = name + "_key_password"
    try {
        storeFile = file
        storePassword = properties[storePasswordKey] as String
        keyAlias = properties[keyAliasKey] as String
        keyPassword = properties[keyPasswordKey] as String
    } catch (ignored: Throwable) {
        error("You should define $storePasswordKey, $keyAliasKey and $keyPasswordKey in ${file.name}")
    }
}
