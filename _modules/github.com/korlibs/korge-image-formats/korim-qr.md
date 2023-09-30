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

### Constructing a QR builder

First we have to construct a `QR` instance or use the companion object. This class can be constructed with a `correctionLevel` parameter, and colors for the dark and light areas: `colorDark` and `colorLight`.

```kotlin
val qr = QR // Singleton
val qr = QR()
val qr = QR(colorDark = Colors.BLACK, colorLight = Colors.WHITE)
val qr = QR(correctLevel = QRErrorCorrectLevel.H)
```

### Generating a Bitmap32 QR code

With a `QR` instance already constructed, we can generate a QR code by using the provided methods in the class. QR codes support several kind of contents, and there are methods supporting those contents.

```kotlin
qr.msg(message)
qr.vCard(name, phone, email, url, addr, org, note)
qr.meCard(name, phone, email, url, addr, org)
qr.wifi(ssid, password, WifiKind.WEP)
qr.phone(phone)
qr.email(email)
qr.sms(number, message)
qr.geo(latitude, longitude)
qr.calendarEvent(summary, startDateTime, endDateTime, location, description)
```
