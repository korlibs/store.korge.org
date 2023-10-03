#!/usr/bin/env kotlin

//@Repository("")
@file:Repository("https://repo1.maven.org/maven2/")
@file:DependsOn("org.yaml:snakeyaml:2.0")
@file:DependsOn("com.soywiz.korlibs.korio:korio-jvm:4.0.7")

import korlibs.memory.*
import org.yaml.snakeyaml.DumperOptions
import org.yaml.snakeyaml.Yaml
import java.io.File

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

val links = File("./_modules").walkBottomUp().filter { it.name.endsWith(".md") }.map { FrontMatter.extract(it)["link"] }.toList()

File("args.txt").writeText(links.joinToString("\n"))

ProcessBuilder().inheritIO()
    .command(*buildList {
        if (Platform.isWindows) {
            addAll(listOf("cmd", "/c", "kotlin"))
            add("addlink.main.kts")
        } else {
            add("./addlink.main.kts")
        }
        add("@args.txt")
        //addAll(links.map { it.toString() })
    }.toTypedArray())
    .start()
    .waitFor()

/*
for (file in File("./_modules").walkBottomUp()) {
    if (!file.name.endsWith(".md")) continue
    val data = FrontMatter.extract(file)
    println(file)
    ProcessBuilder().inheritIO()
        .command("./addlink.main.kts", data["link"].toString())
        .start()
        .waitFor()
}

 */
