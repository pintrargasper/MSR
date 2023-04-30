package core.database;

import com.badlogic.gdx.utils.Json;
import core.GameData;
import core.database.objects.Settings;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class SettingsConnection {

    public static Settings getSettings() {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .build();
        String response ;
        try {
            response = ApiResponse.getResponse(API.API_GET_SETTINGS, formBody);
        } catch (Exception e) {
            response = "";
        }
        return new Json().fromJson(Settings.class, Settings.class, response);
    }

    public static String updateSettings(int music, int soundEffect, String language,int upCode, int leftCode, int rightCode, int shootCode, int pauseCode) {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("music", String.valueOf(music))
                .add("soundEffect", String.valueOf(soundEffect))
                .add("language", language)
                .add("keyUp", String.valueOf(upCode))
                .add("keyLeft", String.valueOf(leftCode))
                .add("keyRight", String.valueOf(rightCode))
                .add("keyShoot", String.valueOf(shootCode))
                .add("keyPause", String.valueOf(pauseCode))
                .build();

        String response ;
        try {
            response = ApiResponse.getResponse(API.API_UPDATE_SETTINGS, formBody);
        } catch (Exception e) {
            response = "";
        }
        return response;
    }
}