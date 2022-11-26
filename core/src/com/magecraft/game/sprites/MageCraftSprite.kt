package com.magecraft.game.sprites

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Rectangle
import com.magecraft.game.engine.entities.spawner.SpawnableEntity

abstract class MageCraftSprite(
    open val texture: Texture
): SpawnableEntity {
    fun draw(batch: SpriteBatch, rectangle: Rectangle) {
        batch.draw(
            texture,
            rectangle.x,
            rectangle.y,
            rectangle.width,
            rectangle.height
        )
    }
}