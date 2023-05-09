---
layout: module
title: SWF / Adobe Animate
authors: [korlibs]
category: Animations
link: https://github.com/korlibs/korge-swf/tree/main/korge-swf
icon: /i/adobe_animate.png
---

```kotlin
val config = SWFExportConfig(
    rasterizerMethod = ShapeRasterizerMethod.NONE,
    generateTextures = false,
    graphicsRenderer = GraphicsRenderer.SYSTEM,
)

container {
    this += resourcesVfs["morph.swf"].readSWF(views, config, false).createMainTimeLine()
    this += resourcesVfs["dog.swf"].readSWF(views, config, false).createMainTimeLine()
    this += resourcesVfs["test1.swf"].readSWF(views, config, false).createMainTimeLine().position(400, 0)
    this += resourcesVfs["demo3.swf"].readSWF(views, config, false).createMainTimeLine()
}
```
