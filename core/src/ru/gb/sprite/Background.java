package ru.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class Background extends Sprite {

    public Background (Texture texture) {
        super(new TextureRegion(texture));
    }

    @Override
    public void resize(Rect worldBounds) {
       setHeightProportion(worldBounds.getHeight());
       pos.set(worldBounds.pos);
    }
}
