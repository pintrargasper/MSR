package core;

import core.objects.LeaderBoard;
import core.objects.Mission;
import core.screens.*;

import java.util.ArrayList;

public class ScreenChanger {

    public ScreenChanger(Boot instance) {
        GameData.INSTANCE = instance;
    }

    public ScreenChanger() {}

    public void changeScreen(int screenCode, Mission... mission) {
        var instance = GameData.INSTANCE;

        switch (screenCode) {
            case 0 -> {
                instance.setScreen(new SignInScreen());
            }
            case 1 -> {
                instance.setScreen(new MenuScreen());
            }
            case 2 -> {
                instance.setScreen(new InventoryScreen());
            }
            case 3 -> {
                instance.setScreen(new ShopScreen());
            }
            case 4 -> {
                instance.setScreen(new MissionScreen());
            }
            case 5 -> {
                instance.setScreen(new GameScreen(mission[0]));
            }
        }
    }
}