---
layout: module
title: JPEG Encoding
authors: [korlibs]
category: Image
link: https://github.com/korlibs/korge-image-formats/tree/main/korim-jpeg
icon: /i/jpeg.jpg
---

Supports encoding and decoding JPG/JPEGs in pure Kotlin.
Typically not necessary, since KorIM will use 
native decoders for JPEG and PNG. But useful to run on Node,
or to be able to encode images.

```kotlin
val pngBytes = resourcesVfs["korge.png"].readBytes()
val bitmap = resourcesVfs["korge.png"].readBitmap()
val jpegBytes = measureTime({ JPEG.encode(bitmap, ImageEncodingProps(quality = 0.1)) }) { println("ENCODED in $it") }
val image = measureTime({ JPEG.decode(jpegBytes) }) { println("DECODED in $it") }
image(image).alpha(0.25)
```
