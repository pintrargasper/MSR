package core.database;

import com.badlogic.gdx.utils.Json;
import core.GameData;
import core.objects.Account;
import core.objects.LeaderBoard;
import okhttp3.FormBody;
import okhttp3.RequestBody;

import java.util.ArrayList;

public class AccountConnection {

    public static Account getAccountDetails() {
        RequestBody formBody = new FormBody.Builder()
                .add("idUser", String.valueOf(GameData.PLAYER_ACCOUNT.getId()))
                .build();
        String response ;
        try {
            response = ApiResponse.getResponse(API.API_GET_ACCOUNT_DETAILS, formBody);
        } catch (Exception e) {
            response = "";
        }
        return new Json().fromJson(Account.class, Account.class, response);
    }

    public static ArrayList<LeaderBoard> getLeaderBoard() {
        RequestBody formBody = new FormBody.Builder()
                .build();
        String response ;
        try {
            response = ApiResponse.getResponse(API.API_GET_LEADERBOARD, formBody);
        } catch (Exception e) {
            response = "";
        }
        return new Json().fromJson(ArrayList.class, LeaderBoard.class, response);
    }
}