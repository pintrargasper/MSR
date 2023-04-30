package core.database;

import com.badlogic.gdx.Gdx;

public class Utils {

    public boolean isButtonOrKeyJustPressed(int code) {
        return code == 0 || code == 1 || code == 2 || code == 3 || code == 4 ? Gdx.input.isButtonJustPressed(code) : Gdx.input.isKeyJustPressed(code);
    }

    public boolean isButtonOrKeyPressed(int code) {
        return code == 0 || code == 1 || code == 2 || code == 3 || code == 4 ? Gdx.input.isButtonPressed(code) : Gdx.input.isKeyPressed(code);
    }
}