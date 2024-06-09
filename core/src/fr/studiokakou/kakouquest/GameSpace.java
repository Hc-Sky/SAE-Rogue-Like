package fr.studiokakou.kakouquest;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.keybinds.Keybinds;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.*;

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

	public SpriteBatch upgradeBatch;
	/**
	 * Le temps de démarrage de l'application.
	 */
	public long startTime;

	/**
	 * L'écran de démarrage du jeu.
	 */
	public SplashScreen splashScreen;


	public BitmapFont font;

	private Player player;


	/**
	 * Méthode appelée lors de la création de l'application.
	 */
	@Override
	public void create() {
		// Mise à jour des raccourcis clavier
		Keybinds.updateKeys();

		batch = new SpriteBatch();
		hudBatch = new SpriteBatch();
		upgradeBatch = new SpriteBatch();
		startTime = TimeUtils.millis();
		//Le initialize sert juste pour le HelpScreen pour les fonts
		initialize();
		// Définition de l'écran initial du jeu (écran de jeu ou écran de démarrage ou menu)
		this.setScreen(new SplashScreen(this));
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

	public void initialize() {
		font = new BitmapFont();
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	public void removeAvatarChoiceScreen() {
		screen.dispose(); // Libère les ressources de l'écran de sélection d'avatar
		screen = null; // Définit l'écran de sélection d'avatar comme nul
	}
}