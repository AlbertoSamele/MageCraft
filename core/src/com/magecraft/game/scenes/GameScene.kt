package com.magecraft.game.scenes

import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.ExtendViewport
import com.magecraft.game.engine.canvas.SharedResources

class GameScene(
    private val resources: SharedResources
): MageCraftScene {
    // MARK: - Private properties

    private lateinit var viewport: ExtendViewport

    // MARK: - MageCraftScene methods

    override fun initialize() {
        viewport = ExtendViewport(resources.canvas.width, resources.canvas.height)
    }

    override fun show() {}

    override fun render(delta: Float) {
        ScreenUtils.clear(1F, 0F, 0F, 1F)
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, false)
    }

    override fun pause() {}

    override fun resume() {}

    override fun hide() {}

    override fun dispose() {}
}