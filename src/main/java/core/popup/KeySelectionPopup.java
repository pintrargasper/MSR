package core.popup;

import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import core.Background;
import core.Language;
import core.ScreenChanger;
import core.screens.helper.processor.InputListener;
import core.screens.helper.processor.KeyProcessor;
import core.views.MenuView;

public class KeySelectionPopup extends Table implements Screen, InputListener {

    private final Stage stage;
    private final Table mainTable, innerTable, emptyTable, combineTable;
    private final Label titleLabel, reasonLabel, currentKeyLabel;
    private final TextButton closeButton;
    private TextButton textButton;
    private final MenuView menuView;
    private final ScreenChanger screenChanger;
    public final KeyProcessor keyProcessor;
    public final InputMultiplexer inputMultiplexer;
    public boolean isOpen;

    public KeySelectionPopup(MenuView menuView, Stage stage, Skin skin) {
        this.stage = stage;
        this.mainTable = new Table();
        this.innerTable = new Table();
        this.emptyTable = new Table();
        this.combineTable = new Table();
        this.titleLabel = new Label(Language.get("label_select_key"), skin.get("medium-title", Label.LabelStyle.class));
        this.reasonLabel = new Label("", skin);
        this.currentKeyLabel = new Label("", skin);
        this.closeButton = new TextButton(Language.get("button_key_close"), skin);
        this.menuView = menuView;
        this.screenChanger = new ScreenChanger();
        this.keyProcessor = new KeyProcessor(this);
        this.inputMultiplexer = new InputMultiplexer();
        this.isOpen = false;

        stage.setScrollFocus(this);

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        innerTable.setBackground(Background.setBackground(Background.white));

        titleLabel.setAlignment(Align.center);

        titleLabel.setWrap(true);

        innerTable.add(titleLabel).height(50).growX();
        innerTable.row();
        innerTable.add(reasonLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(currentKeyLabel).pad(0, 10, 5, 10).growX();
        innerTable.row();
        innerTable.add(emptyTable).growY();
        innerTable.row();
        innerTable.add(closeButton).width(200).height(50);

        mainTable.add(innerTable).pad(20);
        this.add(mainTable);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuView.closeSettingsOrKeySelectionPopup();
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        if (isOpen) {
            inputMultiplexer.addProcessor(stage);
            inputMultiplexer.addProcessor(keyProcessor);
            Gdx.input.setInputProcessor(inputMultiplexer);
        }
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

    @Override
    public void onKeyDown(int keycode) {
        currentKeyLabel.setText(Language.get("label_current_key") + ": " + Input.Keys.toString(keycode));
        textButton.setText(Input.Keys.toString(keycode));
    }

    @Override
    public void onTouchDown(int screenX, int screenY, int pointer, int button) {
        String buttonName;
        switch (button) {
            case 0 -> {
                buttonName = "Mouse left";
            }
            case 1 -> {
                buttonName = "Mouse right";
            }
            case 2 -> {
                buttonName = "Mouse middle";
            }
            case 3 -> {
                buttonName = "Back button";
            }
            case 4 -> {
                buttonName = "Forward button";
            }
            default -> {
                buttonName = "";
            }
        }
        currentKeyLabel.setText(Language.get("label_current_key") + ": " + buttonName);
        textButton.setText(buttonName);
    }

    public void setPopup(String forWhat, String currentKey, TextButton textButton) {
        this.textButton = textButton;
        reasonLabel.setText(Language.get("label_for") + ": " + forWhat);
        currentKeyLabel.setText(Language.get("label_current_key") + ": " + currentKey);
        isOpen = true;
    }

    public void disableMultipleProcessor() {
        isOpen = false;
        inputMultiplexer.removeProcessor(keyProcessor);
    }
}