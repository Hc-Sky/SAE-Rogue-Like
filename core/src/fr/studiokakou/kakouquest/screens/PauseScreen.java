package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.GameSpace;

public class PauseScreen implements Screen {

	private static final int RESUME_BUTTON_HEIGHT = 300;
	private static final int RESUME_BUTTON_WIDTH = 600;
	private static final int RESUME_BUTTON_SELECTED_HEIGHT = 400;
	private static final int RESUME_BUTTON_SELECTED_WIDTH = 800;
	private static final int EXIT_BUTTON_HEIGHT = 100;
	private static final int EXIT_BUTTON_WIDTH = 210;
	private static final int SETTINGS_BUTTON_WIDTH = 349;
	private static final int SETTINGS_BUTTON_HEIGHT = 158;
	private static final int SETTINGS_BUTTON_SELECTED_WIDTH = 390;
	private static final int SETTINGS_BUTTON_SELECTED_HEIGHT = 204;


	private GameSpace game;
	private Texture resumeButton;
	private Texture resumeButtonSelected;
	private Texture settingsButton;
	private Texture settingsButtonSelected;
	private Texture exitButton;
	private Texture exitButtonSelected;

	public PauseScreen(GameSpace game) {
		this.game = game;
		resumeButton = new Texture("assets/buttons/resume_button.png");
		resumeButtonSelected = new Texture("assets/buttons/resume_button_selected.png");
		settingsButton = new Texture("assets/buttons/settings_button.png");
		settingsButtonSelected = new Texture("assets/buttons/settings_button_selected.png");
		exitButton = new Texture("assets/buttons/exit_button.png");
		exitButtonSelected = new Texture("assets/buttons/exit_button_selected.png");
	}

	@Override
	public void show() {

	}

	@Override
	public void render(float delta) {

		// Initialisation de l'écran
		Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 0.5f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.hudBatch.begin();

		// Bouton Resume
		int xposResume = Gdx.graphics.getWidth()/2 - RESUME_BUTTON_WIDTH/2;
		int yposResume = Gdx.graphics.getHeight()/ 2 - RESUME_BUTTON_HEIGHT/8;

		if (Gdx.input.getX() < xposResume + RESUME_BUTTON_WIDTH &&
				Gdx.input.getX() > xposResume &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < yposResume + RESUME_BUTTON_HEIGHT &&
				Gdx.graphics.getHeight() - Gdx.input.getY() > yposResume) {
			game.hudBatch.draw(resumeButtonSelected, xposResume - 40, yposResume - 45, RESUME_BUTTON_SELECTED_WIDTH - 120, RESUME_BUTTON_SELECTED_HEIGHT - 10);
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				this.dispose();
				game.setScreen(new InGameScreen(game));
			}
		} else {
			game.hudBatch.draw(resumeButton, xposResume, yposResume, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
		}

		// Bouton Settings
		int xposSettings = Gdx.graphics.getWidth()/2 - SETTINGS_BUTTON_WIDTH/2;
		int yposSettings = yposResume - SETTINGS_BUTTON_HEIGHT -100;

		if (Gdx.input.getX() < xposSettings + SETTINGS_BUTTON_WIDTH &&
				Gdx.input.getX() > xposSettings &&
				Gdx.graphics.getHeight() - Gdx.input.getY() < yposSettings + SETTINGS_BUTTON_HEIGHT &&
				Gdx.graphics.getHeight() - Gdx.input.getY() > yposSettings) {
			game.hudBatch.draw(settingsButtonSelected, xposSettings - 21, yposSettings - 23, SETTINGS_BUTTON_SELECTED_WIDTH , SETTINGS_BUTTON_SELECTED_HEIGHT);
			if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
				this.dispose();
				game.setScreen(new SettingsScreen(game));
			}
		} else {
			game.hudBatch.draw(settingsButton, xposSettings, yposSettings, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
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
