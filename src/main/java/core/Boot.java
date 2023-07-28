package core;

import com.badlogic.gdx.Game;
import core.database.AccountConnection;

public class Boot extends Game {

	private ScreenChanger screenChanger;

	@Override
	public void create() {
		this.screenChanger = new ScreenChanger(this);

		var leaderBoard = AccountConnection.getLeaderBoard();

		if (leaderBoard == null) {
			screenChanger.changeScreen(6);
		} else {
			screenChanger.changeScreen(0, leaderBoard);
		}
	}

	@Override
	public void dispose () {

	}
}
