package core.objects.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class BulletEntity {

    protected Body body;
    protected float x, y, width, height, angle, speed;

    public BulletEntity(Body body, float angle) {
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
        this.width = 5 * 1.5f;
        this.height = 5 * 1.5f;
        this.body = body;
        this.angle = angle;
        this.speed = 0;
    }

    public abstract void render(SpriteBatch spriteBatch);
    public abstract void update();
    public Body getBody() {
        return body;
    }
}