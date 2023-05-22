---
layout: sfx
title: 100 CC0 SFX
base: https://github.com/korlibs/korge-free-sfx/raw/main/MOD-CC0/
authors: [OwlishMedia]
enabled: true
files:
- fx-poly1.mod
---

You will have to install first [MOD/S3M/XM support](/module/korau-mod/)

```kotlin
defaultAudioFormats.register(MOD, S3M, XM)
val music = resourcesVfs["sfx/file.mod"].readMusic()
```

or

```kotlin
val music = resourcesVfs["sfx/file.mod"].readMusic(AudioDecodingProps.DEFAULT.copy(formats = MOD))
```
