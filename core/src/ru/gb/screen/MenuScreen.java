package ru.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture fon;
    private Texture img;

    private Vector2 pos;
    private Vector2 v;
    private Vector2 purpose;

    @Override
    public void show() {
        super.show();
        fon = new Texture("fon.jpg");
        img = new Texture("badlogic.jpg");

        pos = new Vector2();
        v = new Vector2(0,0);
        purpose = new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        batch.begin();
        batch.draw(fon,0,0);
        batch.draw(img, pos.x, pos.y);
        batch.end();

        if (pos.equals(purpose))
//        if ((pos.x == purpose.x) & (pos.y == purpose.y))
            v.set(0, 0);
        else
            v = setSpeed(pos, purpose, 8);

        pos.add(v);
    }

    private Vector2 setSpeed(Vector2 start, Vector2 finish, int step) {

        int deltaX = (int) Math.abs(finish.x - start.x);
        int deltaY = (int) Math.abs(finish.y - start.y);

        int stepX = Math.min(deltaX, step);
        int stepY = Math.min(deltaY, step);

        int x = (finish.x - start.x > 0) ? stepX : -stepX;
        int y = (finish.y - start.y > 0) ? stepY : -stepY;

        return new Vector2(x,y);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        fon.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        pos.set(screenX, Gdx.graphics.getHeight() - screenY);
          purpose.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        pos.set(screenX, Gdx.graphics.getHeight() - screenY);
        return super.touchDragged(screenX, screenY, pointer);
    }
}
