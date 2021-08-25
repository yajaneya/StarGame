package ru.gb.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gb.base.SpritesPool;
import ru.gb.sprite.TechAssist;

public class TechAssistPool extends SpritesPool <TechAssist> {

    private final TextureAtlas atlas;

    public TechAssistPool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected TechAssist newSprite() {
        return new TechAssist(atlas);
    }
}
