package core.screens.collision;

import com.badlogic.gdx.physics.box2d.*;
import core.GameData;
import core.objects.game.*;
import core.screens.GameScreen;
import core.views.NavigationBar;

import java.util.ArrayList;
import java.util.Objects;

public class Collisions {

    private final GameScreen gameScreen;
    private final World world;
    private final Player player;
    private final NavigationBar navigationBar;
    private final ArrayList<Body> bodiesList, bodiesListToRemove;

    public Collisions(GameScreen gameScreen, World world, Player player, NavigationBar navigationBar) {
        this.gameScreen = gameScreen;
        this.world = world;
        this.player = player;
        this.navigationBar = navigationBar;
        this.bodiesList = new ArrayList<>();
        this.bodiesListToRemove = new ArrayList<>();
    }

    public void check() {

        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {

                Fixture fixtureA = contact.getFixtureA();
                Fixture fixtureB = contact.getFixtureB();

                collision(fixtureA, fixtureB);
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold manifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse contactImpulse) {

            }
        });

        dispose();
    }

    private void collision(Fixture fixtureA, Fixture fixtureB) {

        if (fixtureA.getBody().getUserData() != null && fixtureB.getBody().getUserData() != null) {
            switch (fixtureA.getBody().getUserData().toString() + fixtureB.getBody().getUserData().toString()) {
                case "EnemyPlayerBullet", "PlayerBulletEnemy", "EnemyBulletEnemy",
                        "EnemyEnemyBullet", "HostagePlayer", "PlayerHostage", "VipPlayer", "PlayerVip", "EnemyBulletPlayerBullet", "PlayerBulletEnemyBullet" -> {
                    bodiesList.add(fixtureA.getBody());
                    bodiesList.add(fixtureB.getBody());
                }
                case "EnemyBulletPlayer", "PlayerEnemyBullet" -> {
                    bodiesList.add(fixtureA.getBody());
                    bodiesList.add(fixtureB.getBody());
                    navigationBar.playerLives();
                }
                case "HostagePlayerBullet", "PlayerBulletHostage", "HostageEnemyBullet", "EnemyBulletHostage" -> {
                    bodiesList.add(fixtureA.getBody());
                    bodiesList.add(fixtureB.getBody());
                    GameData.HOSTAGE_KILLED_COUNT++;
                }
                case "VipPlayerBullet", "PlayerBulletVip", "VipEnemyBullet", "EnemyBulletVip" -> {
                    bodiesList.add(fixtureA.getBody());
                    bodiesList.add(fixtureB.getBody());
                    GameData.VIP_KILLED_COUNT++;
                }
                default -> {

                }
            }
        }

        if ((Objects.equals(fixtureA.getBody().getUserData(), "PlayerBullet") && Objects.equals(fixtureB.getBody().getType(), BodyDef.BodyType.StaticBody))
                || Objects.equals(fixtureA.getBody().getUserData(), "EnemyBullet") && Objects.equals(fixtureB.getBody().getType(), BodyDef.BodyType.StaticBody)) {
            bodiesList.add(fixtureA.getBody());
        } else if ((Objects.equals(fixtureB.getBody().getUserData(), "PlayerBullet") && Objects.equals(fixtureA.getBody().getType(), BodyDef.BodyType.StaticBody))
                || Objects.equals(fixtureB.getBody().getUserData(), "EnemyBullet") && Objects.equals(fixtureA.getBody().getType(), BodyDef.BodyType.StaticBody)) {
            bodiesList.add(fixtureB.getBody());
        }
    }

    private void dispose() {
        for (Body body : bodiesList) {

            switch (body.getUserData().toString()) {
                case "PlayerBullet" -> {
                    for (Bullet bullet : gameScreen.playerBulletsList) {
                        if (bullet.getBody() == body) {
                            bullet.destroyBullet();
                            bodiesListToRemove.add(body);
                            gameScreen.removePlayerBulletsList.add(bullet);
                        }
                    }
                }

                case "EnemyBullet" -> {
                    for (Bullet bullet : gameScreen.enemyBulletsList) {
                        if (bullet.getBody() == body) {
                            bullet.destroyBullet();
                            bodiesListToRemove.add(body);
                            gameScreen.removeEnemyBulletsList.add(bullet);
                        }
                    }
                }

                case "Player" -> {
                    if (player.getBody() == body) {
                        bodiesListToRemove.add(body);
                    }
                }

                case "Enemy" -> {
                    for (Enemy enemy : gameScreen.enemyList) {
                        if (enemy.getBody() == body) {
                            enemy.destroyEnemy();
                            navigationBar.enemyKill();
                            bodiesListToRemove.add(body);
                            gameScreen.removeEnemyList.add(enemy);
                        }
                    }
                }

                case "Hostage" -> {
                    for (Hostage hostage : gameScreen.hostageList) {
                        if (hostage.getBody() == body) {
                            hostage.destroyHostage();
                            navigationBar.hostageCollect();
                            bodiesListToRemove.add(body);
                            gameScreen.removeHostageList.add(hostage);
                        }
                    }
                }

                case "Vip" -> {
                    for (Vip vip : gameScreen.vipList) {
                        if (vip.getBody() == body) {
                            vip.destroyVip();
                            navigationBar.vipCollect();
                            bodiesListToRemove.add(body);
                            gameScreen.removeVipList.add(vip);
                        }
                    }
                }
            }
        }

        bodiesList.removeAll(bodiesListToRemove);
        gameScreen.playerBulletsList.removeAll(gameScreen.removePlayerBulletsList);
        gameScreen.enemyBulletsList.removeAll(gameScreen.removeEnemyBulletsList);
        gameScreen.enemyList.removeAll(gameScreen.removeEnemyList);
        gameScreen.hostageList.removeAll(gameScreen.removeHostageList);
        gameScreen.vipList.removeAll(gameScreen.removeVipList);
    }
}