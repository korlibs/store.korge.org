---
layout: gfx
title: Kenney Game Icons
authors: [www.kenney.nl]
enabled: true
screenshot: https://github.com/korlibs/korge-free-gfx/blob/5d2afe10ac98fe6f94433cf2baed23330c860fb0/kenney-game-icons/kenney_game_icons_preview.png?raw=true
show_screenshot: false
---

## Explosions

* <https://opengameart.org/content/game-icons>
* <https://opengameart.org/content/game-icons-expansion>

LICENSE CC0: Created by <https://kenney.nl/>


![](https://raw.githubusercontent.com/korlibs/korge-free-gfx/5d2afe10ac98fe6f94433cf2baed23330c860fb0/kenney-game-icons/kenney_game_icons_preview.png)

{% include asset.html name="explosions" prefix="gfx" unzip="true" url="https://raw.githubusercontent.com/korlibs/korge-free-gfx/5d2afe10ac98fe6f94433cf2baed23330c860fb0/kenney-game-icons/icons.atlas.zip" %}

## Untyped usage:

```kotlin
val icons = resourcesVfs["gfx/icons.atlas.json"].readAtlas()
image(icons["arrowDown.png"])
```

## Typed usage:

```kotlin
val icons = resourcesVfs["gfx/icons.atlas.json"].readAtlas().toIconsAtlas()
image(icons.arrowDown)

fun Atlas.toIconsAtlas() = IconsAtlas(this)
inline class IconsAtlas(val atlas: Atlas) {
    val arrowDown get() = atlas["arrowDown.png"]
    val arrowLeft get() = atlas["arrowLeft.png"]
    val arrowRight get() = atlas["arrowRight.png"]
    val arrowUp get() = atlas["arrowUp.png"]
    val audioOff get() = atlas["audioOff.png"]
    val audioOn get() = atlas["audioOn.png"]
    val barsHorizontal get() = atlas["barsHorizontal.png"]
    val barsVertical get() = atlas["barsVertical.png"]
    val button1 get() = atlas["button1.png"]
    val button2 get() = atlas["button2.png"]
    val button3 get() = atlas["button3.png"]
    val buttonA get() = atlas["buttonA.png"]
    val buttonB get() = atlas["buttonB.png"]
    val buttonL get() = atlas["buttonL.png"]
    val buttonL1 get() = atlas["buttonL1.png"]
    val buttonL2 get() = atlas["buttonL2.png"]
    val buttonR get() = atlas["buttonR.png"]
    val buttonR1 get() = atlas["buttonR1.png"]
    val buttonR2 get() = atlas["buttonR2.png"]
    val buttonSelect get() = atlas["buttonSelect.png"]
    val buttonStart get() = atlas["buttonStart.png"]
    val buttonX get() = atlas["buttonX.png"]
    val buttonY get() = atlas["buttonY.png"]
    val checkmark get() = atlas["checkmark.png"]
    val contrast get() = atlas["contrast.png"]
    val cross get() = atlas["cross.png"]
    val down get() = atlas["down.png"]
    val downLeft get() = atlas["downLeft.png"]
    val downRight get() = atlas["downRight.png"]
    val exclamation get() = atlas["exclamation.png"]
    val exit get() = atlas["exit.png"]
    val exitLeft get() = atlas["exitLeft.png"]
    val exitRight get() = atlas["exitRight.png"]
    val export get() = atlas["export.png"]
    val fastForward get() = atlas["fastForward.png"]
    val gamepad get() = atlas["gamepad.png"]
    val gamepad1 get() = atlas["gamepad1.png"]
    val gamepad2 get() = atlas["gamepad2.png"]
    val gamepad3 get() = atlas["gamepad3.png"]
    val gamepad4 get() = atlas["gamepad4.png"]
    val gear get() = atlas["gear.png"]
    val home get() = atlas["home.png"]
    val import get() = atlas["import.png"]
    val information get() = atlas["information.png"]
    val joystick get() = atlas["joystick.png"]
    val joystickLeft get() = atlas["joystickLeft.png"]
    val joystickRight get() = atlas["joystickRight.png"]
    val joystickUp get() = atlas["joystickUp.png"]
    val larger get() = atlas["larger.png"]
    val leaderboardsComplex get() = atlas["leaderboardsComplex.png"]
    val leaderboardsSimple get() = atlas["leaderboardsSimple.png"]
    val left get() = atlas["left.png"]
    val locked get() = atlas["locked.png"]
    val massiveMultiplayer get() = atlas["massiveMultiplayer.png"]
    val medal1 get() = atlas["medal1.png"]
    val medal2 get() = atlas["medal2.png"]
    val menuGrid get() = atlas["menuGrid.png"]
    val menuList get() = atlas["menuList.png"]
    val minus get() = atlas["minus.png"]
    val mouse get() = atlas["mouse.png"]
    val movie get() = atlas["movie.png"]
    val multiplayer get() = atlas["multiplayer.png"]
    val musicOff get() = atlas["musicOff.png"]
    val musicOn get() = atlas["musicOn.png"]
    val next get() = atlas["next.png"]
    val open get() = atlas["open.png"]
    val pause get() = atlas["pause.png"]
    val phone get() = atlas["phone.png"]
    val plus get() = atlas["plus.png"]
    val power get() = atlas["power.png"]
    val previous get() = atlas["previous.png"]
    val question get() = atlas["question.png"]
    val `return` get() = atlas["return.png"]
    val rewind get() = atlas["rewind.png"]
    val right get() = atlas["right.png"]
    val save get() = atlas["save.png"]
    val scrollHorizontal get() = atlas["scrollHorizontal.png"]
    val scrollVertical get() = atlas["scrollVertical.png"]
    val share1 get() = atlas["share1.png"]
    val share2 get() = atlas["share2.png"]
    val shoppingBasket get() = atlas["shoppingBasket.png"]
    val shoppingCart get() = atlas["shoppingCart.png"]
    val siganl1 get() = atlas["siganl1.png"]
    val signal2 get() = atlas["signal2.png"]
    val signal3 get() = atlas["signal3.png"]
    val singleplayer get() = atlas["singleplayer.png"]
    val smaller get() = atlas["smaller.png"]
    val star get() = atlas["star.png"]
    val stop get() = atlas["stop.png"]
    val tablet get() = atlas["tablet.png"]
    val target get() = atlas["target.png"]
    val trashcan get() = atlas["trashcan.png"]
    val trashcanOpen get() = atlas["trashcanOpen.png"]
    val trophy get() = atlas["trophy.png"]
    val unlocked get() = atlas["unlocked.png"]
    val up get() = atlas["up.png"]
    val upLeft get() = atlas["upLeft.png"]
    val upRight get() = atlas["upRight.png"]
    val video get() = atlas["video.png"]
    val warning get() = atlas["warning.png"]
    val wrench get() = atlas["wrench.png"]
    val zoom get() = atlas["zoom.png"]
    val zoomDefault get() = atlas["zoomDefault.png"]
    val zoomIn get() = atlas["zoomIn.png"]
    val zoomOut get() = atlas["zoomOut.png"]
}
```