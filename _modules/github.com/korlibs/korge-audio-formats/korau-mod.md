---
layout: module
title: MOD, XM & S3M
authors: [korlibs]
category: Audio
link: https://github.com/korlibs/korge-audio-formats/tree/main/korau-mod
icon: /i/fasttracker.png
---

Support for MOD, XM & S3M music modules.

```kotlin
defaultAudioFormats.register(MOD, S3M, XM)
val music = resourcesVfs["GUITAROU.MOD"].readMusic()
var channel = music.play(times = infinitePlaybackTimes)
```
