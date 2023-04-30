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

public class PausePopup extends Table implements Screen {

    private final Table mainTable, innerTable, emptyTable, combineTable;
    private final Label titleLabel;
    private final TextButton continueButton, missionsButton;
    private final GameScreenView gameScreenView;
    private final ScreenChanger screenChanger;

    public PausePopup(GameScreenView gameScreenView, Skin skin) {
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.combineTable = new Table();
        this.titleLabel = new Label(Language.get("label_pause"), skin.get("medium-title", Label.LabelStyle.class));
        this.continueButton = new TextButton(Language.get("button_continue"), skin);
        this.missionsButton = new TextButton(Language.get("button_pause_missions"), skin);
        this.gameScreenView = gameScreenView;
        this.screenChanger = new ScreenChanger();

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);

        combineTable.add(continueButton).pad(5, 10, 5, 10).height(50).width(200);
        combineTable.add(missionsButton).pad(5, 10, 5, 10).height(50).width(200);

        innerTable.add(titleLabel).pad(10, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(emptyTable).growY();
        innerTable.row();
        innerTable.add(combineTable).growX().growY();

        mainTable.add(innerTable).pad(20);
        this.add(mainTable);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        continueButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreenView.closePausePopup();
            }
        });

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

    public void setPopup() {

    }
}