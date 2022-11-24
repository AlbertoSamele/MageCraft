package com.magecraft.game.engine.canvas

data class Size(
    val width: Float,
    val height: Float
) {
    val area: Float
        get() = width*height
}
