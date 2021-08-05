package ru.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class Background extends Sprite {

    private static final String ICON_BG = "textures/bg.png";

    public Background () {
        super(new TextureRegion(new Texture(ICON_BG)));
    }

    @Override
    public void resize(Rect worldBounds) {
       setHeightProportion(worldBounds.getHeight());
       pos.set(worldBounds.pos);
    }
}
