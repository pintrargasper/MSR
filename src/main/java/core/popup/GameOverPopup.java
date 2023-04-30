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

public class GameOverPopup extends Table implements Screen {

    private final Table mainTable, innerTable, emptyTable;
    private final Label titleLabel, causeLabel, ammoCostsLabel;
    private final TextButton missionsButton;
    private final GameScreenView gameScreenView;
    private final ScreenChanger screenChanger;

    public GameOverPopup(GameScreenView gameScreenView, Skin skin) {
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.titleLabel = new Label(Language.get("label_mission_failed"), skin.get("medium-title", Label.LabelStyle.class));
        this.causeLabel = new Label("", skin);
        this.ammoCostsLabel = new Label("", skin);
        this.missionsButton = new TextButton(Language.get("button_game_over_missions"), skin);
        this.gameScreenView = gameScreenView;
        this.screenChanger = new ScreenChanger();

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);
        causeLabel.setAlignment(Align.center);
        ammoCostsLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);

        innerTable.add(titleLabel).pad(10, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(causeLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(ammoCostsLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(emptyTable).growY();
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

    public void setPopup(String cause, String ammoCosts) {
        String text;
        switch (cause) {
            case "lives" -> {
                text = Language.get("label_rain_lives");
            }
            case "hostage" -> {
                text = Language.get("label_to_many_hostages");
            }
            case "vip" -> {
                text = Language.get("label_vip_killed");
            }
            default -> {
                text = "";
            }
        }
        causeLabel.setText(text);
        ammoCostsLabel.setText(Language.get("label_game_over_ammo_costs") + ": " + ammoCosts);
    }
}