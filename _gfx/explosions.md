---
layout: gfx
title: Explosions GFX
authors: [Cuzco]
enabled: true
screenshot: https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/explosion/exp2.jpg
show_screenshot: false
---

## Explosions

![](https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/explosion/exp2.jpg)

{% include asset.html name="explosions" prefix="gfx" url="https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/explosion/exp2.jpg" %}

```kotlin
val animation = SpriteAnimation(
    resourcesVfs["gfx/exp2.jpg"].readBitmapSlice().splitInRows(64, 64),
    60.milliseconds
)

val random = Random(0L)
interval(0.02.seconds) {
    sprite(animation).xy(random[0, 250], random[0, 250]).also { it.blendMode = BlendMode.SCREEN }
        .also { sprite -> sprite.onAnimationCompleted { sprite.removeFromParent() } }
        .playAnimation()
}
```