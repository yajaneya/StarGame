package ru.gb.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.gb.base.BaseButton;
import ru.gb.math.Rect;
import ru.gb.screen.GameScreen;

public class NewGameButton extends BaseButton {

    private static final float HEIGHT = 0.03f;

    private GameScreen gameScreen;

    public NewGameButton (TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(HEIGHT);
        pos.set(worldBounds.getLeft() + getHalfWidth() + HEIGHT, worldBounds.getBottom() + getHalfHeight() + HEIGHT);
    }

    @Override
    public void action() {
        gameScreen.initialize();
    }

}
