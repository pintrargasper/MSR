package core;

import com.badlogic.gdx.Game;

public class Boot extends Game {
	private ScreenChanger screenChanger;
	private CertificateManager certificateManager;

	@Override
	public void create() {
		this.screenChanger = new ScreenChanger(this);
		this.certificateManager = new CertificateManager();
		certificateManager.set();
		screenChanger.changeScreen(0);
	}

	@Override
	public void dispose () {

	}
}