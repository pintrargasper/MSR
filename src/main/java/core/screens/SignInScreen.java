package core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import core.GameData;
import core.Language;
import core.MusicPlayer;
import core.database.AccountConnection;
import core.views.SignInView;

public class SignInScreen extends ScreenAdapter {

    private final Stage stage;
    private final SignInView signInView;

    public SignInScreen() {
        this.stage = new Stage(new FitViewport(GameData.GAME_WIDTH, GameData.GAME_HEIGHT));
        this.signInView = new SignInView();

        if (MusicPlayer.getMusic() != null) {
            MusicPlayer.stop();
        }

        var leaderBoard = AccountConnection.getLeaderBoard();

        stage.addActor(signInView.getView(stage, leaderBoard));
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
    }
}