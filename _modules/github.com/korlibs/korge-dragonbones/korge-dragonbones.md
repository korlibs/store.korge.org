---
layout: module
title: DragonBones
authors: [korlibs]
category: Skeletal
link: https://github.com/korlibs/korge-dragonbones/tree/main/korge-dragonbones
icon: /i/dragonbones.png
---

Examples:
* <https://github.com/korlibs/korge-dragonbones/blob/0.0.4/example/src/commonMain/kotlin/MainDragonbones.kt>

```kotlin
val skeDeferred = async { Buffer(res["mecha_1002_101d_show/mecha_1002_101d_show_ske.dbbin"].readBytes()) }
val texDeferred = async { res["mecha_1002_101d_show/mecha_1002_101d_show_tex.json"].readString() }
val imgDeferred = async { res["mecha_1002_101d_show/mecha_1002_101d_show_tex.png"].readBitmap().mipmaps() }

val data = factory.parseDragonBonesData(skeDeferred.await())
val atlas = factory.parseTextureAtlasData(Json.parseFast(texDeferred.await())!!, imgDeferred.await())

val armatureDisplay = factory.buildArmatureDisplay("mecha_1002_101d")!!.position(0, 300).scale(SCALE)

println(armatureDisplay.animation.animationNames)
armatureDisplay.animation.play("idle")
this += armatureDisplay
```
