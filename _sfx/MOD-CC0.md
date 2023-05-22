---
layout: sfx
title: MOD CC0
base: https://korlibs.github.io/korge-free-sfx/MOD-CC0/
#base: https://github.com/korlibs/korge-free-sfx/raw/main/MOD-CC0/
authors: [OwlishMedia]
enabled: true
files:
- fx-poly1.mod
---

You will have to install first [MOD/S3M/XM support](/module/korau-mod/)

Files from <https://modarchive.org/index.php?request=view_by_license&query=cc0>

```kotlin
defaultAudioFormats.register(MOD, S3M, XM)
val music = resourcesVfs["sfx/file.mod"].readMusic()
```

or

```kotlin
val music = resourcesVfs["sfx/file.mod"].readMusic(AudioDecodingProps.DEFAULT.copy(formats = MOD))
```
