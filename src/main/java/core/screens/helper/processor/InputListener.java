package core.screens.helper.processor;

public interface InputListener {
    void onKeyDown(int keycode);
    void onTouchDown(int screenX, int screenY, int pointer, int button);
}