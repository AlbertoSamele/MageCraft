package com.magecraft.game.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.magecraft.game.engine.canvas.SharedResources
import com.magecraft.game.engine.canvas.Size
import com.magecraft.game.engine.entities.spawner.SpawnerPool
import com.magecraft.game.engine.entities.spawner.spawners.QuadrantBatchSpawner
import com.magecraft.game.engine.utils.*
import com.magecraft.game.sprites.CrystalSprite
import com.magecraft.game.sprites.MageCraftSprite

class GameScene(
    private val resources: SharedResources,
    private val spawnersPool: SpawnerPool = SpawnerPool()
): MageCraftScene {
    // MARK: - Private properties

    private lateinit var camera: OrthographicCamera
    private lateinit var viewport: ExtendViewport

    // MARK: - Computed properties

    private val spawners: List<QuadrantBatchSpawner<MageCraftSprite>>
        get() = listOf(spawner, spawner2)

    // MARK: - Datasource properties

    private val spawner = QuadrantBatchSpawner<MageCraftSprite>(
        entity=CrystalSprite(),
        fov=resources.canvasSize
    )
    private val spawner2 = QuadrantBatchSpawner<MageCraftSprite>(
        entity=CrystalSprite(size = Size(40F, 40F)),
        fov=resources.canvasSize
    )

    // MARK: - Overridden methods

    override fun initialize() {
        initializeSpawners()
        initializeCamera()
    }

    override fun render(delta: Float) {
        ScreenUtils.clear(1F, 0F, 0F, 1F)
        updateCamera()

        resources.batch.projectionMatrix = camera.combined
        resources.batch.begin()
        renderAmbientElements()
        resources.batch.end()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun show() {}

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {}

    // MARK: - Private methods

    private fun initializeCamera() {
        camera = OrthographicCamera(resources.canvasSize.width, resources.canvasSize.height)
        viewport = ExtendViewport(resources.canvasSize.width, resources.canvasSize.height, camera)
        spawner.spawnAtPosition(camera.position.toVector2())
        spawner2.spawnAtPosition(camera.position.toVector2())
    }

    private fun initializeSpawners() {
        listOf(spawner, spawner2).forEach {
            spawnersPool.spawners.add(it)
            it.pool = spawnersPool
        }
    }

    private fun updateCamera() {
        camera.update()

        listOf(camera.minXminY, camera.maxXminY, camera.minXMaxY, camera.maxXMaxY).forEach { position ->
            spawners.forEach {
                it.spawnAtPosition(position)
            }
        }
    }

    private fun renderAmbientElements() {
        spawners.forEach { spawner ->
            spawner.entities(camera.position.toVector2()).forEach {
                spawner.entity.draw(resources.batch, it)
            }
        }
    }
}