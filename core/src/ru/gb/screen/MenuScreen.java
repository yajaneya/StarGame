package ru.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 1.8f;

    private Texture fon;
    private Texture img;

    private Vector2 pos;
    private Vector2 v;
    private Vector2 touch;

    @Override
    public void show() {
        super.show();
        fon = new Texture("fon.jpg");
        img = new Texture("badlogic.jpg");

        pos = new Vector2();
        v = new Vector2(0,0);
        touch = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(fon,0,0);
        batch.draw(img, pos.x, pos.y);
        batch.end();
        if (touch.dst(pos) > V_LEN) {
            pos.add(v);
        } else {
            pos.set(touch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        fon.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
          touch.set(screenX, Gdx.graphics.getHeight() - screenY);
//          v.set(touch.cpy().sub(pos)).setLength(V_LEN);
          v.set(touch.cpy().sub(pos)).scl(V_LEN);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return false;
    }
}
