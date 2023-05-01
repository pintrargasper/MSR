package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;

public class Utils {

    public boolean isButtonOrKeyJustPressed(int code) {
        return code == 0 || code == 1 || code == 2 || code == 3 || code == 4 ? Gdx.input.isButtonJustPressed(code) : Gdx.input.isKeyJustPressed(code);
    }

    public boolean isButtonOrKeyPressed(int code) {
        return code == 0 || code == 1 || code == 2 || code == 3 || code == 4 ? Gdx.input.isButtonPressed(code) : Gdx.input.isKeyPressed(code);
    }

    public void disableAll(Stage stage, boolean active) {
        for (Actor actor : stage.getActors()) {
            actor.setTouchable(active ? Touchable.disabled : Touchable.enabled);
        }
    }
}