package core.database;

public class API {

    public static final String HOST = "https://api.memostickrescue.eu.org/";
    //public static final String HOST = "http://localhost:8079/";

    //Account
    public static final String API_SIGN_IN = HOST + "web-sign-in";
    public static final String API_GET_ACCOUNT_DETAILS = HOST + "game-get-account-details";
    public static final String API_GET_LEADERBOARD = HOST + "web-get-leader-board-statistics";

    //Missions
    public static final String API_GET_MISSIONS = HOST + "game-get-missions";
    public static final String API_GET_BEST_SCORE_PER_MISSION = HOST + "game-best-mission-score";
    public static final String API_INSERT_MISSION_DATA = HOST + "game-insert-mission-data";
    public static final String API_GAME_OVER = HOST + "game-game-over";
    public static final String API_BUY_MISSION = HOST + "game-buy-mission";

    //Settings
    public static final String API_GET_SETTINGS = HOST + "game-get-settings";
    public static final String API_UPDATE_SETTINGS = HOST + "game-update-settings";

    //Skins
    public static final String AVAILABLE_SKINS = HOST + "game-get-available-skins";
    public static final String API_GET_OWNED_SKINS = HOST + "game-get-owned-skins";
    public static final String API_GET_ACTIVE_SKINS = HOST + "game-get-active-skins";
    public static final String API_SAVE_ACTIVE_SKINS = HOST + "game-set-active-skins";
    public static final String API_BUY_SKIN = HOST + "game-buy-skin";
}