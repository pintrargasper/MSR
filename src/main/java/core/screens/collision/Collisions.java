package core.screens.collision;

import com.badlogic.gdx.physics.box2d.*;
import core.GameData;
import core.SoundEffectPlayer;
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
                case "EnemyBulletEnemy", "EnemyEnemyBullet", "EnemyBulletPlayerBullet", "PlayerBulletEnemyBullet" -> {
                    addFixture(fixtureA, fixtureB);
                }
                case "HostagePlayer", "PlayerHostage", "VipPlayer", "PlayerVip" -> {
                    addFixture(fixtureA, fixtureB);
                    GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.COLLECT_EFFECT);
                }
                case "EnemyPlayerBullet", "PlayerBulletEnemy" -> {
                    addFixture(fixtureA, fixtureB);
                    GameData.WEAPON_KILLS++;
                    GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.HIT_EFFECT);
                }
                case "EnemyBulletPlayer", "PlayerEnemyBullet" -> {
                    addFixture(fixtureA, fixtureB);
                    navigationBar.playerLives();
                    GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.HIT_EFFECT);
                }
                case "HostagePlayerBullet", "PlayerBulletHostage" -> {
                    addFixture(fixtureA, fixtureB);
                    GameData.HOSTAGE_KILLED_COUNT++;
                    GameData.WEAPON_KILLS++;
                    GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.HIT_EFFECT);
                }
                case "HostageEnemyBullet", "EnemyBulletHostage" -> {
                    addFixture(fixtureA, fixtureB);
                    GameData.HOSTAGE_KILLED_COUNT++;
                    GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.HIT_EFFECT);
                }
                case "VipEnemyBullet", "EnemyBulletVip" -> {
                    addFixture(fixtureA, fixtureB);
                    GameData.VIP_KILLED_COUNT++;
                    GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.HIT_EFFECT);
                }
                case "VipPlayerBullet", "PlayerBulletVip" -> {
                    addFixture(fixtureA, fixtureB);
                    GameData.VIP_KILLED_COUNT++;
                    GameData.WEAPON_KILLS++;
                    GameData.SOUND_EFFECT_PLAYER.playEffect(SoundEffectPlayer.SoundEffectType.HIT_EFFECT);
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

    private void addFixture(Fixture fixtureA, Fixture fixtureB) {
        bodiesList.add(fixtureA.getBody());
        bodiesList.add(fixtureB.getBody());
    }
}