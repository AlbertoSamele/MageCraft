package com.magecraft.game.engine.entities.spawner

import com.magecraft.game.engine.canvas.Size

interface SpawnableEntity {
    val maxSpawnRate: Float
    val size: Size
}