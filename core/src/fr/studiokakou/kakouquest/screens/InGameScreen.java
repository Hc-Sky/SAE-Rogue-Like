package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.hud.Hud;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;

/**
 * le type InGameScreen.
 * Cette classe est utilisée pour créer un objet InGameScreen.
 *
 * @version 1.0
 */
public class InGameScreen implements Screen {

	/**
	 * le temps entre chaque frame.
	 */
//defaults
    public static float FRAME_DURATION=0.17f;

	/**
	 * le jeu.
	 */
//screen info
	GameSpace game;
	/**
	 * le batch.
	 */
	SpriteBatch batch;
	/**
	 * le batch de l'HUD.
	 */
	SpriteBatch hudBatch;

	public static float stateTime=0f;

	/**
	 * le joueur.
	 */
//player
	Player player;
	/**
	 * la caméra.
	 */
	Camera cam;

	/**
	 * l'HUD.
	 */
//hud
	Hud hud;

	/**
	 * le niveau actuel.
	 */
//map info
	int currentLevel;
	/**
	 * la map.
	 */
	Map map;
	/**
	 * la hauteur de la map.
	 */
	public int map_height;
	/**
	 * la largeur de la map.
	 */
	public int map_width;

	/**
	 * le temps de départ.
	 */
	long startTime;


	/**
	 * Constructeur de InGameScreen.
	 *
	 * @param game the game
	 */
	public InGameScreen(GameSpace game){
		this.game=game;
		this.batch = game.batch;
		this.hudBatch = game.hudBatch;


		this.currentLevel = 1;

		Monster.createPossibleMonsters(currentLevel);
		MeleeWeapon.createPossibleMeleeWeapons();

		//map init
		this.map_height = 150;
		this.map_width = 150;
		this.map = new Map(this.map_width, this.map_height);

		//player init
		this.player = new Player(map.getPlayerSpawn(),"player");
		this.cam = new Camera(this.player);
	}

	public void nextLevel(){
		InGameScreen.stateTime=0f;
		System.out.println("next level");
		this.currentLevel+=1;

		this.map = new Map(this.map_width, this.map_height);
		this.player.hasPlayerSpawn=false;
		this.player.setPos(map.getPlayerSpawn());

		startTime = TimeUtils.millis();

		this.map.spawnMonsters(currentLevel);
		this.map.genInteractive(currentLevel, this);
	}

	/**
	 * Affiche l'écran de jeu.
	 */
	@Override
	public void show() {

		InGameScreen.stateTime=0f;

		//set cursor
		Pixmap pm = new Pixmap(Gdx.files.internal("assets/cursor/melee_attack.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth()/2, pm.getHeight()/2));
		pm.dispose();

		this.hud = new Hud(this.player, this.currentLevel, this.cam.zoom);

		startTime = TimeUtils.millis();

		this.map.spawnMonsters(currentLevel);
		this.map.genInteractive(currentLevel, this);
	}

    @Override
    public void render(float delta) {
        InGameScreen.stateTime += delta;

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

		//update monsters pos
		this.map.moveMonsters(this.player);
		this.map.updateInteractive(this.player);

		batch.setProjectionMatrix(Camera.camera.combined);

		batch.begin();

		//map draw
		this.map.drawMap(this.batch);
		this.map.drawInteractive(this.batch);
		this.map.drawMonsters(batch);
		this.map.updateHitsAnimation(this.batch);

		player.regainStamina();
		player.draw(this.batch);

		batch.end();

		this.map.checkDeadMonster();


		hudBatch.begin();
		this.hud.draw(hudBatch);
		hudBatch.end();

		if (player.hp<=0){
			this.currentLevel=0;
			this.player.playerDeath();
			this.nextLevel();
		}

		this.map.updateRemoveInteractive();
	}

	/**
	 * Resize l'écran de jeu.
	 * Permet de redimensionner l'écran de jeu.
	 *
	 */
	@Override
	public void resize(int width, int height) {
		this.batch.getProjectionMatrix().setToOrtho2D(0, 0, width,height);
	}

	@Override
	public void pause() {
		// TODO
	}

	@Override
	public void resume() {
		// TODO
	}

	@Override
	public void hide() {
		// TODO
	}

	@Override
	public void dispose() {
		this.game.dispose();
		this.map.dispose();
	}
}
