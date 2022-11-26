package com.magecraft.game.engine.entities.spawner

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2

class SpawnerPool(var spawners: MutableList<FOVSpawner> = mutableListOf()) {
    fun entities(coordinates: Vector2): Set<Rectangle> {
        return spawners
            .map { it.entities(coordinates) }
            .reduce { res, entities ->  res + entities }
    }
}