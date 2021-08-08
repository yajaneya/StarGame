package ru.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class Logo extends Sprite {

    private static final float SIZE_LOGO = 0.1f;
    private static final String ICON_LOGO = "badlogic.jpg";

    private static final float V_STEP = 0.03f;

    private Vector2 v;
    private Vector2 touch;

    public Logo () {
        super(new TextureRegion(new Texture(ICON_LOGO)));
        v = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(SIZE_LOGO);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (pos.dst(touch) > V_STEP) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("Logo mouse");
        this.touch.set(touch);
        v.set(touch.cpy().sub(pos).scl(V_STEP));
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("Logo dragged mouse");
        pos.set(touch);
        return false;
    }
}