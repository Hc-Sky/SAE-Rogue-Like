package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

public class InGameScreen implements Screen {

    //defaults
    public static float FRAME_DURATION=0.20f;

    //screen info
    GameSpace game;
    SpriteBatch batch;

    //player
    Player player;
    Camera cam;

    //map info
    Map map;
    public int map_height;
    public int map_width;

    long startTime;


    public InGameScreen(GameSpace game){
        this.game=game;
        this.batch = game.batch;
        this.player = new Player("player");
        this.cam = new Camera(this.player);

        //map size
        this.map_height = 50;
        this.map_width = 50;
    }

    @Override
    public void show() {
        this.map = new Map(this.map_width, this.map_height);

        //set cursor
        Pixmap pm = new Pixmap(Gdx.files.internal("assets/cursor/melee_attack.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth()/2, pm.getHeight()/2));
        pm.dispose();

        startTime = TimeUtils.millis();
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }

        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (TimeUtils.millis() - startTime >= 1000 && !player.hasPlayerSpawn && !player.isPlayerSpawning){
            player.spawnPlayer(100, 100, this.cam);
        }

        if (player.hasPlayerSpawn && !player.isPlayerSpawning){
            player.getKeyboardMove();
            player.getOrientation(this.cam.camera);
            player.dash(this.cam.camera);
        }

        cam.update();
        batch.setProjectionMatrix(cam.camera.combined);

        batch.begin();

        //map draw
        this.map.drawMap(this.batch);

        player.draw(this.batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        this.batch.getProjectionMatrix().setToOrtho2D(0, 0, width,height);
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
        this.game.dispose();
    }
}
