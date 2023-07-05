---
layout: module
title: FLAC
enabled: true
authors: [korlibs]
category: Audio
link: https://github.com/korlibs/korge-audio-formats/tree/main/korau-flac
icon: /i/flac.svg
icon_size: contain
icon_position: center center
---

Supports loading FLAC files that have a great compression rate.

<https://en.wikipedia.org/wiki/FLAC>

## Explicitly loading an FLAC sound or music

```kotlin
val data = resourcesVfs["sounds/8Khz-Mono.flac"].readSound(AudioDecodingProps(formats = FLAC))
```

## Getting information (length) of an FLAC file

```kotlin
println(FLAC.tryReadInfo(resourcesVfs["sounds/8Khz-Mono.flac"].open(), AudioDecodingProps(exactTimings = true)))
```

## Registering the FLAC format, so it can detect the format automatically

```kotlin
defaultAudioFormats.register(FLAC)
val data = resourcesVfs["sounds/8Khz-Mono.flac"].readSound()
```
