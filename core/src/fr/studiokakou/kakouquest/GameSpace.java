package fr.studiokakou.kakouquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.keybinds.Keybinds;
import fr.studiokakou.kakouquest.screens.SplashScreen;

/**
 * La classe GameSpace représente l'application principale du jeu.
 */
public class GameSpace extends Game {
	/**
	 * Le SpriteBatch principal pour dessiner les éléments du jeu.
	 */
	public SpriteBatch batch;
	/**
	 * Le SpriteBatch pour dessiner les éléments de l'HUD (interface utilisateur).
	 */
	public SpriteBatch hudBatch;
	/**
	 * Le temps de démarrage de l'application.
	 */
	public long startTime;

	/**
	 * L'écran de démarrage du jeu.
	 */
	public SplashScreen splashScreen;

	/**
	 * Méthode appelée lors de la création de l'application.
	 */
	@Override
	public void create() {
		// Mise à jour des raccourcis clavier
		Keybinds.updateKeys();

		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		startTime = TimeUtils.millis();

		// Définition de l'écran initial du jeu (écran de jeu ou écran de démarrage)
		this.setScreen(new InGameScreen(this));
	}

	/**
	 * Méthode appelée à chaque frame pour afficher le jeu.
	 */
	@Override
	public void render() {
		super.render();
	}

	/**
	 * Méthode appelée lors de la fermeture de l'application pour libérer les ressources.
	 */
	@Override
	public void dispose() {
		batch.dispose();
		hudBatch.dispose();
	}

	/**
	 * Méthode appelée lors du redimensionnement de la fenêtre de l'application.
	 *
	 * @param width  Nouvelle largeur de la fenêtre
	 * @param height Nouvelle hauteur de la fenêtre
	 */
	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}
}
