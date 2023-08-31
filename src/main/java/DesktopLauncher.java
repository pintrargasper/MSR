import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import core.Boot;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setForegroundFPS(60);
		configuration.setTitle("Memo Stick Rescue");
		configuration.setWindowIcon("pictures/icon/logo512.png");
		configuration.setMaximized(true);
		configuration.setWindowSizeLimits(1590, 600, 9999, 9999);

		//System.setProperty("javax.net.ssl.trustStore", "C:/Users/gaspe rpintar/OneDrive/Namizje/Projekt-Igra/MemoStickRescue/src/main/resources/certificate/cacerts");

		new Lwjgl3Application(new Boot(), configuration);
	}
}