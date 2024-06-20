package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.bdd.GameDatabase;

import static fr.studiokakou.kakouquest.screens.InGameScreen.currentLevel;
import static fr.studiokakou.kakouquest.screens.InGameScreen.deepestLevel;

public class PauseScreen implements Screen {

	private static final int RESUME_BUTTON_HEIGHT = 300;
	private static final int RESUME_BUTTON_WIDTH = 600;
	private static final int RESUME_BUTTON_SELECTED_HEIGHT = 400;
	private static final int RESUME_BUTTON_SELECTED_WIDTH = 800;
	private static final int EXIT_BUTTON_HEIGHT = 70;
	private static final int EXIT_BUTTON_WIDTH = 311;
	private static final int EXIT_BUTTON_SELECTED_HEIGHT = 123;
	private static final int EXIT_BUTTON_SELECTED_WIDTH = 352;
	private static final int SETTINGS_BUTTON_WIDTH = 349;
	private static final int SETTINGS_BUTTON_HEIGHT = 158;
	private static final int SETTINGS_BUTTON_SELECTED_WIDTH = 390;
	private static final int SETTINGS_BUTTON_SELECTED_HEIGHT = 204;

	private GameSpace game;
	private InGameScreen inGameScreen;
	private GameDatabase db;
	private Texture resumeButton;
	private Texture resumeButtonSelected;
	private Texture settingsButton;
	private Texture settingsButtonSelected;
	private Texture exitButton;
	private Texture exitButtonSelected;
	private Texture background;

	public PauseScreen(GameSpace game) {
		this.game = game;
		this.db = new GameDatabase();
		resumeButton = new Texture("assets/buttons/resume_button.png");
		resumeButtonSelected = new Texture("assets/buttons/resume_button_selected.png");
		settingsButton = new Texture("assets/buttons/settings_button.png");
		settingsButtonSelected = new Texture("assets/buttons/settings_button_selected.png");
		exitButton = new Texture("assets/buttons/exit_button.png");
		exitButtonSelected = new Texture("assets/buttons/exit_button_selected.png");
		background = new Texture("assets/window/settings_background.png");
	}

	public PauseScreen(GameSpace game, InGameScreen inGameScreen) {
		this.game = game;
		this.db = new GameDatabase();
		resumeButton = new Texture("assets/buttons/resume_button.png");
		resumeButtonSelected = new Texture("assets/buttons/resume_button_selected.png");
		settingsButton = new Texture("assets/buttons/settings_button.png");
		settingsButtonSelected = new Texture("assets/buttons/settings_button_selected.png");
		exitButton = new Texture("assets/buttons/exit_button.png");
		exitButtonSelected = new Texture("assets/buttons/exit_button_selected.png");
		background = new Texture("assets/window/settings_background.png");
		this.inGameScreen = inGameScreen;
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {

		// Initialisation de l'écran
		Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.hudBatch.begin();

		// Calculer les dimensions de l'image de fond pour qu'elle remplisse l'écran tout en conservant ses proportions
		float aspectRatio = 1129.0f / 959.0f;
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float backgroundWidth = screenWidth;
		float backgroundHeight = screenHeight;

		if (screenWidth / screenHeight > aspectRatio) {
			backgroundWidth = screenHeight * aspectRatio;
		} else {
			backgroundHeight = screenWidth / aspectRatio;
		}

		// Dessiner l'image de fond
		game.hudBatch.draw(background, (screenWidth - backgroundWidth) / 2, (screenHeight - backgroundHeight) / 2, backgroundWidth, backgroundHeight);

		// Bouton Resume
		int xposResume = Gdx.graphics.getWidth() / 2 - RESUME_BUTTON_WIDTH / 2;
		int yposResume = Gdx.graphics.getHeight() / 2 + 50;

		if (Gdx.input.getX() < xposResume + RESUME_BUTTON_WIDTH &&
				Gdx.input.getX() > xposResume &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < yposResume + RESUME_BUTTON_HEIGHT &&
				Gdx.graphics.getHeight() - Gdx.input.getY() > yposResume) {
			game.hudBatch.draw(resumeButtonSelected, xposResume - 40, yposResume - 45, RESUME_BUTTON_SELECTED_WIDTH - 120, RESUME_BUTTON_SELECTED_HEIGHT - 10);
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				game.setScreen(game.previousScreen);
				if (game.previousScreen instanceof InGameScreen) {
					((InGameScreen) game.previousScreen).resumeGame();
				}
			}
		} else {
			game.hudBatch.draw(resumeButton, xposResume, yposResume, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
		}

		// Bouton Settings
		int xposSettings = Gdx.graphics.getWidth() / 2 - SETTINGS_BUTTON_WIDTH / 2;
		int yposSettings = yposResume - SETTINGS_BUTTON_HEIGHT - 50;

		if (Gdx.input.getX() < xposSettings + SETTINGS_BUTTON_WIDTH &&
				Gdx.input.getX() > xposSettings &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < yposSettings + SETTINGS_BUTTON_HEIGHT &&
				Gdx.graphics.getHeight() - Gdx.input.getY() > yposSettings) {
			game.hudBatch.draw(settingsButtonSelected, xposSettings - 21, yposSettings - 23, SETTINGS_BUTTON_SELECTED_WIDTH, SETTINGS_BUTTON_SELECTED_HEIGHT);
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				game.setScreen(new SettingsScreen(game));
			}
		} else {
			game.hudBatch.draw(settingsButton, xposSettings, yposSettings, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
		}

		// Bouton Exit
		int xposExit = Gdx.graphics.getWidth() / 2 - EXIT_BUTTON_WIDTH / 2;
		int yposExit = yposSettings - EXIT_BUTTON_HEIGHT - 200;

		if (Gdx.input.getX() < xposExit + EXIT_BUTTON_WIDTH &&
				Gdx.input.getX() > xposExit &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < yposExit + EXIT_BUTTON_HEIGHT &&
				Gdx.graphics.getHeight() - Gdx.input.getY() > yposExit) {
			game.hudBatch.draw(exitButtonSelected, xposExit - 21, yposExit - 23, EXIT_BUTTON_SELECTED_WIDTH, EXIT_BUTTON_SELECTED_HEIGHT);
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				if (currentLevel > deepestLevel){
					deepestLevel = currentLevel;
				}
				try {
					db.savePlayerStats(inGameScreen.player);
					db.saveWeaponStats(inGameScreen.player);
					db.saveAmeliorationStats(inGameScreen.player);
					db.saveGameStats(inGameScreen.player);
					db.closeConnection();
				} catch (Exception e) {
					e.printStackTrace();
				}
				game.setScreen(new MenuScreen(game));
			}
		} else {
			game.hudBatch.draw(exitButton, xposExit, yposExit, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
		}
		game.hudBatch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void dispose() {

	}
}
