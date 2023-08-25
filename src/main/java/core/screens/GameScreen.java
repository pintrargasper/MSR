package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import core.GameData;
import core.Utils;
import core.database.objects.Settings;
import core.objects.Mission;
import core.objects.game.*;
import core.screens.collision.Collisions;
import core.screens.helper.DetectionSystem;
import core.screens.helper.DurationTimer;
import core.screens.helper.TiledMapHelper;
import core.views.GameScreenView;

import java.util.ArrayList;

public class GameScreen extends ScreenAdapter {

    private final Mission mission;
    private final Stage stage;
    private final SpriteBatch spriteBatch;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private final OrthographicCamera orthographicCamera;
    private final TiledMapHelper tiledMapHelper;
    private final OrthogonalTiledMapRenderer orthogonalTiledMapRenderer;
    private final Collisions collisions;
    private final DetectionSystem detectionSystem;
    private final GameScreenView gameScreenView;
    private final DurationTimer durationTimer;
    private final Settings settings;
    private final Utils utils;
    private Player player;
    public ArrayList<Bullet> playerBulletsList, removePlayerBulletsList, enemyBulletsList, removeEnemyBulletsList;
    public ArrayList<Enemy> enemyList, removeEnemyList;
    public ArrayList<Hostage> hostageList, removeHostageList;
    public ArrayList<Vip> vipList, removeVipList;
    public ArrayList<Rectangle> ladderList;
    public GameStats gameStats;

    public enum GameStats {
        IN_PROCESS,
        PAUSE,
        FINNISH,
        OVER
    }

    public GameScreen(Mission mission) {
        this.mission = mission;
        this.stage = new Stage(new FitViewport(0, 0));
        this.spriteBatch = new SpriteBatch();
        this.world = new World(new Vector2(0, -25f), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.orthographicCamera = new OrthographicCamera();
        playerBulletsList = new ArrayList<>();
        removePlayerBulletsList = new ArrayList<>();
        enemyBulletsList = new ArrayList<>();
        removeEnemyBulletsList = new ArrayList<>();
        hostageList = new ArrayList<>();
        removeHostageList = new ArrayList<>();
        enemyList = new ArrayList<>();
        removeEnemyList = new ArrayList<>();
        vipList = new ArrayList<>();
        removeVipList = new ArrayList<>();
        ladderList = new ArrayList<>();
        this.tiledMapHelper = new TiledMapHelper(this, world, orthographicCamera);
        this.orthogonalTiledMapRenderer = tiledMapHelper.setupMap(mission.getMap());
        this.detectionSystem = new DetectionSystem();
        this.gameScreenView = new GameScreenView(this, stage, mission);
        this.durationTimer = new DurationTimer(gameScreenView.getNavigationBar());
        this.settings = GameData.SETTINGS;
        this.utils = new Utils();
        this.collisions = new Collisions(this, world, player, gameScreenView.getNavigationBar());
        this.gameStats = GameStats.IN_PROCESS;

        orthographicCamera.setToOrtho(false, Gdx.graphics.getWidth() / 4f, Gdx.graphics.getHeight() / 4f);

        stage.addActor(gameScreenView.getView(mission.getName()));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(255f, 255f, 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));

        orthogonalTiledMapRenderer.setView(orthographicCamera);
        orthogonalTiledMapRenderer.render();

        spriteBatch.setProjectionMatrix(orthographicCamera.combined);
        spriteBatch.begin();

        //box2DDebugRenderer.render(world, orthographicCamera.combined.scl(GameData.PPM));

        player.render(spriteBatch);
        player.getWeapon().render(spriteBatch);

        for (Enemy enemy : enemyList) {
            enemy.render(spriteBatch);
            if (enemy.getWeapon().getShow()) {
                enemy.getWeapon().render(spriteBatch);
            }
        }

        for (Bullet bullet : playerBulletsList) {
            bullet.render(spriteBatch);
        }

        for (Bullet bullet : enemyBulletsList) {
            bullet.render(spriteBatch);
        }

        for (Hostage hostage : hostageList) {
            hostage.render(spriteBatch);
        }

        for (Vip vip : vipList) {
            vip.render(spriteBatch);
        }

        spriteBatch.end();

        if (gameStats == GameStats.IN_PROCESS) {
            update(delta);
        } else {
            centerCameraOnPlayer();
        }

        if (utils.isButtonOrKeyJustPressed(settings.getKeyPauseCode())) {
            if (gameStats == GameStats.IN_PROCESS) {
                gameScreenView.showPausePopup();
            } else {
                gameScreenView.closePausePopup();
            }
        }

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(new FitViewport(width, height));
        stage.getViewport().update(width, height, true);

        gameScreenView.getPausePopup().resize(width, height);
        gameScreenView.getGameFinnishPopup().resize(width, height);
        gameScreenView.getGameOverPopup().resize(width, height);

        orthographicCamera.setToOrtho(false, width, height);
    }

    @Override
    public void pause() {
        if (gameStats == GameStats.IN_PROCESS) {
            gameStats = GameStats.PAUSE;
            gameScreenView.showPausePopup();
        }
    }

    private void update(float delta) {
        world.step(delta, 6, 2);
        world.clearForces();

        centerCameraOnPlayer();

        player.update();
        player.getWeapon().update(player, orthographicCamera);

        for (Enemy enemy : enemyList) {
            enemy.update();
            if (enemy.getWeapon().getShow()) {
                enemy.getWeapon().update(enemy, player);
            }
            Enemy getEnemy = detectionSystem.detection(enemy, player);
            if (getEnemy != null) {
                getEnemy.shoot(player);
            }
        }

        for (Bullet bullet : playerBulletsList) {
            bullet.update();
        }

        for (Bullet bullet : enemyBulletsList) {
            bullet.update();
        }

        for (Hostage hostage : hostageList) {
            hostage.update();
        }

        for (Vip vip : vipList) {
            vip.update();
        }

        durationTimer.timer();
        collisions.check();
        checkGameOver();
        checkGameFinnish();
    }

    private void centerCameraOnPlayer() {
        var newX = player.getBody().getPosition().x * GameData.PPM;
        var newY = player.getBody().getPosition().y * GameData.PPM;

        orthographicCamera.position.x = newX;
        orthographicCamera.position.y = newY;
        orthographicCamera.update();
    }

    private void checkGameFinnish() {
        if (GameData.ENEMY_COUNT == 0 && GameData.HOSTAGE_COUNT == 0 && GameData.HOSTAGE_KILLED_COUNT < 2 && GameData.VIP_COUNT == 0 && GameData.VIP_KILLED_COUNT == 0) {
            GameData.SOUND_EFFECT_PLAYER.stopAll();
            gameScreenView.showGameFinnishPopup(mission, durationTimer);
        }
    }

    private void checkGameOver() {
        if (GameData.PLAYER_LIVES == 0) {
            endGame("lives");
        } else {
            if (GameData.VIP_KILLED_COUNT != 0 && GameData.VIP_COUNT == 0 && GameData.HOSTAGE_COUNT == 0 && GameData.ENEMY_COUNT == 0) {
                endGame("vip");
            } else if (GameData.HOSTAGE_KILLED_COUNT >= 2 && GameData.HOSTAGE_COUNT == 0 && GameData.VIP_COUNT == 0 && GameData.ENEMY_COUNT == 0) {
                endGame("hostage");
            }
        }
    }

    private void endGame(String reason) {
        GameData.SOUND_EFFECT_PLAYER.stopAll();
        gameScreenView.showGameOverPopup(reason);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}