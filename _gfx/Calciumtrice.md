---
layout: gfx
title: Calciumtrice Dungeon assets
authors: 
- { title: "Calciumtrice", url: "https://twitter.com/calciumtrice", image: "https://pbs.twimg.com/profile_images/1282712290593312769/YPkbqLUC_400x400.jpg" }
enabled: true
screenshot: https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/wizard_gesture.gif?raw=true
show_screenshot: false
license: CC-BY-4.0
credit: By twitter.com/calciumtrice
sources:
- https://twitter.com/calciumtrice
- https://opengameart.org/users/calciumtrice
assets:
- title: Wizard Boy
  name: wizard_boy
  preview: https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/wizard_gesture.gif?raw=true
  download: https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/Calciumtrice/wizard_boy.ase
- title: Wizard Girl
  name: wizard_girl
  preview: https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/wizard_walk.gif?raw=true
  download: https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/Calciumtrice/wizard_girl.ase
- title: Orc 1
  name: orc1
  preview: https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/orc1.gif?raw=true
  download: https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/Calciumtrice/orc1.ase
- title: Orc 2
  name: orc2
  preview: https://github.com/korlibs/korge-free-gfx/blob/main/Calciumtrice/orc2.gif?raw=true
  download: https://raw.githubusercontent.com/korlibs/korge-free-gfx/main/Calciumtrice/orc2.ase
---

Wizard Boy & Girl + Orcs.

Animations: `idle`, `gesture`, `walk`, `attack`, `death`

<div class="container-fluid" markdown="1"><div class="row">

{% for asset in page.assets %}

<div class="col-md-6" markdown="1">
## {{ asset.title }}
![]({{ asset.preview }})
{% include asset.html name=asset.name prefix="gfx" url=asset.download %}
</div>

{% endfor %}

</div>
</div>

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
