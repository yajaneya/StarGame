package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class TechAssist extends Sprite {

    private Rect worldBounds;

    private Vector2 v;
    private int reability;

    public TechAssist(TextureAtlas atlas) {
        super(atlas.findRegion("to"), 1, 1, 1);
        v = new Vector2();
    }

    public void set(
            Rect worldBounds,
            Vector2 pos0,
            Vector2 v0,
            float height,
            int reability
    ) {
        this.worldBounds = worldBounds;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.reability = reability;
    }

    public void update(float delta) {
        this.pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public int getReability() {
        return reability;
    }
}
