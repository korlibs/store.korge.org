---
layout: module
title: Box2D
authors: [korlibs]
category: Physics
link: https://github.com/korlibs/korge-box2d/tree/main/korge-box2d
icon: /i/box2d.png
---

Adds Box2d support to KorGE:

```kotlin
solidRect(920, 100).xy(0, 620).registerBodyWithFixture(type = BodyType.STATIC, friction = 0.2, restitution = 0.2)
for (n in 0 until 5) {
    //fastEllipse(Size(100, 100))
    circle(50f)
    //ellipse(Size(50, 50))
        .xy(120 + 140 * n, 246)
        .anchor(Anchor.CENTER)
        .registerBodyWithFixture(
            type = BodyType.DYNAMIC,
            linearVelocityY = 6.0,
            friction = 0.2,
            restitution = 0.3 + (n * 0.1)
        )
}
```