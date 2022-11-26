package com.magecraft.game.sprites

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.magecraft.game.engine.canvas.Size
import com.magecraft.game.engine.entities.spawner.SpawnableEntity

data class CrystalSprite(
    override val maxSpawnRate: Float = 0.21F,
    override val size: Size = Size(100F,100F),
    override val texture: Texture = Texture(Gdx.files.internal("placeholder.png"))
): MageCraftSprite(texture)
