package core.objects.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class EnemyEntity {

    protected float x, y, velX, velY, speed;
    protected float width, height;
    protected Long startTime;
    protected Body body;
    protected Weapon weapon;

    public EnemyEntity(float width, float height, Body body, Weapon weapon) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.startTime = TimeUtils.nanoTime();
        this.body = body;
        this.weapon = weapon;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
    }

    public abstract void render(SpriteBatch batch);
    public abstract void update();
    public Body getBody() {
        return body;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public float getX() { return x;}
    public float getY() { return y;}
    public float getWidth() {
        return width;
    }
}