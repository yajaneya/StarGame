package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;

public class GameOver extends Sprite {

    private static final float HEIGHT = 0.05f;

    public GameOver (TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"), 1, 1, 1);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        pos.set(worldBounds.pos);
    }
}
