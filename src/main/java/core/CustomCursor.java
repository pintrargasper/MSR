package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.Pixmap;

public class CustomCursor {

    public CustomCursor() {}

    public void setSystemCursor(Cursor.SystemCursor systemCursor) {
        Gdx.graphics.setSystemCursor(systemCursor);
    }

    public void setCustomCursor(String cursorType) {
        String picturePath;
        int hotspot = 0;

        if ("cursor".equals(cursorType)) {
            picturePath = String.format("pictures/skins/cursor/%s/cursor-%s-stand.png", GameData.CURRENT_CURSOR_SKIN, GameData.CURRENT_CURSOR_SKIN);
        } else {
            picturePath = String.format("pictures/skins/aim/%s/aim-%s-stand.png", GameData.CURRENT_AIM_SKIN, GameData.CURRENT_AIM_SKIN);
            hotspot = 11;
        }

        Pixmap pixmap = new Pixmap(Gdx.files.internal(picturePath));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pixmap, hotspot, hotspot));
        pixmap.dispose();
    }
}