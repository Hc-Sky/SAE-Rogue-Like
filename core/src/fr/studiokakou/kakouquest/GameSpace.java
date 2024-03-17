package fr.studiokakou.kakouquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.screens.SplashScreen;

import java.sql.Time;

public class GameSpace extends Game {
	public SpriteBatch batch;
	public long startTime;

	public SplashScreen splashScreen;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
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
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
