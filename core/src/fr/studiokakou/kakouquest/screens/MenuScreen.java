package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.GameSpace;

import static com.badlogic.gdx.Gdx.input;

public class MenuScreen implements Screen {
    // Constants for the buttons
    private static final int PLAY_BUTTON_HEIGHT = 200;
    private static final int PLAY_BUTTON_WIDTH = 400;
    private static final int EXIT_BUTTON_HEIGHT = 70;
    private static final int EXIT_BUTTON_WIDTH = 300;
    private static final int SETTINGS_BUTTON_HEIGHT = 150;
    private static final int SETTINGS_BUTTON_WIDTH = 300;
    private static final int HELP_BUTTON_HEIGHT = 120;
    private static final int HELP_BUTTON_WIDTH = 120;

    // Espace de jeu
    GameSpace game;

    // Définition des boutons
    Texture playButton;
    Texture exitButton;
    Texture settingsButton;
    Texture helpButton;

    // Définition des boutons sélectionnés
    Texture playButtonSelected;
    Texture exitButtonSelected;
    Texture settingsButtonSelected;

    // Image de fond
    Texture background;

    public MenuScreen(GameSpace game) {
        this.game = game;
        playButton = new Texture("assets/buttons/play_button.png");
        exitButton = new Texture("assets/buttons/exit_button.png");
        settingsButton = new Texture("assets/buttons/settings_button.png");
        helpButton = new Texture("assets/buttons/help_button.png");
        playButtonSelected = new Texture("assets/buttons/play_button_selected.png");
        exitButtonSelected = new Texture("assets/buttons/exit_button_selected.png");
        settingsButtonSelected = new Texture("assets/buttons/settings_button_selected.png");
        background = new Texture("assets/window/menu_background.png"); // Charger l'image de fond
    }

    @Override
    public void show() {}

    @Override
    public void render(float v) {
        // Echap pour quitter l'écran
        if (input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        // Initialisation de l'écran
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        // Dessiner l'image de fond
        game.batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Définition de l'abscisse pour chaque bouton en fonction de la taille (résolution) de l'écran
        int xposPlay = Gdx.graphics.getWidth() / 2 - PLAY_BUTTON_WIDTH / 2;
        int xposExit = Gdx.graphics.getWidth() / 2 - EXIT_BUTTON_WIDTH / 2;
        int xposSettings = Gdx.graphics.getWidth() / 2 - SETTINGS_BUTTON_WIDTH / 2;
        int xposHelp = Gdx.graphics.getWidth() - HELP_BUTTON_WIDTH - 30;

        // Définition des ordonnées
        int yposPlay = Gdx.graphics.getHeight() / 2 + 40;
        int yposExit = Gdx.graphics.getHeight() / 4 - 20;
        int yposSettings = Gdx.graphics.getHeight() / 2 - SETTINGS_BUTTON_HEIGHT + 10;
        int yposHelp = Gdx.graphics.getHeight() - HELP_BUTTON_HEIGHT - 250;

        // Bouton Play
        if (Gdx.input.getX() < xposPlay + PLAY_BUTTON_WIDTH &&
                Gdx.input.getX() > xposPlay &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposPlay + PLAY_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposPlay) {
            game.batch.draw(playButtonSelected, xposPlay - 34, yposPlay - 45, PLAY_BUTTON_WIDTH + 67, PLAY_BUTTON_HEIGHT + 77);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new GameModeChoice(game));
            }
        } else {
            game.batch.draw(playButton, xposPlay, yposPlay, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        }

        // Bouton Exit
        if (Gdx.input.getX() < xposExit + EXIT_BUTTON_WIDTH &&
                Gdx.input.getX() > xposExit &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposExit + EXIT_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposExit) {
            game.batch.draw(exitButtonSelected, xposExit - 18, yposExit - 28, EXIT_BUTTON_WIDTH + 39, EXIT_BUTTON_HEIGHT + 53);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Gdx.app.exit();
            }
        } else {
            game.batch.draw(exitButton, xposExit, yposExit, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        }

        // Bouton Settings
        if (Gdx.input.getX() < xposSettings + SETTINGS_BUTTON_WIDTH &&
                Gdx.input.getX() > xposSettings &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposSettings + SETTINGS_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposSettings) {
            game.batch.draw(settingsButtonSelected, xposSettings - 18, yposSettings - 22, SETTINGS_BUTTON_WIDTH + 35, SETTINGS_BUTTON_HEIGHT + 44);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new SettingsScreen(game));
            }
        } else {
            game.batch.draw(settingsButton, xposSettings, yposSettings, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        }

        // Bouton Help (Règles du jeu)
        game.batch.draw(helpButton, xposHelp, yposHelp, HELP_BUTTON_WIDTH, HELP_BUTTON_HEIGHT);
        if (Gdx.input.getX() < xposHelp + HELP_BUTTON_WIDTH &&
                Gdx.input.getX() > xposHelp &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposHelp + HELP_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposHelp) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new HelpScreen(game));
            }
        }
        game.batch.end();
    }

    @Override
    public void resize(int i, int i1) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        // Dispose of textures to free up resources
        playButton.dispose();
        exitButton.dispose();
        settingsButton.dispose();
        helpButton.dispose();
        playButtonSelected.dispose();
        exitButtonSelected.dispose();
        settingsButtonSelected.dispose();
        background.dispose();
    }
}
