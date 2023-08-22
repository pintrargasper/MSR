package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import core.database.objects.MissionBestScore;
import core.database.objects.Settings;
import core.objects.Account;
import core.objects.CustomSkin;
import core.objects.Mission;

import java.util.ArrayList;

public class GameData {

    //Basic
    public static Boot INSTANCE;
    public static int PPM = 32;
    public static final int GAME_HEIGHT = 500;
    public static final int GAME_WIDTH = 500;
    public static final int MISSIONS_LIMIT = 21;
    public static final int SKINS_LIMIT = 22;

    //Music
    public static final Music BASIC_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("music/basic.mp3"));
    public static final Music MISSION_MUSIC = Gdx.audio.newMusic(Gdx.files.internal("music/mission.mp3"));

    //Player
    public static Account PLAYER_ACCOUNT;

    //Missions
    public static ArrayList<Mission> MISSIONS_LIST;
    public static ArrayList<MissionBestScore> MISSIONS_BEST_SCORE;

    //Settings
    public static Settings SETTINGS;
    public static final String[] LANGUAGES = {"Slovenscina", "Deutsch", "English"};

    //Skins
    public static final String SKIN = "game-skins/json/my-skin.json";

    public static ArrayList<CustomSkin> SKINS_LIST;
    public static ArrayList<CustomSkin> OWNED_SKINS_LIST;

    public static String CURRENT_PLAYER_SKIN;
    public static String CURRENT_BULLET_SKIN;
    public static String CURRENT_CURSOR_SKIN;
    public static String CURRENT_AIM_SKIN;
    public static String CURRENT_WEAPON_SKIN;
    public static String CURRENT_ENEMY_WEAPON_SKIN = "1";

    //ScrollPane
    public static final int SCROLL_PANE_SIZE = 1350;

    //Game
    public static int PLAYER_LIVES;
    public static int PLAYER_FIRED_BULLETS;
    public static float WEAPON_SPEED = 0f;
    public static int ENEMY_COUNT;
    public static int ENEMY_KILLED_COUNT;
    public static int ENEMY_FINAL_COUNT;
    public static int HOSTAGE_COUNT;
    public static int HOSTAGE_KILLED_COUNT;
    public static int VIP_COUNT;
    public static int VIP_KILLED_COUNT;
    public static int WEAPON_KILLS = 0;
}