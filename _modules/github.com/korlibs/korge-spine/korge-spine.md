---
layout: module
title: Spine
authors: [korlibs]
category: Skeletal
link: https://github.com/korlibs/korge-spine/tree/main/korge-spine
icon: /i/spine.png
---

Adds support for [Spine](https://esotericsoftware.com/).

```kotlin
val atlas = resourcesVfs["spineboy/spineboy-pma.atlas"].readAtlas(ImageDecodingProps(asumePremultiplied = true))
val skeletonData = resourcesVfs["spineboy/spineboy-pro.skel"].readSkeletonBinary(atlas, 0.6f)

fun createSkel(): Pair<Skeleton, AnimationState> {
    val skeleton = Skeleton(skeletonData) // Skeleton holds skeleton state (bone positions, slot attachments, etc).
    val stateData = AnimationStateData(skeletonData) // Defines mixing (crossfading) between animations.
    stateData.setMix("run", "jump", 0.2f)
    stateData.setMix("jump", "run", 0.2f)

    val state = AnimationState(stateData) // Holds the animation state for a skeleton (current animation, time, etc).
    state.timeScale = 0.5f // Slow all animations down to 50% speed.

    // Queue animations on track 0.
    state.setAnimation(0, "run", true)
    state.addAnimation(0, "jump", false, 2f) // Jump after 2 seconds.
    state.addAnimation(0, "run", true, 0f) // Run after the jump.
    state.update(1f / 60f) // Update the animation time.
    state.apply(skeleton) // Poses skeleton using current animations. This sets the bones' local SRT.

    skeleton.updateWorldTransform() // Uses the bones' local SRT to compute their world SRT.
    return skeleton to state
}

// Add view
container {
    val (skeleton, state) = createSkel()
    //speed = 2.0f
    speed = 0.5f
    scale(2.0)
    position(200, 700)
    skeletonView(skeleton, state).also { it.debugAnnotate = true }
    solidRect(10.0, 10.0, Colors.RED).centered
    filters(DropshadowFilter(shadowColor = Colors.RED))
}

container {
    val (skeleton, state) = createSkel()
    //speed = 2.0f
    speed = 1.0f
    scale(2.0)
    position(900, 700)
    skeletonView(skeleton, state).also { it.debugAnnotate = true }
    solidRect(10.0, 10.0, Colors.RED).centered
    //filters(DropshadowFilter(shadowColor = Colors.RED))
}
```