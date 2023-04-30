package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import core.*;

public class NavigationBar {

    private final Skin skin;
    private Table signInNavigationTable, menuNavigationTable, menuCombineTable, gameNavigationTable, playerLivesTable, playerLivesCombineTable, enemyTable, enemyCombineTable, hostageTable, hostageCombineTable, vipTable, vipCombineTable;
    private Label gameNameLabel, emptyLabel, usernameLabel, rankLabel, missionLabel, titlePlayerLivesLabel, playerCountLivesLabel, titleEnemyLabel, enemyCountLabel, titleHostageLabel, hostageCountLabel, titleVipLabel, vipCountLabel, timerLabel;
    private static Label moneyLabel;
    private TextButton signUpButton, forgotPasswordButton, inventoryButton, shopButton, backToMenuButton;
    private Image playerLivesImage, enemyImage, hostageImage, vipImage;
    private ScreenChanger screenChanger;

    public NavigationBar() {
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.menuNavigationTable = new Table();
        this.menuCombineTable = new Table();
        this.gameNavigationTable = new Table();
        this.playerLivesTable = new Table();
        this.playerLivesCombineTable = new Table();
        this.enemyTable = new Table();
        this.enemyCombineTable = new Table();
        this.hostageTable = new Table();
        this.hostageCombineTable = new Table();
        this.vipTable = new Table();
        this.vipCombineTable = new Table();
        this.emptyLabel = new Label("", skin);
        this.usernameLabel = new Label(GameData.PLAYER_ACCOUNT.getUsername(), skin);
        this.rankLabel = new Label(Language.get("button_rank") + ": " + GameData.PLAYER_ACCOUNT.getRank(), skin);
        this.moneyLabel = new Label(Language.get("button_money") + ": " + Money.format(GameData.PLAYER_ACCOUNT.getMoney()), skin);
        this.missionLabel = new Label("", skin);
        this.titlePlayerLivesLabel = new Label(Language.get("label_lives"), skin);
        this.playerCountLivesLabel = new Label(String.valueOf(GameData.PLAYER_LIVES), skin);
        this.titleEnemyLabel = new Label(Language.get("label_enemy"), skin);
        this.enemyCountLabel = new Label(String.valueOf(GameData.ENEMY_COUNT), skin);
        this.titleHostageLabel = new Label(Language.get("label_hostage"), skin);
        this.hostageCountLabel = new Label(String.valueOf(GameData.HOSTAGE_COUNT), skin);
        this.titleVipLabel = new Label(Language.get("label_vip"), skin);
        this.vipCountLabel = new Label(String.valueOf(GameData.VIP_COUNT), skin);
        this.timerLabel = new Label(Language.get("label_nav_timer") + ": 00:00:00", skin);
        this.inventoryButton = new TextButton(Language.get("button_inventory"), skin);
        this.shopButton = new TextButton(Language.get("button_shop"), skin);
        this.backToMenuButton = new TextButton(Language.get("button_back_to_menu"), skin);
        this.playerLivesImage = new Image(new Texture("pictures/skins/other/heart.png"));
        this.enemyImage = new Image(new Texture("pictures/skins/enemy/1/enemy-1-stand.png"));
        this.hostageImage = new Image(new Texture("pictures/skins/hostage/1/hostage-1-stand.png"));
        this.vipImage = new Image(new Texture("pictures/skins/vip/1/vip-1-stand.png"));

        this.screenChanger = new ScreenChanger();
    }

    public NavigationBar(boolean signIn) {
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.signInNavigationTable = new Table();
        this.gameNameLabel = new Label("Memo Stick Rescue", skin);
        this.signUpButton = new TextButton("Sign up", skin);
        this.forgotPasswordButton = new TextButton("Forgot password", skin);
    }

