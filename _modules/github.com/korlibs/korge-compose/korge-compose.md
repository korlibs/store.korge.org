---
layout: module
title: KorGE Compose
authors: [korlibs]
category: UI
link: https://github.com/korlibs/korge-compose/tree/main/korge-compose
icon: /i/compose-multiplatform.svg
---

Use Compose Multiplatform along KorGE.

Example. Using the Vampires asset:

```kotlin
import androidx.compose.runtime.*
import com.finalbossblues.timefantasy.*
import korlibs.event.*
import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.image.format.*
import korlibs.io.file.std.*
import korlibs.io.stream.*
import korlibs.korge.*
import korlibs.korge.compose.*
import korlibs.korge.scene.*
import korlibs.korge.view.*
import korlibs.math.geom.*
import korlibs.math.geom.Anchor
import korlibs.korge.compose.Image
import korlibs.korge.ui.*
import korlibs.korge.view.animation.*

suspend fun main() = Korge(
    title = "Korge Compose",
    windowSize = Size(512, 512),
    backgroundColor = Colors["#2b2b2b"],
    displayMode = KorgeDisplayMode(ScaleMode.SHOW_ALL, Anchor.TOP_LEFT, clipBorders = false),
    forceRenderEveryFrame = false
) {
    val sceneContainer = sceneContainer()

    sceneContainer.changeTo({ MyScene() })
}

class MyScene : Scene() {
    override suspend fun SContainer.sceneMain() {
        setComposeContent(this) {
            var width by remember { mutableStateOf(width.toInt()) }
            var height by remember { mutableStateOf(height.toInt()) }
            LaunchedEffect(true) {
                fun onResized() {
                    val w = views.actualVirtualWidth
                    val h = views.actualVirtualHeight
                    width = w
                    height = h
                    this@sceneMain.size(w.toDouble(), h.toDouble())
                }

                onEvent(ReshapeEvent) {
                    onResized()
                }
                onResized()
                //onStageResized { w, h ->
                //    //println("RESIZED: $w, $h")
                //    this@sceneMain.size(w.toDouble(), h.toDouble())
                //}
            }
            MainApp(width, height)
        }
    }
}

@Composable
fun ImageDataView(
    data: ImageData? = null,
    animation: String? = null,
) {
    ComposeKorgeView({
        UIContainer(Size(32, 32)).apply {
            imageDataView(data, animation)
                .xy(16, 16)
                .also { it.play() }
        }
    }, {
        set(data) { (this.firstChild as korlibs.korge.view.animation.ImageDataView).data = data }
        set(animation) { (this.firstChild as korlibs.korge.view.animation.ImageDataView).animation = animation }
    })
}

@Composable
private fun MainApp(width: Int, height: Int) {
    var color by remember { mutableStateOf(Colors.WHITE) }
    var count by remember { mutableStateOf(0) }
    var bitmap: BmpSlice by remember { mutableStateOf(Bitmaps.transparent) }
    var vampire: ImageData? by remember { mutableStateOf(null) }

    fun insert(digit: Int) {
        count *= 10
        count += digit
    }

    LaunchedEffect(true) {
        val vampires = EvilTransformingVampires.readImages()
        vampire = vampires?.vampire
        bitmap = vampires?.vampire?.defaultAnimation?.firstFrame?.bitmap?.slice() ?: Bitmaps.white
        println("1")
        //bitmap = resourcesVfs["korge-30.jpg"].readBitmap().slice()
    }

    VStack(width.toFloat(), adjustSize = true) {
        Image(bitmap)
        ImageDataView(vampire, "down")
        Text("$count", color)
        HStack {
            Button("-") { count-- }
            Button("+") { count++ }
        }
    }
}
```