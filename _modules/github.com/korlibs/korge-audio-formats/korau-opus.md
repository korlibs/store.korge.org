---
layout: module
title: OPUS
enabled: true
authors: [korlibs]
category: Audio
link: https://github.com/korlibs/korge-audio-formats/tree/main/korau-opus
icon: /i/opus.png
icon_size: contain
icon_position: center center
---

Supports loading OPUS files that have a great compression rate.

<https://en.wikipedia.org/wiki/Opus_(audio_format)>

## Explicitly loading an OPUS sound or music

```kotlin
val data = resourcesVfs["sounds/8Khz-Mono.opus"].readSound(AudioDecodingProps(formats = OPUS))
```

## Getting information (length) of an OPUS file

```kotlin
println(OPUS.tryReadInfo(resourcesVfs["sounds/8Khz-Mono.opus"].open(), AudioDecodingProps(exactTimings = true)))
```

## Registering the OPUS format, so it can detect the format automatically

```kotlin
defaultAudioFormats.register(OPUS)
val data = resourcesVfs["sounds/8Khz-Mono.opus"].readSound()
```
