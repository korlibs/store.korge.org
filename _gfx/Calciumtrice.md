---
layout: gfx
title: Dungeon assets
authors: [Calciumtrice]
enabled: true
screenshot: https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/wizard_gesture.gif?raw=true
show_screenshot: false
---

Wizard Boy & Girl. Animations: `idle`, `gesture`, `walk`, `attack`, `death`

License: CC-BY
Author:
* <https://twitter.com/calciumtrice>{:target=_blank}
* <https://opengameart.org/users/calciumtrice>{:target=_blank}

## Wizard Boy

![](https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/wizard_gesture.gif?raw=true)

{% include asset.html name="wizard_boy" prefix="gfx" url="https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/Calciumtrice/wizard_boy.ase" %}

## Wizard Girl

![](https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/wizard_walk.gif?raw=true)

{% include asset.html name="wizard_girl" prefix="gfx" url="https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/Calciumtrice/wizard_girl.ase" %}

## Usage code

```kotlin
val atlas = MutableAtlasUnit()
val boy = KR.gfx.wizardBoy.__file.readImageDataContainer(ASE.toProps(), atlas)
val girl = KR.gfx.wizardGirl.__file.readImageDataContainer(ASE.toProps(), atlas)

imageDataView(boy.default).scale(4, 4).also { it.smoothing = false }.also { boyView ->
    println(boyView.animationNames)
    boyView.animation = "idle"
    boyView.play()
}

imageDataView(girl.default).scale(4, 4).also { it.smoothing = false }.also { girlView ->
    println(girlView.animationNames)
    girlView.animation = "gesture"
    girlView.play()
    girlView.x = 128f
}
```