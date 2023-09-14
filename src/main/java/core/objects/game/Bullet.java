package core.objects.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import core.GameData;

public class Bullet extends BulletEntity {

    private final Sprite sprite;
    public boolean remove;

    public Bullet(Body body, float angle, String bulletSkin) {
        super(body, angle);
        this.speed = 20f;
        this.sprite = new Sprite(new Texture(String.format("pictures/skins/bullet/%s/bullet-%s", bulletSkin, bulletSkin) + "-stand.png"));
        this.remove = false;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite, x, y, width, height);
    }

    @Override
    public void update() {
        x = body.getPosition().x * GameData.PPM;
        y = body.getPosition().y * GameData.PPM;

        body.setLinearVelocity((float) (speed * Math.cos(angle)), (float) (speed * Math.sin(angle)));
    }

    public void destroyBullet() {
        if (body.getFixtureList().size > 0) {
            body.destroyFixture(body.getFixtureList().first());
        }
    }

    public static float getBulletAngle(Player player, OrthographicCamera camera) {
        return (float) Math.atan2(diffY(camera, player), diffX(camera, player));
    }

    public static float getBulletAngle(Enemy enemy, Player player) {
        return (float) Math.atan2(diffY(player, enemy), diffX(player, enemy));
    }

    public static float getBulletAngle(Weapon weapon, OrthographicCamera camera) {
        return (float) Math.atan2(diffY(camera, weapon), diffX(camera, weapon));
    }

    public static float diffX(OrthographicCamera orthographicCamera, Player player) {
        int xMouse = Gdx.input.getX();
        var XMouse = xMouse + orthographicCamera.position.x - Gdx.graphics.getWidth() / 2;
        return XMouse - player.getX();
    }

     public static float diffY(OrthographicCamera camera, Weapon weapon) {
        int yMouse = Gdx.graphics.getHeight() - Gdx.input.getY();
        var YMouse = yMouse + camera.position.y - Gdx.graphics.getHeight() / 2;
        return YMouse - (weapon.getY());
    }

    public static float diffX(Player player, Enemy enemy) {
        return player.getX() - enemy.getX();
    }

    public static float diffY(Player player, Enemy enemy) {
        return player.getY() - enemy.getY();
    }

    public static float diffY(OrthographicCamera orthographicCamera, Player player) {
        int yMouse = Gdx.graphics.getHeight() - Gdx.input.getY();
        var YMouse = yMouse + orthographicCamera.position.y - Gdx.graphics.getHeight() / 2;
        return YMouse - (player.getY() + 17);
    }

    public static float diffX(OrthographicCamera orthographicCamera, Weapon weapon) {
        int xMouse = Gdx.input.getX();
        var XMouse = xMouse + orthographicCamera.position.x - Gdx.graphics.getWidth() / 2;
        return XMouse - weapon.getX();
    }
}