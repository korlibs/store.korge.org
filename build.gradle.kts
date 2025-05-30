import korlibs.io.file.*
import korlibs.io.file.std.*
import korlibs.io.net.*
import kotlinx.coroutines.*
import org.yaml.snakeyaml.*
import java.time.*
import java.util.*

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("org.yaml:snakeyaml:2.0")
        classpath("com.soywiz.korlibs.korio:korio-jvm:4.0.7")
    }
}


fun downloadUrlText(url: String): String {
    return runBlocking { UrlVfs(URL(url)).readString() }
}

val yaml = Yaml(DumperOptions().also {
    it.isPrettyFlow = true
    it.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
})

val FRONTMATTER_REGEX = Regex("---\n(.*)\n---", RegexOption.DOT_MATCHES_ALL)

fun extractFrontMatter(file: File): MutableMap<String, Any?> {
    val content = FRONTMATTER_REGEX.find(file.readText())
    val yamlContent = content?.groupValues?.get(1) ?: error("Can't find frontmatter ('---')")
    return yaml.load(yamlContent) as MutableMap<String, Any?>
}

fun updateFrontMatter(file: File, data: Map<String, Any?>) {
    file.writeText(FRONTMATTER_REGEX.replace(file.readText(), "---\n" + yaml.dump(data).trim() + "\n---"))
}

val GITHUB_TREE_URL_REGEX = Regex("https://github.com/(.*?)/(.*?)/tree/(.*?)/(.*?)")

fun getRepoUrl(org: String, repo: String): String {
    return "https://github.com/$org/$repo.git"
}

fun getLocalGitRepoFolder(org: String, repo: String): File {
    return File("${System.getProperty("user.home")}/.kproject/clones/github.com/${File(org).name}/${File(repo).name}/__git__")
}

fun ensureGitRepo(org: String, repo: String): File {
    val localGitRepoFolder = getLocalGitRepoFolder(org, repo)
    localGitRepoFolder.parentFile.mkdirs()
    if (!localGitRepoFolder.exists()) {
        ProcessBuilder().inheritIO()
            .command("git", "clone", getRepoUrl(org, repo), localGitRepoFolder.absolutePath)
            .start().waitFor()
    }
    ProcessBuilder().inheritIO()
        .command("git", "pull")
        .directory(localGitRepoFolder)
        .start().waitFor()
    return localGitRepoFolder
}

data class TagInfo(
    val commitId: String,
    val tagId: String,
    val date: Long,
    val korgeVersion: String?,
)

fun getKorgeVersionFromLibsTomlFileOrNull(content: String): String? {
    for (line in content.lines()) {
        if (line.trim().startsWith("korge") && line.contains("com.soywiz.korge")) {
            Regex("version\\s*=\\s*['\"](.*)['\"]").find(line)?.let { match ->
                return match.groupValues[1]
            }
        }
    }
    return null
}

fun getKorgeVersionFromBuildGradleKts(content: String): String? {
    //id("com.soywiz.korge") version "4.0.6"
    for (line in content.lineSequence().map { it.trim() }) {
        if (line.startsWith("id(") && line.contains("com.soywiz.korge")) {
            Regex("version\\s*['\"](.*)['\"]").find(line)?.let { match ->
                return match.groupValues[1]
            }
        }
    }
    return null
}

fun getRepoTags(org: String, repo: String): List<TagInfo> {
    fun command(vararg args: String): String {
        return ProcessBuilder()
            .command(*args)
            .directory(getLocalGitRepoFolder(org, repo))
            .start().inputStream.readBytes().toString(Charsets.UTF_8)
    }

    val lines = command("git", "log", "--no-walk", "--tags", "--pretty=%H:::::%d:::::%at:::::%s", "--decorate=full", "--date-order")
        .lines()
        .map { it.trim() }
        .filter { it.isNotBlank() }

    val map = lines.map {
        val (commitId, refInfo, date, message) = it.split(":::::")
        val tagId = Regex("refs/tags/(.*?)(,|\\)|\$)").find(refInfo)?.groupValues?.get(1) ?: error("Can't find tagId in '$refInfo'")
        val korgeVersion = getKorgeVersionFromLibsTomlFileOrNull(command("git", "show",  "${commitId}:gradle/libs.versions.toml"))
            ?: getKorgeVersionFromBuildGradleKts(command("git", "show",  "${commitId}:build.gradle.kts"))

        TagInfo(commitId, tagId, date.toLong() * 1000L, korgeVersion)
    }

    return map
}

//getRepoTags(org, repo)

//fun getRepoTags2(org: String, repo: String): Map<String, String> {
//    val tags = Json.parse(downloadUrlText("https://api.github.com/repos/$org/$repo/tags")).dyn
//    return tags.list.associate { it["name"].str to it["commit"]["sha"].str }
//}

//fun getCommitDate(org: String, repo: String, ref: String): String {
//    val info = Json.parse(downloadUrlText("https://api.github.com/repos/$org/$repo/commits/$ref")).dyn
//    //println(info)
//    return info["commit"]["committer"]["date"].str
//}

