package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import core.Background;
import core.GameData;
import core.Language;
import core.ScreenChanger;
import core.database.objects.MissionBestScore;
import core.popup.BasicPopup;
import core.popup.KeySelectionPopup;
import core.popup.SettingsPopup;

import java.util.ArrayList;

public class MenuView {

    private final Stage stage;
    private final Skin skin;
    private final Table mainTable, formTable, combineTable, scrollPaneTable;
    private final ScrollPane scrollPane;
    private final Label bestMissionScoreLabel;
    private final TextButton missionsButton, settingsButton, exitButton;
    private final NavigationBar navigationBar;
    private final ScreenChanger screenChanger;
    private final KeySelectionPopup keySelectionPopup;
    private final BasicPopup basicPopup;
    private final SettingsPopup settingsPopup;

    public MenuView(Stage stage) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.mainTable = new Table();
        this.formTable = new Table();
        this.combineTable = new Table();
        this.scrollPaneTable = new Table();
        this.scrollPane = new ScrollPane(scrollPaneTable, skin);
        this.bestMissionScoreLabel = new Label(Language.get("label_best_missions_score"), skin.get("medium-title", Label.LabelStyle.class));
        this.missionsButton = new TextButton(Language.get("button_missions"), skin);
        this.settingsButton = new TextButton(Language.get("button_settings"), skin);
        this.exitButton = new TextButton(Language.get("button_exit"), skin);
        this.navigationBar = new NavigationBar();
        this.screenChanger = new ScreenChanger();
        this.keySelectionPopup = new KeySelectionPopup(this, stage, skin);
        this.basicPopup = new BasicPopup(stage, skin);
        this.settingsPopup = new SettingsPopup(this, keySelectionPopup, basicPopup, stage, screenChanger);
    }

    public Table getView(Stage stage, ArrayList<MissionBestScore> missionBestScores) {
        mainTable.setFillParent(true);
        mainTable.setBackground(Background.setBackground(Background.lightGray));

        stage.setScrollFocus(scrollPane);

        bestMissionScoreLabel.setAlignment(Align.center);

        formTable.add(missionsButton).padBottom(10).width(400).height(50);
        formTable.row();
        formTable.add(settingsButton).padBottom(10).width(400).height(50);
        formTable.row();
        formTable.add(exitButton).padBottom(10).width(400).height(50);

        int index = 0;
        for (MissionBestScore mission : missionBestScores) {
            if (index++ % 3 == 0) {
                scrollPaneTable.row();
            }
            scrollPaneTable.add(getCard(index, mission.getMissionName(), mission.getMissionPicture(), mission.getUsername(), mission.getScore())).pad(0, 10, 10, 10).width(330).height(100);
        }

        combineTable.add(bestMissionScoreLabel).pad(10).growX();
        combineTable.row();
        combineTable.add(scrollPane).maxWidth(1200).maxHeight(400);
        combineTable.add(formTable).width(400).growX();

        mainTable.add(navigationBar.menuNavigationBar()).growX();
        mainTable.row();
        mainTable.add(combineTable).growX().growY();

        missionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                screenChanger.changeScreen(4);
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stage.addActor(settingsPopup);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        return mainTable;
    }

    private Table getCard(int index, String missionName, String missionPicture, String username, int score) {
        Table cardTable = new Table();
        Table middleTable = new Table();
        Label positionLabel = new Label(String.valueOf(index), skin);
        Label missionNameLabel = new Label(missionName, skin);
        Label scoreLabel = new Label(Language.get("label_score") + ": " + score, skin);
        Label usernameLabel = new Label(Language.get("label_by_player") + ": " + username, skin);

        Image missionImage;
        try {
            missionImage = new Image(new Texture("pictures/missions/" + missionPicture));
        } catch (Exception e) {
            missionImage = new Image(new Texture(new Pixmap(1, 1, Pixmap.Format.RGB565)));
        }

        cardTable.setBackground(Background.setBackground(Background.white));

        middleTable.add(missionNameLabel).pad(0, 10, 5, 10).growX();
        middleTable.row();
        middleTable.add(scoreLabel).pad(0, 10, 0, 10).growX();
        middleTable.row();
        middleTable.add(usernameLabel).pad(0, 10, 0, 10).growX();

        cardTable.add(positionLabel).pad(0, 10, 0, 10);
        cardTable.add(missionImage).pad(10, 2, 10, 2).width(50).height(50);
        cardTable.add(middleTable).pad(0, 10, 0, 10).growX();

        return cardTable;
    }

    public void resize(int width, int height) {
        settingsPopup.resize(width, height);
        keySelectionPopup.resize(width, height);
        basicPopup.resize(width, height);
    }

    public void closeSettingsOrKeySelectionPopup() {
        var actors = stage.getActors();
        actors.get(actors.size - 1).remove();
        keySelectionPopup.disableMultipleProcessor();
        Gdx.input.setInputProcessor(stage);
    }

    public KeySelectionPopup getKeySelectionPopup() {
        return keySelectionPopup;
    }
}