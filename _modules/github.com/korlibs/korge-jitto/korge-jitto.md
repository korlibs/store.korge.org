---
layout: module
title: Jitto
authors: [korlibs]
category: Assets
link: https://github.com/korlibs/korge-jitto/tree/main/korge-jitto
icon: https://raw.githubusercontent.com/korlibs/korge-jitto/627c837c9b27db3977226210d00e20a61706e5e6/icons/jitto-new.svg
---

Jitto

```kotlin
val jitto = JittoView().xy(256, 256).addTo(this)
while (true) {
    jitto.interpolateTo(
        Jitto(
            rightHand = 10.degrees,
            leftHand = 14.degrees,
            leftLeg = +1f,
            rightLeg = -1f,
            leftEyeDist = 0f,
            leftEyeAngle = 0.degrees,
            rightEyeDist = 0f,
            rightEyeAngle = 0.degrees,
            rotation = 45.degrees
        )
    )
    jitto.interpolateTo(
        Jitto(
            rightHand = -10.degrees,
            leftHand = -14.degrees,
            leftLeg = -1f,
            rightLeg = +1f,
        )
    )
}
```