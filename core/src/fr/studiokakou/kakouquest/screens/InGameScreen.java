package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
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

	private boolean isCountingDown = false;
	private float countdownTimer = 0;
	private Texture[] countdownTextures;
	private int countdownIndex = 0;
	private static final float COUNTDOWN_INTERVAL = 0.5f; // 0.5 second interval
	private Texture background;

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

		// Load countdown textures
		countdownTextures = new Texture[3];
		countdownTextures[0] = new Texture("assets/window/3.png");
		countdownTextures[1] = new Texture("assets/window/2.png");
		countdownTextures[2] = new Texture("assets/window/1.png");
		background = new Texture("assets/window/settings_background.png");
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
		if (paused) {
			System.out.println("Game is paused");
		}

		if (isCountingDown) {
			renderCountdown(delta);
			return;
		}

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

	private void renderCountdown(float delta) {
		countdownTimer -= delta;
		if (countdownTimer <= 0) {
			countdownIndex++;
			countdownTimer = COUNTDOWN_INTERVAL;
		}

		if (countdownIndex >= countdownTextures.length) {
			isCountingDown = false;
			paused = false;
			System.out.println("Countdown finished, resuming game");
			game.setPaused(false); // Assurez-vous que le jeu reprend
			return;
		}

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		hudBatch.begin();
		// Calculer les dimensions de l'image de fond pour qu'elle remplisse l'écran tout en conservant ses proportions
		float aspectRatio = 1129.0f / 959.0f;
		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float backgroundWidth = screenWidth;
		float backgroundHeight = screenHeight;

		if (screenWidth / screenHeight > aspectRatio) {
			backgroundWidth = screenHeight * aspectRatio;
		} else {
			backgroundHeight = screenWidth / aspectRatio;
		}

		// Dessiner l'image de fond
		game.hudBatch.draw(background, (screenWidth - backgroundWidth) / 2, (screenHeight - backgroundHeight) / 2, backgroundWidth, backgroundHeight);

		Texture currentTexture = countdownTextures[countdownIndex];
		float textureWidth = currentTexture.getWidth();
		float textureHeight = currentTexture.getHeight();
		hudBatch.draw(currentTexture, (screenWidth - textureWidth) / 2, (screenHeight - textureHeight) / 2);
		hudBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		this.batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
	}

	@Override
	public void pause() {
		paused = true;
		game.setPaused(true);
	}

	@Override
	public void resume() {
		// Ne rien faire ici
		System.out.println("Game resumed");
	}

	public void resumeGame() {
		startCountdown();
	}

	private void startCountdown() {
		isCountingDown = true;
		countdownTimer = COUNTDOWN_INTERVAL;
		countdownIndex = 0;
		paused = true; // Ensure the game is paused during the countdown
		System.out.println("Starting countdown");
	}

	@Override
	public void hide() {
		// TODO
	}

	@Override
	public void dispose() {
		this.game.dispose();
		this.map.dispose();
		for (Texture texture : countdownTextures) {
			texture.dispose();
		}
	}
}
