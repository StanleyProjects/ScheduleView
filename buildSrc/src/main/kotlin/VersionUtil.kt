object VersionUtil {
    fun codeByName(name: String): Int {
        val split = name.split(".")
        require(split.size == 3)
        val major = split[0].toIntOrNull() ?: error("Major error!")
        require(major < 1_000)
        val minor = split[1].toIntOrNull() ?: error("Minor error!")
        require(minor < 1_000)
        val patch = split[2].toIntOrNull() ?: error("Patch error!")
        require(patch < 1_000)
        return (1_000_000 * major) + (1_000 * minor) + patch
    }

    fun fullByName(name: String): String {
        return name + "-" + VersionUtil.codeByName(name)
    }
}
