package com.magecraft.game

import com.badlogic.gdx.Game
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.magecraft.game.engine.canvas.Canvas
import com.magecraft.game.engine.canvas.SharedResources

class MageCraft: Game() {
    // MARK: - Private properties

    private val canvas = Canvas(width = 1080F, height = 720F)
    private lateinit var batch: SpriteBatch

    // MARK: - Datasource properties

    private val scenesStateMachine: ScenesStateMachine by lazy {
        ScenesStateMachine(
            game = this,
            resources = SharedResources(batch, canvas)
        )
    }

    // MARK: - Overridden methods

    override fun create() {
        initializeGDXProperties()
        scenesStateMachine.start()
    }

    override fun dispose() {
        batch.dispose()
    }

    // MARK: - Private properties

    private fun initializeGDXProperties() {
        batch = SpriteBatch()
    }
}