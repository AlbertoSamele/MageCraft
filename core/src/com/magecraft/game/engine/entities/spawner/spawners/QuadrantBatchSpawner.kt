package com.magecraft.game.engine.entities.spawner.spawners

import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.magecraft.game.engine.canvas.Size
import com.magecraft.game.engine.entities.spawner.FOVSpawner
import com.magecraft.game.engine.entities.spawner.SpawnableEntity
import java.util.Random
import kotlin.math.floor

class QuadrantBatchSpawner(
    override val entity: SpawnableEntity,
    override val fov: Size
): FOVSpawner {
    // MARK: - Datasource properties

    private var spawnedQuadrants = mutableMapOf<Vector2, Set<Rectangle>>()

    // MARK: - Overridden methods

    override fun updatePosition(coordinates: Vector2) {
        val quadrant = quadrant(coordinates)
        if (!spawnedQuadrants.containsKey(quadrant)) {
            spawn(quadrant)
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

    private fun spawn(quadrant: Vector2) {
        val maxSpawnableEntities = floor(fov.area/entity.size.area)
        val entitiesToBeSpawned = floor(maxSpawnableEntities*entity.maxSpawnRate).toInt()
        val maxSpawnX = floor(fov.width - entity.size.width).toInt() - 1
        val maxSpawnY = floor(fov.height - entity.size.height).toInt() - 1

        val spawnedEntities = mutableSetOf<Rectangle>()
        var occupiedGridCoordinates = setOf<Vector2>()
        val random = Random()
        for (i in 0 until entitiesToBeSpawned) {
            val spawnX = random.nextInt(maxSpawnX)
            val spawnY = random.nextInt(maxSpawnY)

            val newEntity = Rectangle(spawnX.toFloat(), spawnY.toFloat(), entity.size.width, entity.size.height)
            val newEntityGridCoordinates = newEntity.toGridCoordinates()

            val canSpawn = !newEntityGridCoordinates.any { it in occupiedGridCoordinates }
            if (canSpawn) {
                occupiedGridCoordinates = occupiedGridCoordinates + newEntityGridCoordinates
                newEntity.x += quadrant.x*fov.width
                newEntity.y += quadrant.y*fov.height
                spawnedEntities.add(newEntity)
            }
        }

        spawnedQuadrants[quadrant] = spawnedEntities
    }

    private fun Rectangle.toGridCoordinates(): Set<Vector2> {
        val result = mutableSetOf<Vector2>()

        for (x in x.toInt() until x.toInt() + entity.size.width.toInt()) {
            for (y in y.toInt() until y.toInt() + entity.size.height.toInt()) {
                result.add(Vector2(x.toFloat(), y.toFloat()))
            }
        }

        return result
    }

    private fun adjacentQuadrants(quadrant: Vector2): List<Vector2> {
        return listOf<Vector2>(
            Vector2(quadrant.x - 1, quadrant.y),
            Vector2(quadrant.x + 1, quadrant.y),
            Vector2(quadrant.x - 1, quadrant.y - 1),
            Vector2(quadrant.x, quadrant.y - 1),
            Vector2(quadrant.x + 1, quadrant.y - 1),
            Vector2(quadrant.x - 1, quadrant.y + 1),
            Vector2(quadrant.x, quadrant.y - 1),
            Vector2(quadrant.x + 1, quadrant.y - 1)
        )
    }
}