package core.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import core.Background;
import core.GameData;
import core.Language;
import core.ScreenChanger;
import core.database.objects.MissionBestScore;
import core.objects.Mission;
import core.views.GameScreenView;

import java.util.Objects;

public class GameFinnishPopup extends Table implements Screen {

    private final Table mainTable, innerTable, emptyTable;
    private final Label titleLabel, earnedMoneyLabel, noHostageKilledLabel, enemyKilledMoneyLabel, ammoCostsLabel, timeLabel, bonusPenaltyLabel, totalEarnedMoneyLabel;
    private final TextButton missionsButton;
    private final GameScreenView gameScreenView;
    private final ScreenChanger screenChanger;
    private final Mission mission;

    public GameFinnishPopup(GameScreenView gameScreenView, Skin skin, Mission mission) {
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.titleLabel = new Label(Language.get("label_mission_complete"), skin.get("medium-title", Label.LabelStyle.class));
        this.earnedMoneyLabel = new Label("", skin);
        this.noHostageKilledLabel = new Label("", skin);
        this.enemyKilledMoneyLabel = new Label("", skin);
        this.ammoCostsLabel = new Label("", skin);
        this.timeLabel = new Label("", skin);
        this.bonusPenaltyLabel = new Label("", skin);
        this.totalEarnedMoneyLabel = new Label("", skin);
        this.missionsButton = new TextButton(Language.get("button_game_finnish_missions"), skin);
        this.gameScreenView = gameScreenView;
        this.screenChanger = new ScreenChanger();
        this.mission = mission;

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);

        innerTable.add(titleLabel).pad(10, 10, 5, 10).height(50).growX();
        innerTable.row();
        innerTable.add(earnedMoneyLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(noHostageKilledLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(enemyKilledMoneyLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(ammoCostsLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(timeLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(bonusPenaltyLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(totalEarnedMoneyLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(emptyTable).pad(0, 10, 5, 10).growY();
        innerTable.row();
        innerTable.add(missionsButton).width(200).height(50);

        mainTable.add(innerTable).pad(20);
        this.add(mainTable);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        missionsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreenView.changeMusic();
                screenChanger.changeScreen(4);
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

    public void setPopup(String earnedMoney, String noHostagesKilled, String enemyKilledMoney, String ammoCosts, String usedTime, int bonusPenalty, String totalEarnedMoney, int money) {
        earnedMoneyLabel.setText(Language.get("label_earned_money") + ": " + earnedMoney);
        noHostageKilledLabel.setText(Language.get("label_no_hostage_killed") + ": " + noHostagesKilled);
        enemyKilledMoneyLabel.setText(Language.get("label_enemy_killed") + ": " + enemyKilledMoney);
        ammoCostsLabel.setText(Language.get("label_ammo_costs") + ": " + ammoCosts);
        timeLabel.setText(Language.get("label_used_time") + ": " + usedTime);
        bonusPenaltyLabel.setText((bonusPenalty >= 0 ? Language.get("label_bonus") : Language.get("label_penalty")) + " : " + Math.abs(bonusPenalty));
        totalEarnedMoneyLabel.setText(Language.get("label_total_earned_money") + ": " + totalEarnedMoney);

        var mission = GameData.MISSIONS_BEST_SCORE.stream().filter(e -> Objects.equals(e.getId(), this.mission.getId())).findFirst().get();
        mission.setScore(Math.max(mission.getScore(), money));
        mission.setUsername(GameData.PLAYER_ACCOUNT.getUsername());
    }

    public void setPopup() {
        titleLabel.setText(Language.get("label_something_wrong"));
        earnedMoneyLabel.setText(Language.get("label_could_not_connect"));
    }
}