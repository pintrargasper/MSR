package core.objects.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;

public abstract class WeaponEntity {

    protected float x, y, velX, velY, speed;
    protected float width, height, rotation;
    protected boolean show;
    protected Long startTime;
    protected Body body;

    public WeaponEntity(float width, float height, float speed, Body body) {
        this.width = width;
        this.height = height;
        this.startTime = TimeUtils.nanoTime();
        this.body = body;
        this.velX = 0;
        this.velY = 0;
        this.speed = speed;
        this.show = false;
    }

    public abstract void render(SpriteBatch spriteBatch);
    public abstract void update(Player player, OrthographicCamera orthographicCamera);
    public abstract void update(Enemy enemy, Player player);

    public Body getBody() {
        return body;
    }
    public float getX() { return x;}
    public float getY() { return y;}
    public float getWidth() { return width;}
    public float getHeight() { return height;}
    public boolean getShow() { return show;}
    public void setShow(boolean show) { this.show = show;}

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }
    public float getSpeed() {
        return speed;
    }
}