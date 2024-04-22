package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GameSpace;
import static fr.studiokakou.kakouquest.utils.Utils.getAnimationHorizontal;

public class HelpScreen implements Screen {
    /**
     * Constantes de position pour le png des règles du jeu
     */
    private static final int TEXT_HEIGHT = Gdx.graphics.getHeight() - 60;
    /**
     * Espace de jeu.
     */
    GameSpace game;
    Texture backButton;
    Texture text;

    public HelpScreen(GameSpace game){
        this.game = game;
        backButton = new Texture("assets/buttons/back_button.png");
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


        game.batch.begin();

        // Texte des règles du jeu
        game.batch.draw(text, 30, 30, TEXT_HEIGHT, TEXT_HEIGHT);


        //Bouton Back
        game.batch.draw(backButton, 620, 15, 230, 70);
        if (Gdx.input.getX() < 620 + 230 &&
                Gdx.input.getX() > 620 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < 15 + 70 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > 15) {
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