    public Table signInNavigationBar() {
        signInNavigationTable.setBackground(Background.setBackground(Background.lightBlue));

        signInNavigationTable.add(gameNameLabel).pad(0, 50, 0, 10).width(150).height(50);
        signInNavigationTable.add(emptyLabel).height(50).growX();
        signInNavigationTable.add(signUpButton).width(100).height(50);
        signInNavigationTable.add(forgotPasswordButton).pad(0, 10, 0, 0).width(150).height(50);

        signUpButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://memostickrescue.eu.org/signup");
            }
        });

        forgotPasswordButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://memostickrescue.eu.org/forgot-password");
            }
        });
        return signInNavigationTable;
    }

    public Table menuNavigationBar() {
        menuNavigationTable.setBackground(Background.setBackground(Background.lightBlue));

        usernameLabel.setAlignment(Align.left);
        rankLabel.setAlignment(Align.left);

        menuCombineTable.add(usernameLabel).width(140).height(25);
        menuCombineTable.row();
        menuCombineTable.add(rankLabel).width(140).height(25);

        menuNavigationTable.add(menuCombineTable).pad(0, 50, 0, 10).height(50);
        menuNavigationTable.add(moneyLabel).width(200).height(50);
        menuNavigationTable.add(emptyLabel).height(50).growX();
        menuNavigationTable.add(inventoryButton).width(100).height(50);
        menuNavigationTable.add(shopButton).pad(0, 10, 0, 10).width(100).height(50);

        inventoryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenChanger.changeScreen(2);
            }
        });

        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenChanger.changeScreen(3);
            }
        });
        return menuNavigationTable;
    }

    public Table basicNavigationBar() {
        menuNavigationTable.setBackground(Background.setBackground(Background.lightBlue));

        usernameLabel.setAlignment(Align.left);
        rankLabel.setAlignment(Align.left);

        menuCombineTable.add(usernameLabel).width(140).height(25);
        menuCombineTable.row();
        menuCombineTable.add(rankLabel).width(140).height(25);

        menuNavigationTable.add(menuCombineTable).pad(0, 50, 0, 10).height(50);
        menuNavigationTable.add(moneyLabel).width(200).height(50);
        menuNavigationTable.add(emptyLabel).height(50).growX();
        menuNavigationTable.add(backToMenuButton).pad(0, 10, 0 , 10).height(50);

        backToMenuButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenChanger.changeScreen(1);
            }
        });
        return menuNavigationTable;
    }

    public Table gameScreenNavigationBar(String missionName) {
        gameNavigationTable.setBackground(Background.setBackground(Background.lightBlue));

        missionLabel.setText(Language.get("label_mission") + ": " + missionName);

        titleEnemyLabel.setAlignment(Align.left);
        titleHostageLabel.setAlignment(Align.left);
        titleVipLabel.setAlignment(Align.left);
        enemyCountLabel.setAlignment(Align.left);
        hostageCountLabel.setAlignment(Align.left);
        vipCountLabel.setAlignment(Align.left);

        playerLivesTable.add(playerLivesImage).pad(0, 10, 0, 5).width(30).height(50);
        playerLivesCombineTable.add(titlePlayerLivesLabel).pad(0, 10, 0, 5).height(25).growX();
        playerLivesCombineTable.row();
        playerLivesCombineTable.add(playerCountLivesLabel).pad(0, 10, 0, 5).height(25).growX();
        playerLivesTable.add(playerLivesCombineTable);

        enemyTable.add(enemyImage).pad(0, 10, 0, 5).width(30).height(50);
        enemyCombineTable.add(titleEnemyLabel).pad(0, 10, 0, 5).height(25).growX();
        enemyCombineTable.row();
        enemyCombineTable.add(enemyCountLabel).pad(0, 10, 0, 5).height(25).growX();
        enemyTable.add(enemyCombineTable);

        hostageTable.add(hostageImage).pad(0, 10, 0, 5).width(30).height(50);
        hostageCombineTable.add(titleHostageLabel).pad(0, 10, 0, 5).height(25).growX();
        hostageCombineTable.row();
        hostageCombineTable.add(hostageCountLabel).pad(0, 10, 0, 5).height(25).growX();
        hostageTable.add(hostageCombineTable);

        vipTable.add(vipImage).pad(0, 10, 0, 5).width(30).height(50);
        vipCombineTable.add(titleVipLabel).pad(0, 10, 0, 5).height(25).growX();
        vipCombineTable.row();
        vipCombineTable.add(vipCountLabel).pad(0, 10, 0, 5).height(25).growX();
        vipTable.add(vipCombineTable);

        gameNavigationTable.add(missionLabel).pad(0, 20, 0, 10).height(50);
        gameNavigationTable.add(playerLivesTable).pad(0, 0, 0, 5).height(50);
        gameNavigationTable.add(enemyTable).pad(0, 0, 0, 5).height(50);
        gameNavigationTable.add(hostageTable).pad(0, 5, 0, 5).height(50);
        gameNavigationTable.add(vipTable).pad(0, 5, 0, 10).height(50);
        gameNavigationTable.add(emptyLabel).height(50).growX();
        gameNavigationTable.add(timerLabel).pad(0, 0, 0, 10).width(140).height(50);

        return gameNavigationTable;
    }

    public void playerLives() {
        GameData.PLAYER_LIVES--;
        playerCountLivesLabel.setText(GameData.PLAYER_LIVES);
    }

    public void enemyKill() {
        GameData.ENEMY_COUNT--;
        enemyCountLabel.setText(GameData.ENEMY_COUNT);
    }

    public void hostageCollect() {
        GameData.HOSTAGE_COUNT--;
        hostageCountLabel.setText(GameData.HOSTAGE_COUNT);
    }

    public void vipCollect() {
        GameData.VIP_COUNT--;
        vipCountLabel.setText(GameData.VIP_COUNT);
    }

    public void updateDurationTimer(String time) {
        timerLabel.setText(Language.get("label_nav_timer") + ": " + time);
    }

    public static void updateMoney() {
        moneyLabel.setText(Language.get("button_money") + ": " + Money.format(GameData.PLAYER_ACCOUNT.getMoney()));
    }
}