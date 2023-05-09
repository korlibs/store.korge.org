---
layout: module
title: Vampires
authors: [korlibs]
category: Assets
link: https://github.com/korlibs/korge-free-assets/tree/main/finalbossblues-evil-transforming-vampires
icon: https://github.com/korlibs/korge-free-assets/blob/0.0.1/finalbossblues-evil-transforming-vampires/icon.png?raw=true
---

Includes a Vampire and a Vamp spritesheet in .ASE format
and code to load it.

Sprites from: <https://finalbossblues.com/timefantasy/freebies/evil-transforming-vampires/>

```kotlin
val atlas = MutableAtlasUnit(2048, 2048)
val characters = EvilTransformingVampires.readImages(atlas)
val player = imageDataView(characters.vampire, EvilTransformingVampires.Animations.DOWN, playing = true, smoothing = false)
    .scale(4, 4)
    .xy(120, 120)

fun update() {
    val mx = if (input.keys[Key.LEFT]) -1 else if (input.keys[Key.RIGHT]) +1 else 0
    val my = if (input.keys[Key.UP]) -1 else if (input.keys[Key.DOWN]) +1 else 0
    if (mx == 0 && my == 0) player.stop() else player.play()
    when {
        mx < 0 -> player.animation = EvilTransformingVampires.Animations.LEFT
        mx > 0 -> player.animation = EvilTransformingVampires.Animations.RIGHT
        my < 0 -> player.animation = EvilTransformingVampires.Animations.UP
        my > 0 -> player.animation = EvilTransformingVampires.Animations.DOWN
    }
}

addUpdater { update() }
keys {
    downFrame(Key.LEFT, 16.milliseconds) { player.x -= 4 }
    downFrame(Key.RIGHT, 16.milliseconds) { player.x += 4 }
    downFrame(Key.UP, 16.milliseconds) { player.y -= 4 }
    downFrame(Key.DOWN, 16.milliseconds) { player.y += 4 }
}
```