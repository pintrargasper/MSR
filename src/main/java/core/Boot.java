package core;

import com.badlogic.gdx.Game;

public class Boot extends Game {

	private ScreenChanger screenChanger;

	@Override
	public void create() {
		this.screenChanger = new ScreenChanger(this);
		screenChanger.changeScreen(0);
	}

	@Override
	public void dispose () {

	}
}