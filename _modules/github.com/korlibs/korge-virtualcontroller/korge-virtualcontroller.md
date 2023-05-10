---
layout: module
title: VirtualController
category: Input
authors: [korlibs]
icon: /i/controller.png
link: https://github.com/korlibs/korge-virtualcontroller/tree/main/korge-virtualcontroller
---

Supports handling keyboard, real gamepad and virtual gamepad all in a simple interface.

```kotlin
val virtualController = virtualController(
    sticks = listOf(
        VirtualStickConfig(
            left = Key.LEFT,
            right = Key.RIGHT,
            up = Key.UP,
            down = Key.DOWN,
            lx = GameButton.LX,
            ly = GameButton.LY,
            position = Anchor.BOTTOM_LEFT,
        )
    ),
    buttons = listOf(
        VirtualButtonConfig(
            key = Key.SPACE,
            button = GameButton.BUTTON_SOUTH,
            position = Anchor.BOTTOM_RIGHT,
        )
    ),
)

virtualController.apply {
    down(GameButton.BUTTON_SOUTH) {
        val isInGround = playerSpeed.y.isAlmostZero()
        //if (isInGround) {
        if (true) {
            if (!jumping) {
                jumping = true
                updateState()
            }
            playerSpeed += Vector2(0, -5.5)
        }
    }
    changed(GameButton.LX) {
        if (it.new.absoluteValue < 0.01f) {
            updated(right = it.new > 0f, up = true, scale = 1f)
        }
    }
    addUpdater(60.hz) {
        val lx = virtualController.lx
        when {
            lx < 0f -> {
                updated(right = false, up = false, scale = lx.absoluteValue)
            }
            lx > 0f -> {
                updated(right = true, up = false, scale = lx.absoluteValue)
            }
        }
    }
}
```