package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;

public class SplashScreen implements Screen {

    //screen info
    GameSpace game;
    SpriteBatch batch;

    Texture icon;
    float height;
    float width;

    long startTime;

    public SplashScreen(GameSpace game){
        this.game=game;
        this.batch = game.batch;

        this.height = (float) Gdx.graphics.getHeight() /2;
        this.width = this.height;

        startTime = TimeUtils.millis();
    }

    @Override
    public void show() {
        this.icon = new Texture("assets/window/icon.png");
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(TimeUtils.millis() - startTime >= 1000) {
            this.dispose();
            System.out.println("bonjour");
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
