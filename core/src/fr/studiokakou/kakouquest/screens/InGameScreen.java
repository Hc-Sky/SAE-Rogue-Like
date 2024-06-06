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

public class InGameScreen implements Screen {

	public static float FRAME_DURATION = 0.17f;

	GameSpace game;
	SpriteBatch batch;
	SpriteBatch hudBatch;

	public static float stateTime = 0f;

	Player player;
	Camera cam;
	Hud hud;

	int currentLevel;
	Map map;
	public int map_height;
	public int map_width;

	long startTime;
	private boolean paused;

	private boolean initialized = false;

	public InGameScreen(GameSpace game) {
		this.game = game;
		this.batch = game.batch;
		this.hudBatch = game.hudBatch;

		this.currentLevel = 1;

		Monster.createPossibleMonsters(currentLevel);
		MeleeWeapon.createPossibleMeleeWeapons();

		this.map_height = 80;
		this.map_width = 80;
		this.map = new Map(this.map_width, this.map_height);

		this.player = new Player(map.getPlayerSpawn(), "player");
		this.cam = new Camera(this.player);
	}

	public void nextLevel() {
		InGameScreen.stateTime = 0f;
		System.out.println("next level");
		this.currentLevel += 1;

		this.map = new Map(this.map_width, this.map_height);
		this.player.hasPlayerSpawn = false;
		this.player.setPos(map.getPlayerSpawn());

		startTime = TimeUtils.millis();

		this.map.spawnMonsters(currentLevel);
		this.map.genInteractive(currentLevel, this);
	}

	@Override
	public void show() {
		if (!initialized) {
			InGameScreen.stateTime = 0f;

			Pixmap pm = new Pixmap(Gdx.files.internal("assets/cursor/melee_attack.png"));
			Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth() / 2, pm.getHeight() / 2));
			pm.dispose();

			this.hud = new Hud(this.player, this.currentLevel, this.cam.zoom);

			startTime = TimeUtils.millis();

			this.map.spawnMonsters(currentLevel);
			this.map.genInteractive(currentLevel, this);

			initialized = true;
		}
	}

	@Override
	public void render(float delta) {
		if (paused) return;

		InGameScreen.stateTime += delta;

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.previousScreen = this;
			game.setScreen(new PauseScreen(game));
			pause();
			return;
		}

		Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (TimeUtils.millis() - startTime >= 1000 && !player.hasPlayerSpawn && !player.isPlayerSpawning) {
			player.spawnPlayer();
		}

		if (player.hasPlayerSpawn && !player.isPlayerSpawning) {
			player.getKeyboardMove(this.map);
			player.getOrientation();
			player.dash(this.map);
		}

		cam.update();

		this.map.moveMonsters(this.player);
		this.map.updateInteractive(this.player);

		batch.setProjectionMatrix(Camera.camera.combined);

		batch.begin();

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

		if (player.hp <= 0) {
			this.currentLevel = 0;
			this.player.playerDeath();
			this.nextLevel();
		}

		this.map.updateRemoveInteractive();
	}

	@Override
	public void resize(int width, int height) {
		this.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void pause() {
		game.setPaused(true);
	}

	@Override
	public void resume() {

	}

	public void resumeGame() {
		game.setPaused(false);
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
