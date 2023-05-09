---
layout: module
title: A* Pathfinding
authors: [korlibs]
category: Geometry
link: https://github.com/korlibs/korge-ext/tree/main/korma-astar
icon: /i/astar.png
---

Adds support for `A*` pathfinding for bidimensional boolean arrays: true for blocking cells, false for available cells. 

```kotlin
val map = AStar(BooleanArray2(32, 32))
val points = map.find(5, 5, 10, 10, findClosest = true, diagonals = true)
println(points)
```
