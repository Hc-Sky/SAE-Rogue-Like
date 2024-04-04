package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;

/**
 * le type SplashScreen
 * Cette classe est utilisée pour créer un objet SplashScreen.
 * splash screen = écran de démarrage
 * @version 1.0
 */
public class SplashScreen implements Screen {

    /**
     * le jeu.
     */
//screen info
    GameSpace game;
    /**
     * le batch = une collection de sprites.
     */
    SpriteBatch batch;

    /**
     * l'icone.
     * C'est l'image qui s'affiche à l'écran.
     */
    Texture icon;
    /**
     * La hauteur.
     */
    float height;
    /**
     * La largeur.
     */
    float width;

    /**
     * Le temps de démarrage.
     */
    long startTime;

    /**
     * Constructeur de SplashScreen.
     * Sert à créer un objet SplashScreen.
     *
     * @param game the game
     */
    public SplashScreen(GameSpace game){
        this.game=game;
        this.batch = game.batch;

        this.height = (float) Gdx.graphics.getHeight() /2;
        this.width = this.height;

        startTime = TimeUtils.millis();
    }

    /**
     * Affiche l'icone.
     */
    @Override
    public void show() {
        this.icon = new Texture("assets/window/icon_with_background.png");
    }

    /**
     * Affiche l'écran de démarrage.
     *
     * @param v
     */
    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(TimeUtils.millis() - startTime >= 1000) {
            this.dispose();
            game.setScreen(new InGameScreen(game));
        }

        batch.begin();

        batch.draw(this.icon, ((float) Gdx.graphics.getWidth() /2) - (this.width /2), ((float) Gdx.graphics.getHeight() /2) - (this.height /2), this.width, this.height);

        batch.end();
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
        this.icon.dispose();
    }
}
