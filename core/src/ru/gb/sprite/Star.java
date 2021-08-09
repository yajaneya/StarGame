package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;
import ru.gb.math.Rnd;

public class Star extends Sprite {

    private Vector2 v;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        v = new Vector2();
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        float height = Rnd.nextFloat(0.005f, 0.013f);
        setHeightProportion(height);
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x,y);
        v.set(Rnd.nextFloat(-0.005f, 0.005f), getHalfHeight()*-7);
    }
}
