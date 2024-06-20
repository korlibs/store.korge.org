---
layout: module
title: Tile Pattern Matching
authors: [korlibs]
category: Tilemaps
link: https://github.com/korlibs/korge-ext/tree/main/korma-tile-matching/korma-tile-matching
base: https://github.com/korlibs/korge-ext/raw/2a331af4bab999c105c5f945b152e5f95dbf49f1/korma-tile-matching/resources/
files:
- tiles.ase
assets_base: https://raw.githubusercontent.com/korlibs/korge-ext/f5859c899f371406d096485155a3abb3f5b4d518/korma-tile-matching/resources/
assets:
- { title: Tiles, name: tiles, preview: screenshot.png, download: tiles.ase }
icon: /i/modules/korma-tile-matching.png

---

This module allows to automatically find IntArray2 patterns and update a StackedTileMap tile information from it. Similar to what LDtk does.

## Some code to get started

```kotlin
suspend fun main() = Korge(windowSize = Size(256 * 2, 196 * 2)) {
    val tilesIDC = resourcesVfs["gfx/tiles.ase"].readImageDataContainer(ASE)
    val tiles = tilesIDC.mainBitmap.slice()
    val tileSet = TileSet(tiles.splitInRows(16, 16).mapIndexed { index, slice -> TileSetTileInfo(index, slice) })
    val tileMap = tileMap(TileMapData(32, 24, tileSet = tileSet))
    val snakeMap = tileMap(TileMapData(32, 24, tileSet = tileSet))
    val rules = CombinedRuleMatcher(WallsProvider, AppleProvider)
    val ints = IntArray2(tileMap.map.width, tileMap.map.height, GROUND).observe { rect ->
        IntGridToTileGrid(this.base as IntArray2, rules, tileMap.map, rect)
    }
    ints.lock {
        ints[RectangleInt(0, 0, ints.width, 1)] = WALL
        ints[RectangleInt(0, 0, 1, ints.height)] = WALL
        ints[RectangleInt(0, ints.height - 1, ints.width, 1)] = WALL
        ints[RectangleInt(ints.width - 1, 0, 1, ints.height)] = WALL
        ints[RectangleInt(4, 4, ints.width / 2, 1)] = WALL
    }
}

val GROUND = 0
val WALL = 1
val APPLE = 2

object AppleProvider : ISimpleTileProvider by (SimpleTileProvider(value = APPLE).also {
    it.rule(SimpleRule(Tile(12)))
})

object WallsProvider : ISimpleTileProvider by (SimpleTileProvider(value = WALL).also {
    it.rule(SimpleRule(Tile(16)))
    it.rule(SimpleRule(Tile(17), right = true))
    it.rule(SimpleRule(Tile(18), left = true, right = true))
    it.rule(SimpleRule(Tile(19), left = true, down = true))
    it.rule(SimpleRule(Tile(20), up = true, left = true, down = true))
    it.rule(SimpleRule(Tile(21), up = true, left = true, right = true, down = true))
})
```

{% include asset_grid.html assets_base=page.assets_base assets=page.assets %}
