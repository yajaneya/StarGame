package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class Spaceship extends Sprite {

    private static final float SIZE_SHIP = 0.1f;

    private static final float V_STEP = 0.003f;

    private Vector2 v;
    private Rect worldBounds;


    public Spaceship(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship").split(195, 287)[0][1]);
        v = new Vector2();
        pos.set(0, -0.45f);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        super.resize(worldBounds);
        setHeightProportion(SIZE_SHIP);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.add(v);
        checkAndHandleBounds();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (pos.x > touch.x) {
            v.set(-V_STEP, 0);
        } else {
            v.set(V_STEP, 0);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        v.set(0, 0);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (isMe(touch)) {
            pos.set(touch);
            v.set(0,0);
        }
        return false;
    }

    public boolean keyDown(int keycode) {
        System.out.println("keyDown: keycode = " + keycode);
        switch (keycode) {
            case 19:
                v.set(0,V_STEP);
                break;
            case 20:
                v.set(0, -V_STEP);
                break;
            case 21:
                v.set(-V_STEP, 0);
                break;
            case 22:
                v.set(V_STEP, 0);
                break;
         }
        return false;
    }

    public boolean keyUp(int keycode) {
        System.out.println("keyUp: keycode = " + keycode);
        v.set(0, 0);
        return false;
    }

    @Override
    protected void checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft()+0.05f);
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getRight()-0.05f);
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom()+0.05f);
        }
        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getTop()-0.05f);
        }
    }
}