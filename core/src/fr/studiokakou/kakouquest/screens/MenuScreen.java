package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.GetProperties;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.hud.Hud;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;
import static com.badlogic.gdx.Gdx.input;


public class MenuScreen implements Screen {
    /**
     * Constants for the buttons
     * The width/height ratio must be 3:1 for the text buttons
     * and 1:1 for the icon buttons
     */
    private static final int PLAY_BUTTON_HEIGHT = 200;
    private static final int PLAY_BUTTON_WIDTH = 600;
    private static final int EXIT_BUTTON_HEIGHT = 70;
    private static final int EXIT_BUTTON_WIDTH = 210;
    private static final int SETTINGS_BUTTON_HEIGHT = 120;
    private static final int SETTINGS_BUTTON_WIDTH = 360;
    private static final int HELP_BUTTON_HEIGHT = 120;
    private static final int HELP_BUTTON_WIDTH = 120;
    /**
     * Espace de jeu.
     */
    GameSpace game;
    /*
    Définition des boutons
     */
    Texture playButton;
    Texture exitButton;
    Texture settingsButton;
    Texture helpButton;

    public MenuScreen(GameSpace game){
        this.game = game;
        playButton = new Texture("assets/buttons/play_button.png");
        exitButton = new Texture("assets/buttons/exit_button.png");
        settingsButton = new Texture("assets/buttons/settings_button.png");
        helpButton = new Texture("assets/buttons/help_button.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        /*
          Echap pour quitter l'écran
         */
        if (input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        /*
          Initialisation de l'écran
         */
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        /*
          Définition de l'abscisse pour chaque boutons en fonction
          de la taille (résolution) de l'écran
         */
        int xposPlay = Gdx.graphics.getWidth()/2 - PLAY_BUTTON_WIDTH/2;
        int xposExit = Gdx.graphics.getWidth()/8 - EXIT_BUTTON_WIDTH/2;
        int xposSettings = Gdx.graphics.getWidth()/2 - SETTINGS_BUTTON_WIDTH/2;
        int xposHelp = Gdx.graphics.getWidth() - HELP_BUTTON_WIDTH - 30;
        /*
           Définition des ordonnées
         */
        int yposPlay = Gdx.graphics.getHeight()/2;
        int yposExit = Gdx.graphics.getHeight()/8 - EXIT_BUTTON_HEIGHT/2;
        int yposSettings = Gdx.graphics.getHeight()/2 - SETTINGS_BUTTON_HEIGHT -30;
        int yposHelp = Gdx.graphics.getHeight() - HELP_BUTTON_HEIGHT - 30;
        /*
          Bouton Play
         */
        game.batch.draw(playButton, xposPlay, yposPlay, PLAY_BUTTON_WIDTH, PLAY_BUTTON_HEIGHT);
        if (Gdx.input.getX() < xposPlay + PLAY_BUTTON_WIDTH &&
                Gdx.input.getX() > xposPlay &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposPlay + PLAY_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposPlay) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new InGameScreen(game));
            }
        }
        /*
          Bouton Exit
         */
        game.batch.draw(exitButton, xposExit, yposExit, EXIT_BUTTON_WIDTH, EXIT_BUTTON_HEIGHT);
        if (Gdx.input.getX() < xposExit + EXIT_BUTTON_WIDTH &&
                Gdx.input.getX() > xposExit &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposExit + EXIT_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposExit) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                Gdx.app.exit();
            }
        }
        /*
          Bouton Settings
         */
        game.batch.draw(settingsButton, xposSettings, yposSettings, SETTINGS_BUTTON_WIDTH, SETTINGS_BUTTON_HEIGHT);
        /*
          Bouton Help (Règles du jeu)
         */
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
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {}
}
