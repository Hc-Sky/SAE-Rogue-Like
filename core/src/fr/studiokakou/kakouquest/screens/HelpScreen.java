package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.GameSpace;
import org.w3c.dom.Text;

public class HelpScreen implements Screen {
    /**
     * Constantes de position pour le png des règles du jeu
     */
    private static final int TEXT_HEIGHT = Gdx.graphics.getHeight() - 60;
    private static final int TEXT_WIDTH = Gdx.graphics.getWidth() - 60;

    /**
     * Espace de jeu.
     */
    GameSpace game;
    Texture backButton;
    Texture backButtonSelected;
    Texture resumeButton;
    Texture resumeButtonSelected;
    Texture text;

    public HelpScreen(GameSpace game){
        this.game = game;
        backButton = new Texture("assets/buttons/back_button.png");
        backButtonSelected = new Texture("assets/buttons/back_button_selected.png");
        resumeButton = new Texture("assets/buttons/resume_button.png");
        resumeButtonSelected = new Texture("assets/buttons/resume_button_selected.png");
        text = new Texture("assets/window/regles_du_jeu.png");
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        // Retour au menu principal en appuyant sur Echap
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            game.setScreen(new MenuScreen(game));
        }
        /*
        Coordonnées pour le texte :
         */
        int xposText = 60;
        int yposText = Gdx.graphics.getHeight() - 60;
        /*
          Initialisation de l'écran
         */
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | GL20.GL_STENCIL_BUFFER_BIT);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);


        game.hudBatch.begin();

        // Texte des règles du jeu
        game.hudBatch.draw(text, 30, 40, TEXT_WIDTH, TEXT_HEIGHT);


        //Bouton Back
        if (Gdx.input.getX() < 620 + 230 &&
                Gdx.input.getX() > 620 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < 15 + 70 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > 15) {
            game.hudBatch.draw(backButtonSelected, 620, 10, 230, 110);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
        else {game.hudBatch.draw(backButton, 620, 10, 230, 110);}

        // Dessiner le bouton Resume si le jeu est en pause
        if (game.isPaused()) { // Vérifier si le jeu est en pause
            int resumeButtonX = 900; // Ajuster la position X pour le bouton Resume
            int resumeButtonY = 10; // Même position Y que le bouton Back
            int resumeButtonWidth = 230; // Largeur du bouton Resume
            int resumeButtonHeight = 110; // Hauteur du bouton Resume

            if (Gdx.input.getX() < resumeButtonX + resumeButtonWidth &&
                    Gdx.input.getX() > resumeButtonX &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() < resumeButtonY + resumeButtonHeight &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() > resumeButtonY) {
                game.hudBatch.draw(resumeButtonSelected, resumeButtonX, resumeButtonY, resumeButtonWidth, resumeButtonHeight);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    game.setScreen(game.previousScreen);
                    if (game.previousScreen instanceof InGameScreen) {
                        ((InGameScreen) game.previousScreen).resumeGame();
                    }
                }
            } else {
                game.hudBatch.draw(resumeButton, resumeButtonX, resumeButtonY, resumeButtonWidth, resumeButtonHeight);
            }
        }

        game.hudBatch.end();
    }

    @Override
    public void resize(int i, int i1) {

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
