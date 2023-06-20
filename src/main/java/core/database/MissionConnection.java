package core.database;

import com.badlogic.gdx.utils.Json;
import core.GameData;
import core.database.objects.MissionBestScore;
import core.database.objects.Update;
import core.objects.Mission;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.ArrayList;

public class MissionConnection {

    public static ArrayList<Mission> getMissions() {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("limit", String.valueOf(GameData.MISSIONS_LIMIT))
                .build();
        String response;
        try {
            response = ApiResponse.getResponse(API.API_GET_MISSIONS, formBody);
        } catch (Exception e) {
            response = null;
        }
        return new Json().fromJson(ArrayList.class, Mission.class, response);
    }

    public static Update insertMissionData(Long idMap, int playerFiredBullets, int enemyKilled, int hostageKilled, String usedTime) {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("idMap", String.valueOf(idMap))
                .add("playerFiredBullets", String.valueOf(playerFiredBullets))
                .add("enemyKilled", String.valueOf(enemyKilled))
                .add("hostageKilled", String.valueOf(hostageKilled))
                .add("weaponSkin", GameData.CURRENT_WEAPON_SKIN)
                .add("weaponKills", String.valueOf(GameData.WEAPON_KILLS))
                .add("usedTime", usedTime)
                .build();
        String response;
        try {
            response = ApiResponse.getResponse(API.API_INSERT_MISSION_DATA, formBody);
        } catch (Exception e) {
            response = null;
        }
        return new Json().fromJson(Update.class, Update.class, response);
    }

    public static ArrayList<MissionBestScore> getBestScorePerMission() {
        RequestBody formBody = new FormBody.Builder()
                .add("limit", String.valueOf(GameData.MISSIONS_LIMIT))
                .build();
        String response;
        try {
            response = ApiResponse.getResponse(API.API_GET_BEST_SCORE_PER_MISSION, formBody);
        } catch (Exception e) {
            response = null;
        }
        return new Json().fromJson(ArrayList.class, MissionBestScore.class, response);
    }

    public static String gameOver(int money) {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("money", String.valueOf(money))
                .add("weaponSkin", GameData.CURRENT_WEAPON_SKIN)
                .add("weaponKills", String.valueOf(GameData.WEAPON_KILLS))
                .build();
        String response;
        try {
            response = ApiResponse.getResponse(API.API_GAME_OVER, formBody);
        } catch (Exception e) {
            response = null;
        }
        return response;
    }

    public static String buyMission(Long idMap) {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("idMap", String.valueOf(idMap))
                .build();
        String response;
        try {
            response = ApiResponse.getResponse(API.API_BUY_MISSION, formBody);
        } catch (Exception e) {
            response = null;
        }
        return response;
    }
}