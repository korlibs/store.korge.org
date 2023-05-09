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
val map = AStar(BooleanArray2.fromString(mapOf('X' to true, '.' to false), false, code = """
    .X...X....
    .X...X....
    .X.X.X....
    ...X.X....
    ...X......
    ...X......
""".trimIndent()))
val points = map.find(0, 1, 6, 2, findClosest = true, diagonals = true)
println(points)
// [(0, 1), (0, 2), (1, 3), (2, 2), (3, 1), (4, 2), (4, 3), (5, 4), (6, 3), (6, 2)]
```
