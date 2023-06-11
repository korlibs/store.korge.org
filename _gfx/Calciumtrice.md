---
layout: gfx
title: Calciumtrice Dungeon assets
authors: 
- { title: "Calciumtrice", url: "https://twitter.com/calciumtrice", image: "https://pbs.twimg.com/profile_images/1282712290593312769/YPkbqLUC_400x400.jpg" }
enabled: true
screenshot: https://raw.githubusercontent.com/korlibs/korge-free-gfx/ca040dd1b403c8d95eac27a28a29f4c81a1247c0/Calciumtrice/humans/wizard/wizard_f.gif
show_screenshot: false
license: CC-BY-4.0
credit: By twitter.com/calciumtrice
sources:
- https://twitter.com/calciumtrice
- https://opengameart.org/users/calciumtrice
assets_base: https://raw.githubusercontent.com/korlibs/korge-free-gfx/549debc5e6a13e0bbaf78e2a33e8fb0425f53483/Calciumtrice/
assets:
- { title: Wizard Male, name: wizard_m, preview: humans/wizard/wizard_m.gif, download: humans/wizard/wizard_m.ase }
- { title: Wizard Female, name: wizard_f, preview: humans/wizard/wizard_f.gif, download: humans/wizard/wizard_f.ase }
- { title: Cleric Male, name: cleric_m, preview: humans/cleric/cleric_m.gif, download: humans/cleric/cleric_m.ase }
- { title: Cleric Female, name: cleric_f, preview: humans/cleric/cleric_f.gif, download: humans/cleric/cleric_f.ase }
- { title: Ranger Male, name: ranger_m, preview: humans/ranger/ranger_m.gif, download: humans/ranger/ranger_m.ase }
- { title: Ranger Female, name: ranger_f, preview: humans/ranger/ranger_f.gif, download: humans/ranger/ranger_f.ase }
- { title: Rogue Male, name: rogue_m, preview: humans/rogue/rogue_m.gif, download: humans/rogue/rogue_m.ase }
- { title: Rogue Female, name: rogue_f, preview: humans/rogue/rogue_f.gif, download: humans/rogue/rogue_f.ase }
- { title: Warrior Male, name: warrior_m, preview: humans/warrior/warrior_m.gif, download: humans/warrior/warrior_m.ase }
- { title: Warrior Female, name: warrior_f, preview: humans/warrior/warrior_f.gif, download: humans/warrior/warrior_f.ase }
- { title: Bat, name: bat, preview: enemies/bat/bat.gif, download: enemies/bat/bat.ase }
- { title: Rat, name: rat, preview: enemies/rat/rat.gif, download: enemies/rat/rat.ase }
- { title: Skeleton, name: skeleton, preview: enemies/skeleton/skeleton.gif, download: enemies/skeleton/skeleton.ase }
- { title: Snake, name: snake, preview: enemies/snake/snake.gif, download: enemies/snake/snake.ase }
- { title: Minotaur, name: minotaur, preview: enemies/minotaur/minotaur.gif, download: enemies/minotaur/minotaur.ase }
- { title: Orc1, name: orc1, preview: enemies/orc/orc1.gif, download: enemies/orc/orc1.ase }
- { title: Orc2, name: orc2, preview: enemies/orc/orc2.gif, download: enemies/orc/orc2.ase }
- { title: Goblin1, name: goblin1, preview: enemies/goblin/goblin1.gif, download: enemies/goblin/goblin1.ase }
- { title: Goblin2, name: goblin2, preview: enemies/goblin/goblin2.gif, download: enemies/goblin/goblin2.ase }
- { title: Slime Blue, name: slime_blue, preview: enemies/slime/slime_blue.gif, download: enemies/slime/slime_blue.ase }
- { title: Slime Green, name: slime_green, preview: enemies/slime/slime_green.gif, download: enemies/slime/slime_green.ase }
- { title: Slime Red, name: slime_red, preview: enemies/slime/slime_red.gif, download: enemies/slime/slime_red.ase }
- { title: Slime Yellow, name: slime_yellow, preview: enemies/slime/slime_yellow.gif, download: enemies/slime/slime_yellow.ase }
tile_assets:
- { title: Dungeon Tileset, name: dungeon_tileset_calciumtrice, preview: tiles/dungeon_tileset_calciumtrice.png, download: tiles/dungeon_tileset_calciumtrice.png }
- { title: LDtk Base Map, name: dungeon_tilesmap_calciumtrice, preview: tiles/dungeon_tilesmap_calciumtrice.ldtk, download: tiles/dungeon_tilesmap_calciumtrice.ldtk }
---

Wizard Boy & Girl + Orcs.

Animations: `idle`, `gesture`, `walk`, `attack`, `death`

{% include asset_grid.html assets_base=page.assets_base assets=page.assets %}

## Usage code

```kotlin
val atlas = MutableAtlasUnit()
val wizardMale = KR.gfx.wizardM.__file.readImageDataContainer(ASE.toProps(), atlas)
val wizardFemale = KR.gfx.wizardF.__file.readImageDataContainer(ASE.toProps(), atlas)

imageDataView(wizardMale.default).scale(4, 4).also { it.smoothing = false }.also { view ->
    println(view.animationNames)
    view.animation = "idle"
    view.play()
}

imageDataView(wizardFemale.default).scale(4, 4).also { it.smoothing = false }.also { view ->
    println(view.animationNames)
    view.animation = "gesture"
    view.play()
    view.x = 128f
}
```

{% include asset_grid.html assets_base=page.assets_base assets=page.tile_assets %}

Required the [LDtk extension](/module/korge-ldtk/).
