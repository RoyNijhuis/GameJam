package nl.royenedwin.gamejam.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.royenedwin.gamejam.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Puzzle Jam!";
		config.width = 1600;
		config.height = 900;
		config.fullscreen = true;
		new LwjglApplication(new Main(), config);
	}
}
