package core.objects.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Weapon extends WeaponEntity {

    private Sprite sprite;
    private final Sprite leftSprite, rightSprite;

    public Weapon(float width, float height, int bullets, Body body, float speed, String type) {
        super(width, height, bullets, speed, body);
        this.leftSprite = new Sprite(new Texture("pictures/skins/weapon/" + type + "/weapon-" + type + "-left.png"));
        this.rightSprite = new Sprite(new Texture("pictures/skins/weapon/" + type + "/weapon-" + type + "-right.png"));
        this.sprite = rightSprite;
        body.destroyFixture(body.getFixtureList().first());
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite, x, y, 0, height / 2, width, height, 1, 1, rotation);
    }

    @Override
    public void update(Player player, OrthographicCamera camera) {

        if (player.direction) {
            leftSprite.setFlip(true, true);
            setSprite(leftSprite);
        } else {
            setSprite(rightSprite);
        }

        rotation = (float) Math.toDegrees(Bullet.getBulletAngle(player, camera));

        x = player.getX() + player.getWidth() / 2;
        y = player.getY() + 17;
    }

    @Override
    public void update(Enemy enemy, Player player) {

        var direction = enemy.direction ? 0 : 21;

        if (direction == 0) {
            leftSprite.setFlip(true, true);
            setSprite(leftSprite);
        } else {
            setSprite(rightSprite);
        }

        x = enemy.getX() + direction;
        y = enemy.getY() + 17;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}