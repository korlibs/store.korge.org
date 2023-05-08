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

fun addTagToMap(data: MutableMap<String, Any?>, tag: String, commitId: String) {
// Modify the data structure as needed
    if ("tags" !in data || data["tags"] !is MutableList<*>) {
        data["tags"] = arrayListOf<Any?>()
    }
    val tags = (data["tags"] as MutableList<Map<String, String>>)
    for (_tag in tags) {
        if (tag in _tag) return
    }
    println("Added tag $tag -> $commitId")
    tags.add(mapOf(tag to commitId))
}

fun getRepoTags(org: String, repo: String): Map<String, String> {
    val tags = Json.parse(downloadUrlText("https://api.github.com/repos/$org/$repo/tags")).dyn
    return tags.list.associate { it["name"].str to it["commit"]["sha"].str }
}

if (!mainModuleFile.exists()) {
    mainModuleFile.parentFile.mkdirs()
    mainModuleFile.writeText("""
        ---
        layout: module
        title: ${PathInfo(path).baseName}
        tags:
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
for (tag in getRepoTags(org, repo)) {
    addTagToMap(data, tag.key, tag.value)
}
updateFrontMatter(repotagsFile, data)



//file.writer().use {
//    yaml.dump(data, it) // Save the modified data structure with original formatting
//}