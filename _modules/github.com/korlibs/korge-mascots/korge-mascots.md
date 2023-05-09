---
layout: module
title: "Koral & Gest"
authors: [korlibs]
category: Assets
link: https://github.com/korlibs/korge-mascots/tree/main/korge-mascots
icon: https://raw.githubusercontent.com/korlibs/korge-mascots/b9a7ad7e6dcd51cdc61b41b57e86a9452c872e55/3d/korge-models/Pose_2.jpg?raw=true
---

Load Dragonbones models for KorGE mascots Koral & Gest.

```kotlin
val db = KorgeDbFactory()
db.loadKorgeMascots()

val koral = db.buildArmatureDisplayKoral()
    !!.position(100, 490)
    .scale(SCALE)
    .addTo(this)
    .also { it.animation.play(KorgeMascotsAnimations.IDLE) }
```