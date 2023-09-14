package core.objects.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.TimeUtils;
import core.GameData;
import core.SoundEffectPlayer;
import core.screens.GameScreen;
import core.screens.helper.BodyHelper;

public class Enemy extends EnemyEntity {

    private Sprite sprite;
    private final Sprite leftSprite, rightSprite;
    private final GameScreen gameScreen;
    private final OrthographicCamera orthographicCamera;
    private final BodyHelper bodyHelper;
    public boolean direction = true;

    public Enemy(float width, float height, Body body, GameScreen gameScreen, OrthographicCamera orthographicCamera, BodyHelper bodyHelper, Weapon weapon) {
        super(width, height, body, weapon);
        this.speed = 10f;
        this.sprite = new Sprite(new Texture(String.format("pictures/skins/enemy/%s/enemy-%s", 1, 1) + "-stand.png"));
        this.leftSprite = new Sprite(new Texture(String.format("pictures/skins/enemy/%s/enemy-%s", 1, 1) + "-left.png"));
        this.rightSprite = new Sprite(new Texture(String.format("pictures/skins/enemy/%s/enemy-%s", 1, 1) + "-right.png"));
        this.gameScreen = gameScreen;
        this.orthographicCamera = orthographicCamera;
        this.bodyHelper = bodyHelper;
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(sprite, x, y, width, height);
    }

    @Override
    public void update() {
        x = body.getPosition().x * GameData.PPM;
        y = body.getPosition().y * GameData.PPM;
    }

    public void destroyEnemy() {
        if (body.getFixtureList().size > 0) {
            body.destroyFixture(body.getFixtureList().first());
        }
    }

    public void shoot(Player player) {
        long timer = 1000000000L;
        var enemyWidth = player.getX() < this.getX() ? -10 : 24;

        if (enemyWidth < 0) {
            setSprite(leftSprite);
            direction = true;
        } else {
            setSprite(rightSprite);
            direction = false;
        }

        if (TimeUtils.timeSinceNanos(startTime) >= timer) {
            GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.ENEMY_SHOOT_EFFECT);
            float angle = Bullet.getBulletAngle(this, player);
            double weaponLength = Math.sqrt(Math.pow(weapon.getHeight(), 2) + Math.pow(weapon.getWidth(),2));

            float bulletX = !this.direction ? (float) (weapon.getX() + Math.cos(angle) * weaponLength) :
                    (float) (weapon.getX() - Math.cos(angle) / weaponLength - ((weapon.getWidth() / 2) + this.getWidth()));
            float bulletY = (float) (weapon.getY() + Math.sin(angle) * weaponLength);

            Body body = bodyHelper.createObjectBody(5, 5, bulletX, bulletY, "Bullet");
            body.setUserData("EnemyBullet");
            gameScreen.enemyBulletsList.add(new Bullet(body, angle, GameData.CURRENT_ENEMY_BULLET_SKIN));
            startTime = TimeUtils.nanoTime();
        }
    }

    private void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}