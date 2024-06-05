package fr.studiokakou.kakouquest.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.GameSpace;

public class PauseScreen extends ScreenAdapter {

	private static final int RESUME_BUTTON_HEIGHT = 300;
	private static final int RESUME_BUTTON_WIDTH = 600;
	private static final int EXIT_BUTTON_HEIGHT = 100;
	private static final int EXIT_BUTTON_WIDTH = 210;
	private static final int SETTINGS_BUTTON_HEIGHT = 125;
	private static final int SETTINGS_BUTTON_WIDTH = 400;

	private GameSpace game;
	private Texture resumeButton;
	private Texture settingsButton;
	private Texture quitButton;
	private Texture whitePixel;

	public PauseScreen(GameSpace game) {
		this.game = game;
		resumeButton = new Texture("assets/buttons/resume_button.png");
		settingsButton = new Texture("assets/buttons/settings_button.png");
		quitButton = new Texture("assets/buttons/exit_button.png");
//		whitePixel = new Texture("assets/buttons/white_pixel.png");

		// Créez un Pixmap de 1x1 pixel
		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

		// Définissez la couleur du Pixmap à noir avec une transparence de 50%
		pixmap.setColor(0, 0, 0, 0.5f);

		// Dessinez un pixel au point (0,0)
		pixmap.fill();

		// Créez une texture à partir du Pixmap
		whitePixel = new Texture(pixmap);

		// Libérez les ressources du Pixmap
		pixmap.dispose();
	}

	@Override
	public void render(float delta) {


		// Dessinez un écran semi-transparent par-dessus le jeu
		game.batch.begin();
		game.batch.setColor(1, 1, 1, 0.5f);
		game.batch.draw(whitePixel, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Calculez le centre de l'écran
		int centerX = Gdx.graphics.getWidth() / 2;
		int centerY = Gdx.graphics.getHeight() / 2;

		game.batch.draw(resumeButton, centerX - RESUME_BUTTON_WIDTH/2, centerY, RESUME_BUTTON_WIDTH, RESUME_BUTTON_HEIGHT);
		game.batch.draw(settingsButton, centerX - SETTINGS_BUTTON_WIDTH/2, centerY - SETTINGS_BUTTON_HEIGHT -50, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
		game.batch.draw(quitButton, centerX - EXIT_BUTTON_WIDTH/2, centerY - EXIT_BUTTON_HEIGHT - 200, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);


		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
			int mouseX = Gdx.input.getX();
			int mouseY = Gdx.graphics.getHeight() - Gdx.input.getY(); // Inverse l'axe Y

			if (mouseX >= centerX - RESUME_BUTTON_WIDTH / 2 && mouseX <= centerX + RESUME_BUTTON_WIDTH / 2 &&
					  mouseY >= centerY && mouseY <= centerY + RESUME_BUTTON_HEIGHT) {
				game.setScreen(game.previousScreen);
			}
			else if (mouseX >= centerX - SETTINGS_BUTTON_WIDTH / 2 && mouseX <= centerX + SETTINGS_BUTTON_WIDTH / 2 &&
					  mouseY >= centerY - SETTINGS_BUTTON_HEIGHT - 50 && mouseY <= centerY - 50) {
				game.setScreen(new SettingsScreen(game));
			}
			else if (mouseX >= centerX - EXIT_BUTTON_WIDTH / 2 && mouseX <= centerX + EXIT_BUTTON_WIDTH / 2 &&
					  mouseY >= centerY - EXIT_BUTTON_HEIGHT - 200 && mouseY <= centerY - 200) {
				game.setScreen(new MenuScreen(game));
			}
		}

		game.batch.end();
	}
}