package com.magecraft.game.engine.entities.spawner

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.magecraft.game.engine.canvas.Size

interface FOVSpawner {
    val fov: Size
    val entity: SpawnableEntity

    fun spawnAtPosition(coordinates: Vector2)
    fun entities(coordinates: Vector2): Set<Rectangle>
}