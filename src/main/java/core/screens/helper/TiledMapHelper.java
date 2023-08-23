package core.screens.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import core.GameData;
import core.objects.game.*;
import core.screens.GameScreen;

public class TiledMapHelper {

    private final GameScreen gameScreen;
    private final World world;
    private final OrthographicCamera orthographicCamera;
    private final BodyHelper bodyHelper;
    private TiledMap tiledMap;

    public TiledMapHelper(GameScreen gameScreen, World world, OrthographicCamera orthographicCamera) {
        this.gameScreen = gameScreen;
        this.world = world;
        this.orthographicCamera = orthographicCamera;
        this.bodyHelper = new BodyHelper(world);
    }

    public OrthogonalTiledMapRenderer setupMap(String mission) {
        this.tiledMap = new TmxMapLoader().load("missions/" + mission);
        MapProperties prop = tiledMap.getProperties();

        GameData.PLAYER_LIVES = 3;
        GameData.PLAYER_FIRED_BULLETS = 0;
        GameData.ENEMY_COUNT = 0;
        GameData.ENEMY_KILLED_COUNT = 0;
        GameData.HOSTAGE_COUNT = 0;
        GameData.HOSTAGE_KILLED_COUNT = 0;
        GameData.VIP_COUNT = 0;
        GameData.VIP_KILLED_COUNT = 0;
        GameData.WEAPON_KILLS = 0;

        MapObjects objects = tiledMap.getLayers().get("Objects").getObjects();
        parseMapObject(objects);

        GameData.ENEMY_FINAL_COUNT = GameData.ENEMY_COUNT;

        return new OrthogonalTiledMapRenderer(tiledMap);
    }

    private void parseMapObject(MapObjects mapObjects) {

        for (MapObject mapObject : mapObjects) {
            if (mapObject instanceof PolygonMapObject) {
                createStaticBody((PolygonMapObject) mapObject);
            }
            if (mapObject instanceof TiledMapTileMapObject) {
                if (mapObject.getName() != null && mapObject.getName().equals("Player")) {

                    var data = objectData(mapObject);

                    Body playerBody = bodyHelper.createObjectBody(data.getWidth(), data.getHeight(), data.getPosX(), data.getPosY(), "Player");
                    playerBody.setUserData("Player");

                    Body weaponBody = bodyHelper.createObjectBody(20, 7, playerBody.getPosition().x * GameData.PPM, playerBody.getPosition().y * GameData.PPM, "Player");
                    weaponBody.setUserData("Weapon");

                    Weapon weapon = new Weapon(20 * 1.5f, 7 * 1.5f, weaponBody, GameData.WEAPON_SPEED, GameData.CURRENT_WEAPON_SKIN);
                    Player player = new Player((data.getWidth() * 1.5f), (data.getHeight() * 1.5f), playerBody, gameScreen, world, orthographicCamera, weapon);
                    player.setPlayerRectangle(new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight()));
                    gameScreen.setPlayer(player);
                } else if (mapObject.getName() != null && mapObject.getName().equals("Enemy")) {

                    var data = objectData(mapObject);

                    Body enemyBody = bodyHelper.createObjectBody(data.getWidth(), data.getHeight(), data.getPosX(), data.getPosY(), "Enemy");
                    enemyBody.setUserData("Enemy");

                    Body weaponBody = bodyHelper.createObjectBody(20, 7, enemyBody.getPosition().x * GameData.PPM, enemyBody.getPosition().y * GameData.PPM, "Enemy");
                    weaponBody.setUserData("Weapon");

                    Weapon weapon = new Weapon(20 * 1.5f, 7 * 1.5f, weaponBody, GameData.WEAPON_SPEED, GameData.CURRENT_ENEMY_WEAPON_SKIN);
                    gameScreen.enemyList.add(new Enemy(data.getWidth() * 1.5f, data.getHeight() * 1.5f, enemyBody, gameScreen, orthographicCamera, bodyHelper, weapon));
                    GameData.ENEMY_COUNT += 1;
                } else if (mapObject.getName() != null && mapObject.getName().equals("Hostage")) {

                    var data = objectData(mapObject);

                    Body body = bodyHelper.createObjectBody(data.getWidth(), data.getHeight(), data.getPosX(), data.getPosY(), "Hostage");
                    body.setUserData("Hostage");
                    gameScreen.hostageList.add(new Hostage(data.getWidth() * 1.5f, data.getHeight() * 1.5f, body));
                    GameData.HOSTAGE_COUNT += 1;
                } else if (mapObject.getName() != null && mapObject.getName().equals("Vip")) {

                    var data = objectData(mapObject);

                    Body body = bodyHelper.createObjectBody(data.getWidth(), data.getHeight(), data.getPosX(), data.getPosY(), "Vip");
                    body.setUserData("Vip");
                    gameScreen.vipList.add(new Vip(data.getWidth() * 1.5f, data.getHeight() * 1.5f, body));
                    GameData.VIP_COUNT += 1;
                }
            } else if (mapObject instanceof RectangleMapObject) {
                if (mapObject.getName() != null && mapObject.getName().equals("Ladder")) {
                    Rectangle rectangle = ((RectangleMapObject) mapObject).getRectangle();
                    gameScreen.ladderList.add(new Rectangle(rectangle));
                }
            }
        }
    }

    private void createStaticBody(PolygonMapObject polygonMapObject) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        Body body = world.createBody(bodyDef);
        Shape shape = createPolygonShape(polygonMapObject);
        body.createFixture(shape, 1000);
        shape.dispose();
    }

    private Shape createPolygonShape(PolygonMapObject polygonMapObject) {
        float[] vertices = polygonMapObject.getPolygon().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; i++) {
            Vector2 current = new Vector2(vertices[i * 2] / GameData.PPM, vertices[i * 2 + 1] / GameData.PPM);
            worldVertices[i] = current;
        }

        PolygonShape shape = new PolygonShape();
        shape.set(worldVertices);
        return shape;
    }

    private ObjectData objectData(MapObject mapObject) {
        int height = ((TiledMapTileMapObject) mapObject).getTile().getTextureRegion().getRegionHeight();
        int width = ((TiledMapTileMapObject) mapObject).getTile().getTextureRegion().getRegionWidth();
        float posX = ((TiledMapTileMapObject) mapObject).getX();
        float posY = ((TiledMapTileMapObject) mapObject).getY();
        return new ObjectData(height, width, posX, posY);
    }
}