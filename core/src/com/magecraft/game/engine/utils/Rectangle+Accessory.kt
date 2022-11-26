package com.magecraft.game.engine.utils

import com.badlogic.gdx.math.Rectangle

val Rectangle.maxX: Float
    get() = x + width

val Rectangle.maxY: Float
    get() = y + height

fun Rectangle.intersects(rectangle: Rectangle): Boolean {
    return x < rectangle.maxX && maxX > rectangle.x &&
            maxY > rectangle.y && y < rectangle.maxY
}