package com.magecraft.game

import com.badlogic.gdx.Game
import com.magecraft.game.engine.canvas.SharedResources
import com.magecraft.game.scenes.GameScene
import com.magecraft.game.scenes.MageCraftScene

class ScenesStateMachine(
    private val game: Game,
    private val resources: SharedResources
) {
    // MARK: - State

    private enum class State {
        NONE, GAME
    }

    // MARK: - Datasource properties

    private var state = State.NONE

    // MARK: - Public methods

    fun start() {
        composeScene()
    }

    // MARK: - Private methods

    private fun composeScene() {
        val scene = nextScene()
        scene?.initialize()
        scene?.let {
            game.screen = it
        }
    }

    private fun nextScene(): MageCraftScene? {
        return when (state) {
            State.NONE -> GameScene(resources)
            State.GAME -> null
        }
    }
}