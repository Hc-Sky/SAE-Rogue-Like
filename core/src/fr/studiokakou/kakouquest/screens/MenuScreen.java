package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.player.Player;

import static com.badlogic.gdx.Gdx.input;

public class MenuScreen implements Screen {
    // Dimensions des boutons
    private static final int PLAY_BUTTON_WIDTH = 453;
    private static final int PLAY_BUTTON_HEIGHT = 227;
    private static final int PLAY_BUTTON_SELECTED_WIDTH = 528;
    private static final int PLAY_BUTTON_SELECTED_HEIGHT = 314;

    private static final int SETTINGS_BUTTON_WIDTH = 349;
    private static final int SETTINGS_BUTTON_HEIGHT = 158;
    private static final int SETTINGS_BUTTON_SELECTED_WIDTH = 390;
    private static final int SETTINGS_BUTTON_SELECTED_HEIGHT = 204;

    private static final int EXIT_BUTTON_WIDTH = 311;
    private static final int EXIT_BUTTON_HEIGHT = 70;
    private static final int EXIT_BUTTON_SELECTED_WIDTH = 352;
    private static final int EXIT_BUTTON_SELECTED_HEIGHT = 123;

    private static final int HELP_BUTTON_WIDTH = 120;
    private static final int HELP_BUTTON_HEIGHT = 120;

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
        game.hudBatch.begin();

        // Dessiner l'image de fond
        game.hudBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Calculer les dimensions et positions des boutons
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Calcul des ratios pour les tailles des boutons
        float playButtonWidth = screenWidth * (PLAY_BUTTON_WIDTH / 1920f);
        float playButtonHeight = screenHeight * (PLAY_BUTTON_HEIGHT / 1080f);
        float playButtonSelectedWidth = screenWidth * (PLAY_BUTTON_SELECTED_WIDTH / 1920f);
        float playButtonSelectedHeight = screenHeight * (PLAY_BUTTON_SELECTED_HEIGHT / 1080f);

        float settingsButtonWidth = screenWidth * (SETTINGS_BUTTON_WIDTH / 1920f);
        float settingsButtonHeight = screenHeight * (SETTINGS_BUTTON_HEIGHT / 1080f);
        float settingsButtonSelectedWidth = screenWidth * (SETTINGS_BUTTON_SELECTED_WIDTH / 1920f);
        float settingsButtonSelectedHeight = screenHeight * (SETTINGS_BUTTON_SELECTED_HEIGHT / 1080f);

        float exitButtonWidth = screenWidth * (EXIT_BUTTON_WIDTH / 1920f);
        float exitButtonHeight = screenHeight * (EXIT_BUTTON_HEIGHT / 1080f);
        float exitButtonSelectedWidth = screenWidth * (EXIT_BUTTON_SELECTED_WIDTH / 1920f);
        float exitButtonSelectedHeight = screenHeight * (EXIT_BUTTON_SELECTED_HEIGHT / 1080f);

        float helpButtonWidth = screenWidth * (HELP_BUTTON_WIDTH / 1920f);
        float helpButtonHeight = screenHeight * (HELP_BUTTON_HEIGHT / 1080f);

        float xposPlay = (screenWidth - playButtonWidth) / 2;
        float xposExit = (screenWidth - exitButtonWidth) / 2;
        float xposSettings = (screenWidth - settingsButtonWidth) / 2;
        float xposHelp = screenWidth - helpButtonWidth - 30;

        float yposPlay = screenHeight / 2 + 40;
        float yposExit = screenHeight / 4 - 20;
        float yposSettings = screenHeight / 2 - settingsButtonHeight + 10;
        float yposHelp = screenHeight - helpButtonHeight - 250;

        // Bouton Play
        if (Gdx.input.getX() < xposPlay + playButtonWidth &&
                Gdx.input.getX() > xposPlay &&
                screenHeight - Gdx.input.getY() < yposPlay + playButtonHeight &&
                screenHeight - Gdx.input.getY() > yposPlay) {
            game.hudBatch.draw(playButtonSelected, xposPlay - (playButtonSelectedWidth - playButtonWidth) / 2, yposPlay - (playButtonSelectedHeight - playButtonHeight) / 2, playButtonSelectedWidth, playButtonSelectedHeight);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new UsernameSreen(game));
            }
        } else {
            game.hudBatch.draw(playButton, xposPlay, yposPlay, playButtonWidth, playButtonHeight);
        }

        // Bouton Exit
        if (Gdx.input.getX() < xposExit + exitButtonWidth &&
                Gdx.input.getX() > xposExit &&
                screenHeight - Gdx.input.getY() < yposExit + exitButtonHeight &&
                screenHeight - Gdx.input.getY() > yposExit) {
            game.hudBatch.draw(exitButtonSelected, xposExit - (exitButtonSelectedWidth - exitButtonWidth) / 2, yposExit - (exitButtonSelectedHeight - exitButtonHeight) / 2, exitButtonSelectedWidth, exitButtonSelectedHeight);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Gdx.app.exit();
            }
        } else {
            game.hudBatch.draw(exitButton, xposExit, yposExit, exitButtonWidth, exitButtonHeight);
        }

        // Bouton Settings
        if (Gdx.input.getX() < xposSettings + settingsButtonWidth &&
                Gdx.input.getX() > xposSettings &&
                screenHeight - Gdx.input.getY() < yposSettings + settingsButtonHeight &&
                screenHeight - Gdx.input.getY() > yposSettings) {
            game.hudBatch.draw(settingsButtonSelected, xposSettings - (settingsButtonSelectedWidth - settingsButtonWidth) / 2, yposSettings - (settingsButtonSelectedHeight - settingsButtonHeight) / 2, settingsButtonSelectedWidth, settingsButtonSelectedHeight);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new SettingsScreen(game));
            }
        } else {
            game.hudBatch.draw(settingsButton, xposSettings, yposSettings, settingsButtonWidth, settingsButtonHeight);
        }

        // Bouton Help (Règles du jeu)
        game.hudBatch.draw(helpButton, xposHelp, yposHelp, helpButtonWidth, helpButtonHeight);
        if (Gdx.input.getX() < xposHelp + helpButtonWidth &&
                Gdx.input.getX() > xposHelp &&
                screenHeight - Gdx.input.getY() < yposHelp + helpButtonHeight &&
                screenHeight - Gdx.input.getY() > yposHelp) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new HelpScreen(game));
            }
        }
        game.hudBatch.end();
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
