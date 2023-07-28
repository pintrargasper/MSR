package core.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;
import core.Background;
import core.GameData;
import core.ScreenChanger;
import core.database.AccountConnection;

public class NoConnectionView {

    private final Stage stage;
    private final Skin skin;
    private final Table mainTable, currentTable;
    private final Label titleLabel, noConnectionLabel, reason1Label, reason2Label;
    private final TextButton reloadButton;
    private final ScreenChanger screenChanger;

    public NoConnectionView(Stage stage) {
        this.stage = stage;
        this.skin = new Skin(Gdx.files.internal(GameData.SKIN));
        this.mainTable = new Table();
        this.currentTable = new Table();
        this.titleLabel = new Label("Memo Stick Rescue", skin.get("big-title", Label.LabelStyle.class));
        this.noConnectionLabel = new Label("Could not connect to the server", skin.get("medium-title", Label.LabelStyle.class));
        this.reason1Label = new Label("Reason 1: You don't have an internet connection", skin);
        this.reason2Label = new Label("Reason 2: Problems with the server", skin);
        this.reloadButton = new TextButton("Reload", skin);
        this.screenChanger = new ScreenChanger(GameData.INSTANCE);
    }

    public Table getView() {
        mainTable.setFillParent(true);
        mainTable.setBackground(Background.setBackground(Background.lightGray));

        titleLabel.setAlignment(Align.center);
        noConnectionLabel.setAlignment(Align.center);

        currentTable.add(titleLabel).pad(0, 10, 20, 10).growX();
        currentTable.row();
        currentTable.add(noConnectionLabel).pad(0, 10, 10, 10).growX();
        currentTable.row();
        currentTable.add(reason1Label).pad(0, 42, 10, 10).growX();
        currentTable.row();
        currentTable.add(reason2Label).pad(0, 42, 20, 10).growX();
        currentTable.row();
        currentTable.add(reloadButton).pad(0, 10, 0, 10).width(200).height(50);

        mainTable.add(currentTable).width(400).growY();

        reloadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                reloadButton.setDisabled(true);
                noConnectionLabel.setText("Checking...");

                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        var leaderBoard = AccountConnection.getLeaderBoard();

                        if (leaderBoard != null) {
                            screenChanger.changeScreen(0, leaderBoard);
                        } else {
                            noConnectionLabel.setText("Could not connect to the server");
                        }
                        reloadButton.setDisabled(false);
                    }
                }, 3);
            }
        });

        return mainTable;
    }
}