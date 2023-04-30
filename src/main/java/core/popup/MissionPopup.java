package core.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import core.*;
import core.objects.Mission;
import core.views.MissionView;

public class MissionPopup extends Table implements Screen {

    private final Table mainTable, innerTable, emptyTable, combineTable;
    private final Label titleLabel, descriptionLabel, lineLabel, previousMissionScoreLabel, bestMissionScoreLabel;
    private final TextButton playButton, closeButton;
    private final MissionView missionView;
    private final ScreenChanger screenChanger;
    private Mission mission;

    public MissionPopup(MissionView missionView, Skin skin) {
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.combineTable = new Table();
        this.titleLabel = new Label("", skin.get("medium-title", Label.LabelStyle.class));
        this.descriptionLabel = new Label("", skin);
        this.lineLabel = new Label("----------------------------", skin);
        this.previousMissionScoreLabel = new Label("", skin);
        this.bestMissionScoreLabel = new Label("", skin);
        this.playButton = new TextButton(Language.get("button_play"), skin);
        this.closeButton = new TextButton(Language.get("button_close"), skin);
        this.missionView = missionView;
        this.screenChanger = new ScreenChanger();

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);
        descriptionLabel.setAlignment(Align.center);
        lineLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);
        descriptionLabel.setWrap(true);

        combineTable.add(playButton).pad(5, 10, 5, 10).height(50).width(200);
        combineTable.add(closeButton).pad(5, 10, 5, 10).height(50).width(200);

        innerTable.add(titleLabel).pad(10, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(descriptionLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(lineLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(previousMissionScoreLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(bestMissionScoreLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(emptyTable).growY();
        innerTable.row();
        innerTable.add(combineTable).growX().growY();

        mainTable.add(innerTable).pad(20);
        this.add(mainTable);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicPlayer.stop();
                MusicPlayer.setMusic(Gdx.audio.newMusic(Gdx.files.internal("music/mission.mp3")));
                if (GameData.SETTINGS.getMusic() == 1) {
                    MusicPlayer.play();
                }
                screenChanger.changeScreen(5, mission);
            }
        });

        closeButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                missionView.closePopup();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int width, int height) {
        setPosition(width / 2f, height / 2f);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void setPopup(Mission mission) {
        this.titleLabel.setText(mission.getName());
        this.descriptionLabel.setText(mission.getDescription());
        this.previousMissionScoreLabel.setText(Language.get("label_previous_score") + ": " + mission.getLastScore());
        this.bestMissionScoreLabel.setText(Language.get("label_best_score") + ": " + mission.getMaxScore());
        this.mission = mission;
    }
}