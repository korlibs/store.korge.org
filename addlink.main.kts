#!/usr/bin/env kotlin

//@Repository("")
@file:Repository("https://repo1.maven.org/maven2/")
@file:DependsOn("org.yaml:snakeyaml:2.0")
@file:DependsOn("com.soywiz.korlibs.korio:korio-jvm:4.0.0-rc5")

import korlibs.io.dynamic.dyn
import korlibs.io.file.PathInfo
import korlibs.io.file.baseName
import korlibs.io.file.normalize
import korlibs.io.file.std.UrlVfs
import korlibs.io.net.URL
import korlibs.io.serialization.json.Json
import kotlinx.coroutines.runBlocking
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.time.Instant

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

if (args.isEmpty()) {
    println("Pass as a parameter a URL like:")
    println(" - https://github.com/korlibs/korge-jitto/tree/0.0.3/korge-jitto")
    System.exit(-1)
}

val url = args[0]
val match = GITHUB_TREE_URL_REGEX.matchEntire(url) ?: error("URL '$url' doesn't match <$GITHUB_TREE_URL_REGEX>")

val org = PathInfo(match.groupValues[1]).baseName
val repo = PathInfo(match.groupValues[2]).baseName
val ref = PathInfo(match.groupValues[3]).baseName
val path = "/" + PathInfo(match.groupValues[4]).normalize()


println("org: $org, repo: $repo, ref: $ref, path: $path")

val mainModuleFile = File("./_modules/github.com/$org/$repo/$path.md")
val repotagsFile = File("./_repotags/github.com/$org/$repo/github.com-$org-$repo.json")

println("file: $mainModuleFile")

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

ensureGitRepo(org, repo)

data class TagInfo(
    val commitId: String,
    val tagId: String,
    val date: Long
)

fun getRepoTags(org: String, repo: String): List<TagInfo> {
    val lines = ProcessBuilder()
        .command("git", "log", "--no-walk", "--tags", "--pretty=%H:::::%d:::::%at:::::%s", "--decorate=full", "--date-order")
        .directory(getLocalGitRepoFolder(org, repo))
        .start().inputStream.readAllBytes().toString(Charsets.UTF_8)
        .lines()
        .map { it.trim() }
        .filter { it.isNotBlank() }

    val map = lines.map {
        val (commitId, refInfo, date, message) = it.split(":::::")
        val tagId = Regex("refs/tags/(.*?)(,|\\)|\$)").find(refInfo)?.groupValues?.get(1) ?: error("Can't find tagId in '$refInfo'")
        TagInfo(commitId, tagId, date.toLong() * 1000L)
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
    val tags = (data["tags"] as MutableList<Map<String, String>>)
    var exists = false
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

if (!mainModuleFile.exists()) {
    mainModuleFile.parentFile.mkdirs()
    mainModuleFile.writeText("""
        ---
        layout: module
        title: ${PathInfo(path).baseName}
        authors: [${org}]
        link: https://github.com/$org/$repo/tree/main$path
        ---
    """.trimIndent())
}
if (!repotagsFile.exists()) {
    repotagsFile.parentFile.mkdirs()
    repotagsFile.writeText("""
        ---
        layout: repotag
        title: github.com-$org-$repo
        tags:
        ---
    """.trimIndent())
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
for (tag in getRepoTags(org, repo)) {
    addTagToMap(data, tag)
}
updateFrontMatter(repotagsFile, data)



//file.writer().use {
//    yaml.dump(data, it) // Save the modified data structure with original formatting
//}