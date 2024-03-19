package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.hud.Hud;
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
    SpriteBatch hudBatch;

    //player
    Player player;
    Camera cam;

    //hud
    Hud hud;

    //map info
    int currentLevel;
    Map map;
    public int map_height;
    public int map_width;

    long startTime;


    public InGameScreen(GameSpace game){
        this.game=game;
        this.batch = game.batch;
        this.hudBatch = game.hudBatch;


        this.currentLevel = 1;

        //map init
        this.map_height = 80;
        this.map_width = 80;
        this.map = new Map(this.map_width, this.map_height);

        //player init
        this.player = new Player(map.getPlayerSpawn(),"player");
        this.cam = new Camera(this.player);
    }

    @Override
    public void show() {

        //set cursor
        Pixmap pm = new Pixmap(Gdx.files.internal("assets/cursor/melee_attack.png"));
        Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth()/2, pm.getHeight()/2));
        pm.dispose();

        this.hud = new Hud(this.player, this.currentLevel, this.cam.zoom);

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
            player.spawnPlayer();
        }

        if (player.hasPlayerSpawn && !player.isPlayerSpawning){
            player.getKeyboardMove();
            player.getOrientation();
            player.dash();
        }

        cam.update();
        batch.setProjectionMatrix(Camera.camera.combined);

        batch.begin();

        //map draw
        this.map.drawMap(this.batch);
        this.map.updateHitsAnimation(this.batch);

        player.draw(this.batch);

        batch.end();


        hudBatch.begin();
        this.hud.draw(hudBatch);
        hudBatch.end();
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
