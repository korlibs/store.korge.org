---
layout: module
title: QR Generation
authors: [korlibs]
category: Image
link: https://github.com/korlibs/korge-image-formats/tree/main/korim-qr
icon: /i/qr.png
---

Supports generating QR codes:

```kotlin
image(QR.msg("Hello from KorIM-QR!")).xy(128, 128).scale(6.0).also { it.smoothing = false }//.filters(DropshadowFilter(0.0, 0.0, blurRadius = 12.0, shadowColor = Colors.BLACK))
```
