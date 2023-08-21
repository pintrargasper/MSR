package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import core.*;
import core.database.AccountConnection;
import core.database.MissionConnection;
import core.database.SettingsConnection;
import core.database.SkinConnection;
import core.database.objects.MissionBestScore;
import core.objects.Account;
import core.objects.LeaderBoard;
import core.web.PicturesDownloader;

import java.util.ArrayList;

public class SignInView {

    private final Skin skin;
    private final Table mainTable, formTable, panelTable, lowerTable, scrollPaneTable;
    private final ScrollPane scrollPane;
    private final Label leaderBoardLabel ,errorLabel, titleLabel, usernameLabel, passwordLabel;
    private final TextField usernameField, passwordField;
    private final TextButton signInButton, emptyButton, exitButton;
    private final NavigationBar navigationBar;
    private final ScreenChanger screenChanger;
    private final PicturesDownloader picturesDownloader;

    public SignInView() {
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.mainTable = new Table();
        this.formTable = new Table();
        this.panelTable = new Table();
        this.lowerTable = new Table();
        this.scrollPaneTable = new Table();
        this.scrollPane = new ScrollPane(scrollPaneTable);
        this.leaderBoardLabel = new Label("Leaderboard", skin.get("medium-title", Label.LabelStyle.class));
        this.errorLabel = new Label("", skin);
        this.titleLabel = new Label("Sign in", skin.get("big-title", Label.LabelStyle.class));
        this.usernameLabel = new Label("Username", skin);
        this.passwordLabel = new Label("Password", skin);
        this.usernameField = new TextField("", skin);
        this.passwordField = new TextField("", skin);
        this.signInButton = new TextButton("Sign in", skin);
        this.emptyButton = new TextButton("", skin);
        this.exitButton = new TextButton("Exit", skin);
        this.navigationBar = new NavigationBar(true);
        this.screenChanger = new ScreenChanger();
        this.picturesDownloader = new PicturesDownloader();
    }

    public Table getView(Stage stage, ArrayList<LeaderBoard> leaderBoard) {
        mainTable.setFillParent(true);
        mainTable.setBackground(Background.setBackground(Background.lightGray));

        stage.setScrollFocus(scrollPaneTable);

        try {
            int index = 0;
            for (LeaderBoard user : leaderBoard) {
                if (index++ % 3 == 0) {
                    scrollPaneTable.row();
                }
                scrollPaneTable.add(getCard(index, user.getUsername(), user.getRank(), user.getCompleted(), user.getPicture())).pad(0, 10, 10, 10).width(330).height(100);
            }
        } catch (Exception e) {
            leaderBoardLabel.setText("Something Went Wrong");
        }

        mainTable.setFillParent(true);
        mainTable.setBackground(Background.setBackground(Background.lightGray));
        formTable.setBackground(Background.setBackground(Background.white));

        leaderBoardLabel.setAlignment(Align.center);
        errorLabel.setAlignment(Align.center);
        titleLabel.setAlignment(Align.center);

        usernameField.setAlignment(Align.center);
        usernameField.setMessageText("Enter Username");

        passwordField.setPasswordMode(true);
        passwordField.setPasswordCharacter('•');
        passwordField.setAlignment(Align.center);
        passwordField.setMessageText("Enter Password");

        formTable.add(titleLabel).growX().growY();
        formTable.row();
        formTable.add(errorLabel).pad(2, 10, 2, 10).growX();
        formTable.row();
        formTable.add(usernameLabel).pad(0, 10, 1, 10).growX();
        formTable.row();
        formTable.add(usernameField).pad(0, 10, 10, 10).height(50).growX();
        formTable.row();
        formTable.add(passwordLabel).pad(0, 10, 1, 10).growX();
        formTable.row();
        formTable.add(passwordField).pad(0, 10, 10, 10).height(50).growX();
        formTable.row();
        formTable.add(signInButton).pad(0, 10, 10, 10).height(50).growX();

        panelTable.add(leaderBoardLabel).growX();
        panelTable.row();
        panelTable.add(scrollPane).growX();
        panelTable.add(formTable).width(590).pad(0, 20, 0, 50).growX();
        lowerTable.add(panelTable).height(400).pad(0, 20, 0, 50).growX();

        mainTable.add(navigationBar.signInNavigationBar()).growX();
        mainTable.row();
        mainTable.add(lowerTable).growX().growY();

        usernameField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return false;
            }
        });

        passwordField.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return false;
            }
        });

        signInButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {

                errorLabel.setText("Loading...");

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        try {
                            Account account = new Account();
                            account.setId(Long.parseLong(usernameField.getText()));
                            GameData.PLAYER_ACCOUNT = account;

                            account = AccountConnection.getAccountDetails();
                            GameData.PLAYER_ACCOUNT = account;

                            GameData.MISSIONS_LIST = MissionConnection.getMissions();
                            GameData.MISSIONS_BEST_SCORE = MissionConnection.getBestScorePerMission();
                            GameData.SETTINGS = SettingsConnection.getSettings();
                            GameData.SKINS_LIST = SkinConnection.getAvailableSkins();
                            GameData.OWNED_SKINS_LIST = SkinConnection.getOwnedSkins();

                            String[] skins = SkinConnection.getActiveSkins().split(",");
                            GameData.CURRENT_PLAYER_SKIN = skins[0];
                            GameData.CURRENT_BULLET_SKIN = skins[1];
                            GameData.CURRENT_CURSOR_SKIN = skins[2];
                            GameData.CURRENT_AIM_SKIN = skins[3];
                            GameData.CURRENT_WEAPON_SKIN = skins[4];

                            var weapon = GameData.OWNED_SKINS_LIST.stream().filter(e -> e.getPicture().contains("weapon-" + GameData.CURRENT_WEAPON_SKIN)).findFirst().get();
                            GameData.WEAPON_SPEED = weapon.getSpeed();

                            MusicPlayer.setMusic(GameData.BASIC_MUSIC);
                            if (GameData.SETTINGS.getMusic() == 1) {
                                MusicPlayer.play();
                            }

                            Language.setLanguage(GameData.SETTINGS.getLanguage());
                            screenChanger.changeScreen(1);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                            e.printStackTrace();
                            errorLabel.setText("Could not connect to the server");
                        }
                    }
                }, 3);
            }
        });

        return mainTable;
    }

    private Table getCard(int index, String username, int rank, float completed, String picture) {
        Table cardTable = new Table();
        Table middleTable = new Table();
        Label positionLabel = new Label(String.valueOf(index), skin);
        Label usernameLabel = new Label(username, skin);
        Label rankLabel = new Label("Rank: " + rank, skin);
        Label completedLabel = new Label(completed + "%", skin);

        completedLabel.setAlignment(Align.right);

        Image image;

        try {
            image = new Image(new Texture(picturesDownloader.download(picture, index)));
        } catch (Exception e) {
            image = new Image(new Texture(new Pixmap(1, 1, Pixmap.Format.RGB565)));
        }

        cardTable.setBackground(Background.setBackground(Background.white));

        middleTable.add(usernameLabel).pad(0, 10, 5, 10).growX();
        middleTable.row();
        middleTable.add(rankLabel).pad(0, 10, 0, 10).growX();

        cardTable.add(positionLabel).pad(0, 10, 0, 10);
        cardTable.add(image).pad(10, 2, 10, 2).width(50).height(50);
        cardTable.add(middleTable).pad(0, 10, 0, 10).growX();
        cardTable.add(completedLabel).pad(0, 10, 0, 10).growX();

        return cardTable;
    }
}