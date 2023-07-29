package core.popup;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import core.*;
import core.database.AccountConnection;
import core.database.SettingsConnection;
import core.database.objects.Settings;
import core.views.MenuView;

import java.util.ArrayList;

public class SettingsPopup extends Table implements Screen {

    private final Stage stage;
    private final Skin skin;
    private final Table mainTable, formTable;
    private final ScrollPane scrollPane;
    private final Label accountLabel, viewAccountLabel, signOutLabel, gameOptionsLabel, languageLabel, musicLabel, soundEffectLabel, keyboardControlsLabel, upLabel, leftLabel, rightLabel, shootLabel, pauseLabel, errorLabel;
    private final TextButton viewAccountButton, signOutButton, musicButton, soundEffectButton, upButton, leftButton, rightButton, shootButton, pauseButton, saveButton, closeButton;
    private TextButton[] textButtons;
    private final SelectBox languageSelectBox;
    private final KeySelectionPopup keySelectionPopup;
    private final Settings settings;
    private final ScreenChanger screenChanger;
    private final Utils utils;

    public SettingsPopup(KeySelectionPopup keySelectionPopup, BasicPopup basicPopup, Stage stage, Utils utils, ScreenChanger screenChanger) {
        this.stage = stage;
        this.keySelectionPopup = keySelectionPopup;
        this.screenChanger = screenChanger;
        this.utils = new Utils();
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.mainTable = new Table();
        this.formTable = new Table();
        this.scrollPane = new ScrollPane(formTable, skin);
        this.settings = GameData.SETTINGS;
        this.accountLabel = new Label(Language.get("label_account"), skin.get("medium-title", Label.LabelStyle.class));
        this.viewAccountLabel = new Label(Language.get("label_view_account"), skin);
        this.signOutLabel = new Label(Language.get("label_sign_out"), skin);
        this.gameOptionsLabel = new Label(Language.get("label_game_options"), skin.get("medium-title", Label.LabelStyle.class));
        this.languageLabel = new Label(Language.get("label_language"), skin);
        this.musicLabel = new Label(Language.get("label_music"), skin);
        this.soundEffectLabel = new Label(Language.get("label_sound_effect"), skin);
        this.keyboardControlsLabel = new Label(Language.get("label_keyboard_controls"), skin.get("medium-title", Label.LabelStyle.class));
        this.upLabel = new Label(Language.get("label_up"), skin);
        this.leftLabel = new Label(Language.get("label_left"), skin);
        this.rightLabel = new Label(Language.get("label_right"), skin);
        this.shootLabel = new Label(Language.get("label_shoot"), skin);
        this.pauseLabel = new Label(Language.get("label_settings_pause"), skin);
        this.errorLabel = new Label("", skin);
        this.viewAccountButton = new TextButton(Language.get("button_view"), skin);
        this.signOutButton = new TextButton(Language.get("button_sign_out"), skin);
        this.musicButton = new TextButton(codeToStringForMusic(settings.getMusic()), skin);
        this.soundEffectButton = new TextButton(codeToStringForMusic(settings.getSoundEffects()), skin);
        this.upButton = new TextButton(codeToString(settings.getKeyUpCode()), skin);
        this.leftButton = new TextButton(codeToString(settings.getKeyLeftCode()), skin);
        this.rightButton = new TextButton(codeToString(settings.getKeyRightCode()), skin);
        this.shootButton = new TextButton(codeToString(settings.getKeyShootCode()), skin);
        this.pauseButton = new TextButton(codeToString(settings.getKeyPauseCode()), skin);
        this.saveButton = new TextButton(Language.get("button_settings_save"), skin);
        this.closeButton = new TextButton(Language.get("button_settings_close"), skin);
        this.languageSelectBox = new SelectBox<>(skin);

        errorLabel.setAlignment(Align.center);
        languageSelectBox.setAlignment(Align.center);

        mainTable.setBackground(Background.setBackground(Background.strongRed));
        formTable.setBackground(Background.setBackground(Background.white));

        languageSelectBox.setItems(GameData.LANGUAGES);
        languageSelectBox.setSelected(settings.getLanguage());

        formTable.add(accountLabel).pad(10, 10, 5, 10).height(30).left();
        formTable.row();
        formTable.add(viewAccountLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(viewAccountButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(signOutLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(signOutButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(gameOptionsLabel).pad(10, 10, 5, 10).height(30).left();
        formTable.row();
        formTable.add(languageLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(languageSelectBox).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(musicLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(musicButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(soundEffectLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(soundEffectButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(keyboardControlsLabel).pad(10, 10, 5, 10).height(30).left();
        formTable.row();
        formTable.add(upLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(upButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(leftLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(leftButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(rightLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(rightButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(shootLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(shootButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(pauseLabel).pad(5, 30, 5, 10).width(600).height(30);
        formTable.add(pauseButton).pad(5, 10, 5, 10).width(200).height(30);
        formTable.row();
        formTable.add(saveButton).pad(30, 10, 5, 10).width(200).height(50).right();
        formTable.add(closeButton).pad(30, 10, 5, 10).width(200).height(50);

        mainTable.add(formTable).pad(20);
        this.add(mainTable);

        this.setPosition((Gdx.graphics.getWidth() - getWidth()) / 2, (Gdx.graphics.getHeight() - getHeight()) / 2);

        viewAccountButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI("https://memostickrescue.eu.org/" + GameData.PLAYER_ACCOUNT.getUsername());
            }
        });

        signOutButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screenChanger.changeScreen(0);
            }
        });

        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (musicButton.getText().toString().matches(Language.get("button_turn_on"))) {
                    musicButton.setText(Language.get("button_turn_off"));
                } else {
                    musicButton.setText(Language.get("button_turn_on"));
                }
            }
        });

        soundEffectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (soundEffectButton.getText().toString().matches(Language.get("button_turn_on"))) {
                    soundEffectButton.setText(Language.get("button_turn_off"));
                } else {
                    soundEffectButton.setText(Language.get("button_turn_on"));
                }
            }
        });

        upButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keySelectionPopup.setPopup(upLabel.getText().toString(), upButton.getText().toString(), upButton);
                utils.disableAll(stage, true);
                stage.addActor(keySelectionPopup);
            }
        });

        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keySelectionPopup.setPopup(leftLabel.getText().toString(), leftButton.getText().toString(), leftButton);
                utils.disableAll(stage, true);
                stage.addActor(keySelectionPopup);
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keySelectionPopup.setPopup(rightLabel.getText().toString(), rightButton.getText().toString(), rightButton);
                utils.disableAll(stage, true);
                stage.addActor(keySelectionPopup);
            }
        });

        shootButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keySelectionPopup.setPopup(shootLabel.getText().toString(), shootButton.getText().toString(), shootButton);
                utils.disableAll(stage, true);
                stage.addActor(keySelectionPopup);
            }
        });

        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                keySelectionPopup.setPopup(pauseLabel.getText().toString(), pauseButton.getText().toString(), pauseButton);
                utils.disableAll(stage, true);
                stage.addActor(keySelectionPopup);
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ArrayList<TextButton> buttons = new ArrayList<>();
                buttons.add(upButton);
                buttons.add(leftButton);
                buttons.add(rightButton);
                buttons.add(shootButton);
                buttons.add(pauseButton);

                int[] codes = new int[buttons.size()];

                for (TextButton button : buttons) {
                    int keyCode = getKeyCode(button.getText().toString());
                    if (keyCode == -1) {
                        keyCode = Input.Keys.valueOf(button.getText().toString());
                    }
                    codes[buttons.indexOf(button)] = keyCode;
                }

                int music = 0;
                if (musicButton.getText().toString().matches(Language.get("button_turn_off"))) {
                    music = 1;
                }

                int soundEffect = 0;
                if (soundEffectButton.getText().toString().matches(Language.get("button_turn_off"))) {
                    soundEffect = 1;
                }

                String error;
                String language = languageSelectBox.getSelected().toString();
                if (SettingsConnection.updateSettings(music,soundEffect, language, codes[0], codes[1], codes[2], codes[3], codes[4]).matches("1")) {
                    settings.setMusic(music);
                    settings.setSoundEffects(soundEffect);
                    settings.setKeyUpCode(codes[0]);
                    settings.setKeyLeftCode(codes[1]);
                    settings.setKeyRightCode(codes[2]);
                    settings.setKeyShootCode(codes[3]);
                    settings.setKeyPauseCode(codes[4]);
                    settings.setLanguage(language);

                    Language.setLanguage(language);

                    if (MusicPlayer.isPlaying() && music == 0) {
                        MusicPlayer.pause();
                    } else if (!MusicPlayer.isPlaying() && music == 1) {
                        MusicPlayer.play();
                    }
                    error = Language.get("string_settings_saved");
                } else {
                    error = Language.get("string_settings_something_wrong");
                }

                utils.disableAll(stage, true);
                basicPopup.setPopup(error);
                stage.addActor(basicPopup);
            }
        });

        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                var actors = stage.getActors();
                utils.disableAll(stage, false);
                setButtons(false, textButtons);
                actors.get(actors.size - 1).remove();
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

    public void setPopup(TextButton... textButtons) {
        this.textButtons = textButtons;
        setButtons(true, textButtons);
    }

    private void setButtons(boolean active, TextButton... textButtons) {
        for (TextButton textButton : textButtons) {
            textButton.setTouchable(active ? Touchable.disabled : Touchable.enabled);
        }
    }

    private int getKeyCode(String buttonName) {
        switch (buttonName) {
            case "Mouse left" -> {
                return 0;
            }
            case "Mouse right" -> {
                return 1;
            }
            case "Mouse middle" -> {
                return 2;
            }
            case "Back button" -> {
                return 3;
            }
            case "Forward button" -> {
                return 4;
            }
            default -> {
                return -1;
            }
        }
    }

    private String codeToString(int code) {
        switch (code) {
            case 0 -> {
                return "Mouse left";
            }
            case 1 -> {
                return "Mouse right";
            }
            case 2 -> {
                return "Mouse middle";
            }
            case 3 -> {
                return "Back button";
            }
            case 4 -> {
                return "Forward button";
            }
            default -> {
                return Input.Keys.toString(code);
            }
        }
    }

    private String codeToStringForMusic(int code) {
        return code == 0 ? Language.get("button_turn_on") : Language.get("button_turn_off");
    }
}