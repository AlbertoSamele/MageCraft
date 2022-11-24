package com.magecraft.game.engine.canvas

import com.badlogic.gdx.graphics.g2d.SpriteBatch

data class SharedResources(
    val batch: SpriteBatch,
    val canvasSize: Size
)