fun addTagToMap(data: MutableMap<String, Any?>, vtag: TagInfo) {
// Modify the data structure as needed
    if ("tags" !in data || data["tags"] !is MutableList<*>) {
        data["tags"] = arrayListOf<Any?>()
    }
    if ("dates" !in data || data["dates"] !is MutableMap<*, *>) {
        data["dates"] = mutableMapOf<String, String>()
    }
    if ("newtags" !in data || data["newtags"] !is MutableMap<*, *>) {
        data["newtags"] = mutableMapOf<String, Any?>()
    }
    val tags = (data["tags"] as MutableList<Map<String, String>>)
    val newtags = (data["newtags"] as MutableMap<String, Map<String, Any?>>)
    var exists = false

    newtags[vtag.tagId] = mapOf(
        "commitId" to vtag.commitId,
        "korgeVersion" to vtag.korgeVersion,
        "date" to Instant.ofEpochMilli(vtag.date).toString()
    )

    for (_tag in tags) {
        if (vtag.tagId in _tag) {
            exists = true
            break
        }
    }
    if (!exists) {
        println("Added tag ${vtag.tagId} -> ${vtag.commitId}")
        tags.add(mapOf(vtag.tagId to vtag.commitId))
    } else {
        println("Existing tag ${vtag.tagId} -> ${vtag.commitId}")
    }

    val dates = data["dates"] as MutableMap<String, String>
    if (vtag.commitId !in dates) {
        dates[vtag.commitId] = Instant.ofEpochMilli(vtag.date).toString()
    }
    println(" -> ${dates[vtag.commitId]}")
}

fun addLinks(vararg args: String) {

    if (args.isEmpty()) {
        println("Pass as a parameter a URL like:")
        println(" - https://github.com/korlibs/korge-jitto/tree/0.0.3/korge-jitto")
        System.exit(-1)
    }
    var rargs = args.toList()
    rargs = rargs.flatMap {
        if (it.startsWith("@")) {
            File(it.substring(1)).readLines()
        } else {
            listOf(it)
        }
    }

    for (url in rargs) {
        val match = GITHUB_TREE_URL_REGEX.matchEntire(url) ?: error("URL '$url' doesn't match <$GITHUB_TREE_URL_REGEX>")

        val org = PathInfo(match.groupValues[1]).baseName
        val repo = PathInfo(match.groupValues[2]).baseName
        val ref = PathInfo(match.groupValues[3]).baseName
        val path = "/" + PathInfo(match.groupValues[4]).normalize()

        println("org: $org, repo: $repo, ref: $ref, path: $path")

        val mainModuleFile = File("./_modules/github.com/$org/$repo/$path.md")
        val repotagsFile = File("./_repotags/github.com/$org/$repo/github.com-$org-$repo.json")

        println("file: $mainModuleFile")

        ensureGitRepo(org, repo)

        if (!mainModuleFile.exists()) {
            mainModuleFile.parentFile.mkdirs()
            mainModuleFile.writeText(
                """
        ---
        layout: module
        title: ${PathInfo(path).baseName}
        authors: [${org}]
        category: Other
        link: https://github.com/$org/$repo/tree/main$path
        ---
    """.trimIndent()
            )
        }
        if (!repotagsFile.exists()) {
            repotagsFile.parentFile.mkdirs()
            repotagsFile.writeText(
                """
        ---
        layout: repotag
        title: github.com-$org-$repo
        tags:
        ---
    """.trimIndent()
            )
        }

//for (file in File("_modules").walkTopDown()) {
//    if (file.name.endsWith(".md")) {
//        println("file=$file")
//        val data = extractFrontMatter(file)
//        data.remove("tags")
//        updateFrontMatter(file, data)
//    }
//}
//System.exit(-1)

        val data = extractFrontMatter(repotagsFile)
        data["tags"] = arrayListOf<Any?>()
        data["dates"] = mutableMapOf<String, String>()
        data["newtags"] = mutableMapOf<String, Any?>()
        for (tag in getRepoTags(org, repo).sortedByDescending { it.date }) {
            addTagToMap(data, tag)
        }

        updateFrontMatter(repotagsFile, data)
    }
}

//file.writer().use {
//    yaml.dump(data, it) // Save the modified data structure with original formatting
//}

object FrontMatter {
    val yaml = Yaml(DumperOptions().also {
        it.isPrettyFlow = true
        it.defaultFlowStyle = DumperOptions.FlowStyle.BLOCK
    })

    val FRONTMATTER_REGEX = Regex("---\n(.*)\n---", RegexOption.DOT_MATCHES_ALL)

    fun extract(file: File): MutableMap<String, Any?> {
        val content = FRONTMATTER_REGEX.find(file.readText())
        val yamlContent = content?.groupValues?.get(1) ?: error("Can't find frontmatter ('---') in $file")
        return yaml.load(yamlContent) as MutableMap<String, Any?>
    }
}

tasks {
    val updateAll by creating {
        group = "links"
        doLast {
            val links = File("./_modules").walkBottomUp().filter { it.name.endsWith(".md") }.map { FrontMatter.extract(it)["link"] }.toList()

            addLinks(*links.map { it.toString() }.toTypedArray())
        }
    }

    val addLinks by creating {
        group = "links"
        doLast {
            val linkToAdd = project.findProperty("link.to.add")?.toString()
            if (linkToAdd.isNullOrBlank()) {
                val scanner = Scanner(System.`in`)
                while (true) {
                    println("Type a link and press return (for example https://github.com/korlibs/korge-jitto/tree/0.0.3/korge-jitto):")
                    val line = scanner.nextLine().trim()
                    if (line.isEmpty()) break
                    addLinks(line)
                }
            } else {
                addLinks(linkToAdd)
            }
            //println(System.`in`.reader().readText())
            //System.`in`.reader().forEachLine { println("READ: $it") }
        }
    }
}
