---
layout: module
title: KorGE 3D
authors: [korlibs]
category: 3D
link: https://github.com/korlibs/korge-k3d/tree/main/korge-k3d
icon: /i/modules/k3d.jpg
---

Adds initial experimental 3D.

{% include asset.html name="crate" prefix="gfx" url="https://raw.githubusercontent.com/korlibs/korge-k3d/7aac2d063164571032798af9d54e8673af61d784/src/commonMain/resources/crate.jpg" %}
{% include asset.html name="korge" prefix="gfx" url="https://raw.githubusercontent.com/korlibs/korge-k3d/7aac2d063164571032798af9d54e8673af61d784/src/commonMain/resources/korge.png" %}

```kotlin
import korlibs.image.bitmap.*
import korlibs.image.color.*
import korlibs.image.format.readNativeImage
import korlibs.io.async.launchImmediately
import korlibs.io.file.std.resourcesVfs
import korlibs.korge.KeepOnReload
import korlibs.korge.scene.Scene
import korlibs.korge.tween.tween
import korlibs.korge.view.*
import korlibs.korge3d.*
import korlibs.korge3d.shape.*
import korlibs.math.geom.*
import korlibs.time.seconds

class CratesScene : Scene() {
    @KeepOnReload
    var trans = Transform3D()
    @KeepOnReload
    var tick = 0.0

    override suspend fun SContainer.sceneInit() {

        //val korgeTex = KR.korge.read()

        val crateTex = NativeImage(64, 64).context2d {
            fill(Colors.ROSYBROWN) {
                rect(0, 0, 64, 64)
            }
            stroke(Colors.SADDLEBROWN, 8f) {
                rect(0, 0, 63, 63)
                line(Point(0, 0), Point(63, 63))
            }
        }

        //val crateTex = KR.crate.read().mipmaps(true)
        //val crateTex = KR.dice.__file.readBitmap(QOI).mipmaps(true)
        val crateMaterial = Material3D(diffuse = Material3D.LightTexture(crateTex))

        solidRect(512, 512, MaterialColors.AMBER_200).alpha(0.5)
        //image(korgeTex).alpha(0.5)

        scene3D {
            //camera.set(fov = 60.degrees, near = 0.3, far = 1000.0)

            //light().position(0, 0, -3)

            //polyline3d { }
            polyline3D(Colors.BLUEVIOLET) {
                moveTo(Vector3(-10f, 0f, 0f))
                lineTo(Vector3(10f, 0f, 0f))
            }
            polyline3D(Colors.MEDIUMVIOLETRED) {
                moveTo(Vector3(0f, -10f, 0f))
                lineTo(Vector3(0f, 10f, 0f))
            }
            polyline3D(Colors["#8cb04d"]) {
                moveTo(Vector3(0f, 0f, -10f))
                lineTo(Vector3(0f, 0f, 10f))
            }
            polyline3D(Colors.WHITE) {
                moveTo(Vector3(0f, 0f, 0f))
                lineTo(Vector3(2f, 0f, 0f))
                moveTo(Vector3(0f, 0f, 0f))
                lineTo(Vector3(0f, 2f, 0f))
                moveTo(Vector3(0f, 0f, 0f))
                lineTo(Vector3(0f, 0f, 2f))
            }
            val cube1 = cube().material(crateMaterial)
            sphere(1f).position(1, 0, 0).material(crateMaterial)
            torus(1f).position(-1, 0, 0).material(crateMaterial)
            cone(1f).position(0, -1, 0).material(crateMaterial)
            cylinder(1f).position(0, -2, 0).material(crateMaterial)
            //cube(2.0, 2.0)
            val cube2 = cube().position(0, 2, 0).scale(1, 2, 1).rotation(0.degrees, 0.degrees, 45.degrees).material(crateMaterial)
            val cube3 = cube().position(-5, 0, 0).material(crateMaterial)
            val cube4 = cube().position(+5, 0, 0).material(crateMaterial)
            val cube5 = cube().position(0, -5, 0).material(crateMaterial)
            val cube6 = cube().position(0, +5, 0).material(crateMaterial)
            val cube7 = cube().position(0, 0, -5).material(crateMaterial)
            val cube8 = cube().position(0, 0, +5).material(crateMaterial)

            addUpdater {
                val angle = (tick / 4.0).degrees
                trans.setTranslationAndLookAt(
                    cos(angle * 2) * 4, cos(angle * 3) * 4, -sin(angle) * 4, // Orbiting camera
                    0f, 1f, 0f
                )
                camera.transform.copyFrom(trans)
                camera.invalidateRender()
                tick += it.milliseconds / 16.0
            }

            launchImmediately {
                while (true) {
                    tween(time = 16.seconds) {
                        cube1.modelMat.identity().rotate((it * 360).degrees, 0.degrees, 0.degrees)
                        cube2.modelMat.identity().rotate(0.degrees, (it * 360).degrees, 0.degrees)
                    }
                }
            }
        }

        solidRect(512, 512, Colors.BLUEVIOLET).position(views.virtualWidth, 0).anchor(1, 0).alpha(0.5)
        //image(korgeTex).position(views.virtualWidth, 0).anchor(1, 0).alpha(0.5)
    }
}
```