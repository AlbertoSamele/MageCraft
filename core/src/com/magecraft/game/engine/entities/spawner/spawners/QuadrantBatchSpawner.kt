package com.magecraft.game.engine.entities.spawner.spawners

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.magecraft.game.engine.canvas.Size
import com.magecraft.game.engine.utils.intersects
import com.magecraft.game.engine.entities.spawner.FOVSpawner
import com.magecraft.game.engine.entities.spawner.SpawnableEntity
import com.magecraft.game.engine.entities.spawner.SpawnerPool
import java.util.Random
import kotlin.math.floor

class QuadrantBatchSpawner<Entity: SpawnableEntity>(
    override val entity: Entity,
    override val fov: Size,
    override var pool: SpawnerPool? = null
): FOVSpawner {
    // MARK: - Datasource properties

    private var spawnedQuadrants = mutableMapOf<Vector2, Set<Rectangle>>()

    // MARK: - Overridden methods

    override fun spawnAtPosition(coordinates: Vector2) {
        val quadrant = quadrant(coordinates)
        if (!spawnedQuadrants.containsKey(quadrant)) {
            spawnInQuadrant(quadrant, pool?.entities(coordinates).orEmpty())
        }
    }

    override fun entities(coordinates: Vector2): Set<Rectangle> {
        val targetQuadrant = quadrant(coordinates)
        return (listOf(targetQuadrant) + adjacentQuadrants(targetQuadrant)).map {
            spawnedQuadrants[it].orEmpty()
        }.reduce { y, vars -> y + vars }
    }

    // MARK: - Private methods

    private fun quadrant(coordinates: Vector2): Vector2 {
        val x = floor(coordinates.x / fov.width)
        val y = floor(coordinates.y / fov.height)

        return Vector2(x, y)
    }

    private fun spawnInQuadrant(quadrant: Vector2, inhabitants: Set<Rectangle>) {
        val maxSpawnableEntities = floor(fov.area/entity.size.area)
        val entitiesToBeSpawned = floor(maxSpawnableEntities*entity.maxSpawnRate).toInt()
        val maxSpawnX = floor(fov.width - entity.size.width).toInt() - 1
        val maxSpawnY = floor(fov.height - entity.size.height).toInt() - 1

        val spawnedEntities = mutableSetOf<Rectangle>()
        val conflictingInhabitants = inhabitants.toMutableSet()
        val random = Random()
        for (i in 0 until entitiesToBeSpawned) {
            val spawnX = random.nextInt(maxSpawnX)
            val spawnY = random.nextInt(maxSpawnY)

            val newEntity = Rectangle(
                spawnX.toFloat() + quadrant.x*fov.width,
                spawnY.toFloat() + quadrant.y*fov.height,
                entity.size.width,
                entity.size.height
            )

            val canSpawn = !conflictingInhabitants.any { it.intersects(newEntity) }
            if (canSpawn) {
                conflictingInhabitants.add(newEntity)
                spawnedEntities.add(newEntity)
            }
        }

        spawnedQuadrants[quadrant] = spawnedEntities
    }

    private fun adjacentQuadrants(quadrant: Vector2): List<Vector2> {
        val partialResult = mutableListOf<Vector2>()

        for (xOffset in -2..2) {
            for (yOffset in -2..2) {
                partialResult.add(Vector2(quadrant.x + xOffset, quadrant.y + yOffset))
            }
        }

        return partialResult
    }
}