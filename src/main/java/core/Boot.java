package core;

public class Boot extends com.badlogic.gdx.Game {

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
