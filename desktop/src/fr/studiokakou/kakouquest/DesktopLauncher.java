package fr.studiokakou.kakouquest;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import java.io.IOException;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) throws IOException {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(GetProperties.getIntProperty("FRAME_RATE"));
		config.useVsync(true);

		config.setWindowIcon("assets/window/icon.png");

		if (GetProperties.getBoolProperty("FULLSCREEN")){
			config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode());
		}else {
			config.setResizable(false);
			config.setWindowedMode(GetProperties.getIntProperty("RES_WIDTH"), GetProperties.getIntProperty("RES_HEIGHT"));
		}

		config.setTitle("Kakou Quest");
		new Lwjgl3Application(new GameSpace(), config);
	}
}
