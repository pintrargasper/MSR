package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import core.GameData;
import core.views.MissionView;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class MissionScreen extends ScreenAdapter {

    private final Stage stage;
    private final MissionView missionView;

    public MissionScreen() {
        this.stage = new Stage(new FitViewport(GameData.GAME_WIDTH, GameData.GAME_HEIGHT));
        this.missionView = new MissionView(stage);

        var missionsList = GameData.MISSIONS_LIST;

        var completed = missionsList.stream().filter(e -> e.getCompleted() == 1).collect(Collectors.toCollection(ArrayList::new));
        var uncompleted = missionsList.stream().filter(e -> e.getCompleted() == 2).collect(Collectors.toCollection(ArrayList::new));
        var locked = missionsList.stream().filter(e -> e.getCompleted() == 0).collect(Collectors.toCollection(ArrayList::new));

        stage.addActor(missionView.getView(stage, missionsList, completed, uncompleted, locked));
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.setViewport(new FitViewport(width, height));
        stage.getViewport().update(width, height, true);
        missionView.resize(width, height);
    }
}