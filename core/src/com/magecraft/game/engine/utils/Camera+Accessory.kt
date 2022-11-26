package com.magecraft.game.engine.utils

import com.badlogic.gdx.graphics.Camera
import com.badlogic.gdx.math.Vector2

val Camera.minXminY: Vector2
    get() = Vector2(
        position.x - viewportWidth / 2,
        position.y - viewportHeight / 2
    )

val Camera.maxXminY: Vector2
    get() = Vector2(
        position.x + viewportWidth / 2,
        position.y - viewportHeight / 2
    )

val Camera.minXMaxY: Vector2
    get() = Vector2(
        position.x - viewportWidth / 2,
        position.y + viewportHeight / 2
    )

val Camera.maxXMaxY: Vector2
    get() = Vector2(
        position.x + viewportWidth / 2,
        position.y + viewportHeight / 2
    )