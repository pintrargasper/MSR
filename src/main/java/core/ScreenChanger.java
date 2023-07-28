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

    public void changeScreen(int screenCode) {
        var instance = GameData.INSTANCE;

        switch (screenCode) {
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
            case 6 -> {
                instance.setScreen(new NoConnectionScreen());
            }
        }
    }

    public void changeScreen(int screenCode, ArrayList<LeaderBoard> leaderBoard) {
        GameData.INSTANCE.setScreen(new SignInScreen(leaderBoard));
    }

    public void changeScreen(int screenCode, Mission mission) {
        GameData.INSTANCE.setScreen(new GameScreen(mission));
    }
}