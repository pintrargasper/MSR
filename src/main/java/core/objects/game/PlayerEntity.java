package core.objects.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class PlayerEntity {

    protected float x, y, velX, velY, speed, ladderClimbSpeed;
    protected float width, height;
    protected int maxJumpCount, jumpForce;
    protected boolean onLadder;
    protected Body body;
    protected Weapon weapon;
    protected Rectangle playerRectangle;

    public PlayerEntity(float width, float height, Body body, Weapon weapon) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = width;
        this.height = height;
        this.onLadder = false;
        this.body = body;
        this.weapon = weapon;
        this.velX = 0;
        this.velY = 0;
        this.speed = 0;
        this.ladderClimbSpeed = 3;
        this.maxJumpCount = 25;
        this.jumpForce = 12;
    }

    public abstract void render(SpriteBatch spriteBatch);
    public abstract void update();
    public boolean isOnLadder() {
        return onLadder;
    }
    public void setOnLadder(boolean onLadder) {
        this.onLadder = onLadder;
    }
    public Body getBody() {
        return body;
    }
    public Weapon getWeapon() {
        return weapon;
    }
    public float getX() { return x;}
    public float getY() { return y;}
    public float getHeight() { return height;}
    public float getWidth() { return width;}

    public Rectangle getPlayerRectangle() {
        return playerRectangle;
    }

    public void setPlayerRectangle(Rectangle playerRectangle) {
        this.playerRectangle = playerRectangle;
    }
}