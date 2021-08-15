package ru.gb.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.gb.base.Sprite;
import ru.gb.math.Rect;
import ru.gb.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;
    private static final int TIME_OF_BULLET_MIN = 3;
    private static final int TIME_OF_BULLET_MAX = 60;


    private final Vector2 v0 = new Vector2(0.5f, 0);
    private final Vector2 v = new Vector2();

    private Sound bulletSound;

    private boolean pressedLeft;
    private boolean pressedRight;
    
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletPos;
    private Vector2 bulletV;
    private float bulletHeight;
    private int bulletDamage;
    private int bulletTime;
    private int timeOfShoot;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletPos = new Vector2();
        bulletV = new Vector2(0, 0.5f);
        bulletHeight = 0.01f;
        bulletDamage = 1;
        bulletTime = 0;
        timeOfShoot = 30;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v,delta);
        checkAndHandleBounds();
        if (bulletTime < timeOfShoot) {
            bulletTime++;
        } else {
            shoot();
            bulletTime = 0;
        }
//        checkAndHandleBounds1();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return  false;
            }
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
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
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
                shoot();
                break;
            case Input.Keys.PAGE_UP:
                if (timeOfShoot > TIME_OF_BULLET_MIN)
                    timeOfShoot--;
                System.out.println("timeOfShoot: " + timeOfShoot);
                break;
            case Input.Keys.PAGE_DOWN:
                if (timeOfShoot < TIME_OF_BULLET_MAX)
                    timeOfShoot++;
                System.out.println("timeOfShoot: " + timeOfShoot);
               break;
         }
        return false;
    }

    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
        }
        return false;
    }

    @Override
    protected void checkAndHandleBounds() {
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
    }
    protected void checkAndHandleBounds1() {
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotateDeg(180);
    }

    private void stop() {
        v.setZero();
    }

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletPos.set(pos.x, pos.y + getHalfHeight());
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bulletSound.play(0.05f);
    }
}