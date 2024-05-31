package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.GameSpace;

import java.awt.Desktop;
import java.net.URI;

import static com.badlogic.gdx.Gdx.input;

public class GameModeChoice implements Screen {
    private static final int SOLO_BUTTON_HEIGHT = 300;
    private static final int SOLO_BUTTON_WIDTH = 600;
    private static final int MULTI_BUTTON_HEIGHT = 300;
    private static final int MULTI_BUTTON_WIDTH = 800;

    /**
     * Espace de jeu.
     */
    GameSpace game;
    /*
    Définition des boutons
     */
    Texture SoloButton;
    Texture MultiButton;
    Texture BackButton;

    public GameModeChoice(GameSpace game) {
        this.game = game;
        SoloButton = new Texture("assets/buttons/solo_button.png");
        MultiButton = new Texture("assets/buttons/multi_button.png");
        BackButton = new Texture("assets/buttons/back_button.png");

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        /*
          Echap pour quitter l'écran
         */
        /*if (input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }*/
        /*
          Initialisation de l'écran
         */
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        /*
          Définition de l'abscisse et ordonnées pour chaque boutons en fonction
          de la taille (résolution) de l'écran
         */
        int xposSolo = Gdx.graphics.getWidth()/4 - SOLO_BUTTON_WIDTH/2;
        int xposMulti = (Gdx.graphics.getWidth()/4)*3 - MULTI_BUTTON_WIDTH/2;
        int yposSolo = Gdx.graphics.getHeight()/2;
        int yposMulti = Gdx.graphics.getHeight()/2;

        game.batch.draw(SoloButton, xposSolo, yposSolo, SOLO_BUTTON_WIDTH, SOLO_BUTTON_HEIGHT);
        if (Gdx.input.getX() < xposSolo + SOLO_BUTTON_WIDTH &&
                Gdx.input.getX() > xposSolo &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposSolo + SOLO_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposSolo) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new InGameScreen(game));
            }
        }

        game.batch.draw(MultiButton, xposMulti, yposMulti, MULTI_BUTTON_WIDTH, MULTI_BUTTON_HEIGHT);
        if (Gdx.input.getX() < xposMulti + MULTI_BUTTON_WIDTH &&
                Gdx.input.getX() > xposMulti &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposMulti + MULTI_BUTTON_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposMulti) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                try {
                    Desktop desktop = Desktop.getDesktop();
                    desktop.browse(new URI("http://www.studio-kakou.fr/"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        game.batch.draw(BackButton, 60, 60, 230, 70);
        if (Gdx.input.getX() < 60 + 230 &&
                Gdx.input.getX() > 60 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < 60 + 70 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > 60) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }

        game.batch.end();
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
