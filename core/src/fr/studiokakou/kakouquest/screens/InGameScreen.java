package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;

public class InGameScreen implements Screen {

    //defaults
    public static float FRAME_DURATION=0.25f;

    GameSpace game;
    SpriteBatch batch;

    Player player;
    Camera cam;

    public InGameScreen(GameSpace game){
        this.game=game;
        this.batch = game.batch;
        this.player = new Player(100, 100, "player");
        this.cam = new Camera(this.player);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        cam.update();
        batch.setProjectionMatrix(cam.camera.combined);

        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        player.getKeyboardMove();
        player.getOrientation(this.cam.camera);

        batch.begin();

        player.draw(this.batch);

        batch.end();
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
