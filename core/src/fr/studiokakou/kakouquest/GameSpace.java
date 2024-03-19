package fr.studiokakou.kakouquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.keybinds.Keybinds;
import fr.studiokakou.kakouquest.screens.SplashScreen;

import java.sql.Time;

/**
 * Le type GameSpace.
 * Cette classe est utilisée pour créer un objet GameSpace.
 *
 * @version 1.0
 */
public class GameSpace extends Game {
	/**
	 * le batch = une collection de sprites.
	 */
	public SpriteBatch batch;
	/**
	 * le batch de l'HUD : une collection de sprites pour l'HUD.
	 */
	public SpriteBatch hudBatch;
	/**
	 * Le temps de démarrage.
	 */
	public long startTime;

	/**
	 * L'écran de démarrage.
	 */
	public SplashScreen splashScreen;

	/**
	 * Constructeur de GameSpace.
	 * Sert à créer un objet GameSpace.
	 *
	 */
	@Override
	public void create () {
		//update key binds
		Keybinds.updateKeys();

		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		startTime = TimeUtils.millis();

		this.setScreen(new SplashScreen(this));

	}

	/**
	 * Render.
	 * Sert à afficher l'écran.
	 *
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Dispose.
	 * Sert à supprimer l'écran.
	 */
	@Override
	public void dispose () {
		batch.dispose();
		hudBatch.dispose();
	}

	/**
	 * Resize.
	 * Sert à redimensionner l'écran.
	 *
	 * @param width
	 * @param height
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
