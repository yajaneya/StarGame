package ru.gb.pool;

import ru.gb.base.SpritesPool;
import ru.gb.math.Rect;
import ru.gb.sprite.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private final Rect worldbound;
    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;

    public EnemyPool(Rect worldbound, BulletPool bulletPool, ExplosionPool explosionPool) {
        this.worldbound = worldbound;
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
    }

    @Override
    protected EnemyShip newSprite() {
        return new EnemyShip(worldbound, bulletPool, explosionPool);
    }
}
