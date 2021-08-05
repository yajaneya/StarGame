package ru.gb.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
        setHeightProportion(SIZE_LOGO * worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }

    @Override
    public void draw(SpriteBatch batch) {
        System.out.println("Speed: " + v.x + ", " + v.y);
        if (pos.dst(touch) > V_STEP) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
        super.draw(batch);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("Logo mouse");
        this.touch = touch;
        v = touch.cpy().sub(pos).scl(V_STEP);


        System.out.println("Position: " + pos.x + ", " + pos.y);
        System.out.println("Purpose: " + touch.x + ", " + touch.y);
        System.out.println("Speed: " + v.x + ", " + v.y);

//        pos.set(touch);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("Logo dragged mouse");
        pos.set(touch);
        return false;
    }
}