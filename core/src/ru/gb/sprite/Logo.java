package ru.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class Logo extends Sprite {

    private static final float SIZE_LOGO = 0.1f;
    private static final String ICON_LOGO = "badlogic.jpg";

    public Logo () {
        super(new TextureRegion(new Texture(ICON_LOGO)));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SIZE_LOGO * worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("Logo mouse");
        pos.set(touch);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("Logo dragged mouse");
        pos.set(touch);
        return false;
    }
}