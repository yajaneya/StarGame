package ru.gb.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.gb.base.BaseScreen;
import ru.gb.base.Font;
import ru.gb.enums.EnemyShips;
import ru.gb.math.Rect;
import ru.gb.pool.BulletPool;
import ru.gb.pool.EnemyPool;
import ru.gb.pool.ExplosionPool;
import ru.gb.pool.TechAssistPool;
import ru.gb.sprite.Background;
import ru.gb.sprite.Bullet;
import ru.gb.sprite.EnemyShip;
import ru.gb.sprite.Explosion;
import ru.gb.sprite.GameOver;
import ru.gb.sprite.MainShip;
import ru.gb.sprite.NewGameButton;
import ru.gb.sprite.Star;
import ru.gb.sprite.TechAssist;
import ru.gb.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 64;
    private static final float PADDING = 0.01f;

    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private EnemyShips levelEnemyShip;

    private Background background;

    private TextureAtlas atlas;

    private Star[] stars;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private TechAssistPool techAssistPool;
    private MainShip mainShip;

    private Sound bulletSound;
    private Sound lazerSound;
    private Sound explosionSound;

    private EnemyEmitter enemyEmitter;

    private GameOver gameOver;
    private NewGameButton newGameButton;

    private Font font;
    private int frags;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;


    @Override
    public void show() {
        super.show();
        background = new Background();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/musicGame.mp3"));
        music.setLooping(true);
        music.play();

        lazerSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));

        atlas = new TextureAtlas("textures/mainAtlas.pack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i<stars.length; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(worldBounds, bulletPool, explosionPool);
        techAssistPool = new TechAssistPool(atlas);

        mainShip = new MainShip(atlas, bulletPool, explosionPool, lazerSound);
        gameOver = new GameOver(atlas);
        newGameButton = new NewGameButton(atlas, this);

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(worldBounds, bulletSound, enemyPool, atlas);

        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        frags = 0;
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
    }

    public void startNewGame() {

        bulletPool.freeAllActiveSprites();
        enemyPool.freeAllActiveSprites();
        explosionPool.freeAllActiveSprites();
        techAssistPool.freeAllActiveSprites();
        mainShip.initialize();
        mainShip.flushDestroy();
        frags = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        newGameButton.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        explosionSound.dispose();
        enemyPool.dispose();
        techAssistPool.dispose();
        music.dispose();
        lazerSound.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (!newGameButton.isDestroyed()) {
            newGameButton.touchDown(touch, pointer, button);
        } else {
            mainShip.touchDown(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (!newGameButton.isDestroyed()) {
            newGameButton.touchUp(touch, pointer, button);
        } else {
            mainShip.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        mainShip.touchDragged(touch, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    private void update (float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            techAssistPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frags);
        } else {
            gameOver.update(delta);
            newGameButton.update(delta);
        }
    }

    private void checkCollisions() {

        if (mainShip.isDestroyed()) {
            return;
        }

        List<EnemyShip> enemyShipList = enemyPool.getActiveSprites();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isDestroyed()) {
                continue;
            }
            float minDist = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemyShip.pos) <minDist) {
                mainShip.damage(enemyShip.getBulletDamage() * 2);
                enemyShip.destroy();
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveSprites();
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed()) {
                continue;
            }
            for (EnemyShip enemyShip : enemyShipList) {
                if (enemyShip.isDestroyed() || bullet.getOwner() !=mainShip) {
                    continue;
                }
                if (enemyShip.isBulletCollision(bullet)) {
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemyShip.isDestroyed()) {
                        frags++;
                        if (enemyShip.getLevelShip() == levelEnemyShip.BIG) {
                            techAssistPool.obtain().set(worldBounds, enemyShip.pos, new Vector2(0f, -0.5f), 0.03f, 5);
                        }
                    }
                }
            }
            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        List<Explosion> explosionsList = explosionPool.getActiveSprites();
        for (Explosion explosion : explosionsList) {
            if (explosion.isDestroyed()) {
                continue;
            }
            float minDist = explosion.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(explosion.pos) <minDist) {
                mainShip.damage(explosion.getDamage());
                if (mainShip.isDestroyed()) {
                    break;
                }
            }
        }

        List<TechAssist> techAssistsList = techAssistPool.getActiveSprites();
        for (TechAssist techAssist : techAssistsList) {
            if (techAssist.isDestroyed()) {
                continue;
            }
            float minDist = techAssist.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(techAssist.pos) <minDist) {
                mainShip.addHp(techAssist.getReability());
                techAssist.destroy();
            }
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
        techAssistPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        batch.begin();
        background.draw(batch);
        for (Star star : stars) {
            star.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
            techAssistPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        printInfo();
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + PADDING, worldBounds.getTop() - PADDING, Align.left);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - PADDING, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - PADDING, worldBounds.getTop() - PADDING, Align.right);
    }
}

