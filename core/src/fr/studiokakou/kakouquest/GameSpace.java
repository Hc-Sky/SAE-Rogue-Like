package fr.studiokakou.kakouquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.keybinds.Keybinds;
import fr.studiokakou.kakouquest.screens.SplashScreen;

import java.sql.Time;

public class GameSpace extends Game {
	public SpriteBatch batch;
	public SpriteBatch hudBatch;
	public long startTime;

	public SplashScreen splashScreen;
	
	@Override
	public void create () {
		//update key binds
		Keybinds.updateKeys();

		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		startTime = TimeUtils.millis();

		this.setScreen(new SplashScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		hudBatch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
