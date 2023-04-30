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
import core.Language;
import core.ScreenChanger;
import core.views.GameScreenView;

public class GameFinnishPopup extends Table implements Screen {

    private final Table mainTable, innerTable, emptyTable;
    private final Label titleLabel, earnedMoneyLabel, noHostageKilledLabel, enemyKilledMoneyLabel, ammoCostsLabel, timeLabel, totalEarnedMoneyLabel;
    private final TextButton missionsButton;
    private final GameScreenView gameScreenView;
    private final ScreenChanger screenChanger;

    public GameFinnishPopup(GameScreenView gameScreenView, Skin skin) {
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.titleLabel = new Label(Language.get("label_mission_complete"), skin.get("medium-title", Label.LabelStyle.class));
        this.earnedMoneyLabel = new Label("", skin);
        this.noHostageKilledLabel = new Label("", skin);
        this.enemyKilledMoneyLabel = new Label("", skin);
        this.ammoCostsLabel = new Label("", skin);
        this.timeLabel = new Label("", skin);
        this.totalEarnedMoneyLabel = new Label("", skin);
        this.missionsButton = new TextButton(Language.get("button_game_finnish_missions"), skin);
        this.gameScreenView = gameScreenView;
        this.screenChanger = new ScreenChanger();

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);

        innerTable.add(titleLabel).height(50).growX();
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

    public void setPopup(String earnedMoney, String noHostagesKilled, String enemyKilledMoney, String ammoCosts, String usedTime, String totalEarnedMoney) {
        earnedMoneyLabel.setText(Language.get("label_earned_money") + ": " + earnedMoney);
        noHostageKilledLabel.setText(Language.get("label_no_hostage_killed") + ": " + noHostagesKilled);
        enemyKilledMoneyLabel.setText(Language.get("label_enemy_killed") + ": " + enemyKilledMoney);
        ammoCostsLabel.setText(Language.get("label_ammo_costs") + ": " + ammoCosts);
        timeLabel.setText(Language.get("label_used_time") + ": " + usedTime);
        totalEarnedMoneyLabel.setText(Language.get("label_total_earned_money") + ": " + totalEarnedMoney);
    }
}