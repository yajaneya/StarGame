package ru.gb.screen;

import com.badlogic.gdx.math.Vector2;

import ru.gb.base.BaseScreen;
import ru.gb.math.Rect;
import ru.gb.sprite.Background;
import ru.gb.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Background background;
    private Logo logo;

    @Override
    public void show() {
        super.show();
        background = new Background();
        logo = new Logo();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        logo.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        logo.touchDown(touch, pointer, button);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        logo.touchDragged(touch, pointer);
        return false;
    }

    private void update (float delta) {
        logo.update(delta);
    }

    private void draw () {
        batch.begin();
        background.draw(batch);
        logo.draw(batch);
        batch.end();
    }
}
