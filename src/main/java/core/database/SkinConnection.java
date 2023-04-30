package core.database;

import com.badlogic.gdx.utils.Json;
import core.GameData;
import core.objects.CustomSkin;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.ArrayList;

public class SkinConnection {

    public static ArrayList<CustomSkin> getAvailableSkins() {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("limit", String.valueOf(GameData.SKINS_LIMIT))
                .build();
        String response ;
        try {
            response = ApiResponse.getResponse(API.AVAILABLE_SKINS, formBody);
        } catch (Exception e) {
            response = "";
        }
        return new Json().fromJson(ArrayList.class, CustomSkin.class, response);
    }

    public static ArrayList<CustomSkin> getOwnedSkins() {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .build();
        String response ;
        try {
            response = ApiResponse.getResponse(API.API_GET_OWNED_SKINS, formBody);
        } catch (Exception e) {
            response = "";
        }
        return new Json().fromJson(ArrayList.class, CustomSkin.class, response);
    }

    public static String getActiveSkins() {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .build();

        String response ;
        try {
            response = ApiResponse.getResponse(API.API_GET_ACTIVE_SKINS, formBody);
        } catch (Exception e) {
            response = "";
        }
        return response;
    }

    public static String saveActiveSkins(String player, String bullet, String cursor, String aim, String weapon) {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("playerSkin", player)
                .add("bulletSkin", bullet)
                .add("cursorSkin", cursor)
                .add("aimSkin", aim)
                .add("weaponSkin", weapon)
                .build();

        String response ;
        try {
            response = ApiResponse.getResponse(API.API_SAVE_ACTIVE_SKINS, formBody);
        } catch (Exception e) {
            response = "";
        }
        return response;
    }

    public static String buySkin(Long idSkin) {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .add("idSkin", String.valueOf(idSkin))
                .build();

        String response ;
        try {
            response = ApiResponse.getResponse(API.API_BUY_SKIN, formBody);
        } catch (Exception e) {
            response = "";
        }
        return response;
    }
}