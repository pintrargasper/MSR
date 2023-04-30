package core.screens.helper;

import com.badlogic.gdx.graphics.g2d.Sprite;
import core.objects.game.Bullet;
import core.objects.game.Enemy;
import core.objects.game.Player;

public class DetectionSystem {

    public Enemy detection(Enemy enemy, Player player) {
        var distance = calculateDistance(enemy.getX(), enemy.getY(), player.getX(), player.getY(), 300) ? enemy : null;
        if (distance != null) {
            enemy.getWeapon().setShow(true);
            enemy.getWeapon().setRotation((float) Math.toDegrees(Bullet.getBulletAngle(enemy, player)));
        }
        return distance;
    }

    private boolean calculateDistance(float enemyX, float enemyY, float playerX, float playerY, float maxDistance) {
        return Math.sqrt(Math.pow(enemyX - playerX, 2) + Math.pow(enemyY - playerY, 2)) < maxDistance;
    }
}