package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.GetCoreProperties;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.hud.Hud;
import fr.studiokakou.kakouquest.map.BossMap;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.upgradeCard.UpgradeCardScreen;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;

public class InGameScreen implements Screen {

	public static float FRAME_DURATION = 0.17f;

	GameSpace game;
	/**
	 * Batchs pour le rendu.
	 */
	SpriteBatch batch;
	SpriteBatch hudBatch;
	SpriteBatch upgradeBatch;

	public static float stateTime = 0f;

	Player player;
	public static int score;
	Camera cam;
	Hud hud;
	BitmapFont font;

	public static int currentLevel;
	public static int deepestLevel;
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

	String selectedAvatarTexture;

	// Konami Code variables
	private final int[] konamiCode = {
			Input.Keys.UP, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.DOWN,
			Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.RIGHT,
			Input.Keys.B, Input.Keys.A
	};

	private Array<Integer> konamiSequence;
	private boolean konamiActivated = false;

	/**
	 * Constructeur de l'écran de jeu.
	 *
	 * @param game Le jeu
	 */
	public InGameScreen(GameSpace game, String selectedAvatarTexture) {
		this.game = game;
		this.selectedAvatarTexture = selectedAvatarTexture;

		this.batch = game.batch;
		this.hudBatch = game.hudBatch;
		this.upgradeBatch = game.upgradeBatch;

		currentLevel = 1;
		deepestLevel = 1;

		Monster.createPossibleMonsters(currentLevel);
		MeleeWeapon.createPossibleMeleeWeapons();

		this.map_height = 80;
		this.map_width = 80;
		this.map = new Map(this.map_width, this.map_height);

		// Initialisation du joueur
		this.player = new Player(map.getPlayerSpawn(), loadUsername(), selectedAvatarTexture);
		this.cam = new Camera(this.player);

		score = 0;

		// Load countdown textures
		countdownTextures = new Texture[3];
		countdownTextures[0] = new Texture("assets/window/3.png");
		countdownTextures[1] = new Texture("assets/window/2.png");
		countdownTextures[2] = new Texture("assets/window/1.png");
		background = new Texture("assets/window/settings_background.png");
		// Initialize the Konami sequence tracker
		konamiSequence = new Array<Integer>(10);
	}

	public InGameScreen(GameSpace game, String selectedAvatarTexture, int currentLevel, Player player, int score, int deepestLevel) {
		this.game = game;

		this.batch = game.batch;
		this.hudBatch = game.hudBatch;
		this.upgradeBatch = game.upgradeBatch;

		InGameScreen.currentLevel = currentLevel;
		InGameScreen.deepestLevel = deepestLevel;

		Monster.createPossibleMonsters(currentLevel);
		MeleeWeapon.createPossibleMeleeWeapons();

		this.map_height = 80;
		this.map_width = 80;
		map = new Map(this.map_width, this.map_height);

		// Initialisation du joueur
		this.player = player;
		this.player.setPos(map.getPlayerSpawn());
		this.cam = new Camera(this.player);

		InGameScreen.score = score;

		// Load countdown textures
		countdownTextures = new Texture[3];
		countdownTextures[0] = new Texture("assets/window/3.png");
		countdownTextures[1] = new Texture("assets/window/2.png");
		countdownTextures[2] = new Texture("assets/window/1.png");
		background = new Texture("assets/window/settings_background.png");
		// Initialize the Konami sequence tracker
		konamiSequence = new Array<Integer>(10);
	}

	/**
	 * Passe au niveau suivant.
	 */
	public void nextLevel() {
		game.setScreen(new LoadingScreen(game));

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000); // Simulate loading time (optional)

					Gdx.app.postRunnable(new Runnable() {
						@Override
						public void run() {
							if ((currentLevel+1) % 5 == 0){
								InGameScreen.currentLevel++;
								InGameScreen.stateTime = 0f;
								InGameScreen.score+=150;
								System.out.println("boss level");


								map = new BossMap(10,10);
								player.hasPlayerSpawn = false;
								player.setPos(map.getPlayerSpawn());

								startTime = TimeUtils.millis();

								map.genInteractive(currentLevel, InGameScreen.this);

								game.setScreen(InGameScreen.this);
							}
							else {
								InGameScreen.currentLevel += 1;
								InGameScreen.stateTime = 0f;
								System.out.println("next level");

								map = null;
								map = new Map(map_width, map_height);
								player.hasPlayerSpawn = false;
								player.setPos(map.getPlayerSpawn());

								startTime = TimeUtils.millis();

								map.spawnMonsters(currentLevel);
								map.genInteractive(currentLevel, InGameScreen.this);

								game.setScreen(new InGameScreen(game, selectedAvatarTexture, currentLevel, player, score, deepestLevel));
							}
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void show() {
		if (!initialized) {
			InGameScreen.stateTime = 0f;


			Pixmap pm = new Pixmap(Gdx.files.internal("assets/cursor/melee_attack.png"));
			Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth() / 2, pm.getHeight() / 2));
			pm.dispose();

			this.hud = new Hud(this.player, currentLevel,cam.zoom);

			startTime = TimeUtils.millis();
		    font = new BitmapFont();
            hud.setFont(font);

			Monster.initExclamationMark();

			this.map.spawnMonsters(currentLevel);
			this.map.genInteractive(currentLevel, this);

		    this.map.spawnMonsters(currentLevel);
		    this.map.genInteractive(currentLevel, this);

		    UpgradeCardScreen.initUpgradeCards();
			initialized = true;
		}
	}

	@Override
	public void render(float delta) {

		if (isCountingDown) {
			renderCountdown(delta);
			return;
		}

		if (currentLevel%5 == 0){
			if (BossMap.isBossDefeated()){
				map.stairs.canInteract = true;
			} else {
				map.stairs.canInteract = false;
			}
		}

		InGameScreen.stateTime += delta;

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			game.previousScreen = this;
			game.setScreen(new PauseScreen(game, this));
			pause();
			return;
		}


		if (Gdx.input.isKeyPressed(Input.Keys.N)){
			player.experience += 100;
		}

		if (!konamiActivated) {
			checkKonamiCode();
		}

		Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if (TimeUtils.millis() - startTime >= 1000 && !player.hasPlayerSpawn && !player.isPlayerSpawning) {
			player.spawnPlayer();
		}

		if (player.hasPlayerSpawn && !player.isPlayerSpawning && ! UpgradeCardScreen.isUpgrading){
			player.getKeyboardMove(this.map);
			player.getKeyboardWeapon();
			player.getKeyboardPotion();
			player.getOrientation();
			player.dash(this.map);
		}

		cam.update();

		// Met à jour la position des monstres
		if (! UpgradeCardScreen.isUpgrading){
			this.map.moveMonsters(this.player);
			this.map.updateInteractive(this.player);
		}

		batch.setProjectionMatrix(Camera.camera.combined);

		batch.begin();

		this.map.drawMap(this.batch);
		this.map.drawInteractive(this.batch);
		this.map.drawMonsters(batch);
		this.map.updateHitsAnimation(this.batch);

		if (!UpgradeCardScreen.isUpgrading){
			player.regainStamina();
		}
		player.draw(this.batch, map);

		batch.end();

		this.map.checkDeadMonster();

		player.checkUpgrade();


		if(! UpgradeCardScreen.isUpgrading){
			hudBatch.begin();
			this.hud.draw(hudBatch);
			hudBatch.end();

			ShapeRenderer shapeRenderer = new ShapeRenderer();
			this.hud.drawXpBar(shapeRenderer);
		}

		if (UpgradeCardScreen.isUpgrading){
			upgradeBatch.begin();
			UpgradeCardScreen.draw(upgradeBatch, player);
			upgradeBatch.end();
		}


		if (player.hp<=0 && ! UpgradeCardScreen.isUpgrading){
			if (currentLevel > deepestLevel){
				deepestLevel = currentLevel;
			}
			currentLevel=0;
			this.player.playerDeath();
			this.nextLevel();
		}

		if (UpgradeCardScreen.isUpgrading){
			upgradeBatch.begin();
			upgradeBatch.end();
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

	private void checkKonamiCode() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP) ||
				Gdx.input.isKeyJustPressed(Input.Keys.DOWN) ||
				Gdx.input.isKeyJustPressed(Input.Keys.LEFT) ||
				Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) ||
				Gdx.input.isKeyJustPressed(Input.Keys.B) ||
				Gdx.input.isKeyJustPressed(Input.Keys.A)) {

			int key = Gdx.input.isKeyJustPressed(Input.Keys.UP) ? Input.Keys.UP :
					Gdx.input.isKeyJustPressed(Input.Keys.DOWN) ? Input.Keys.DOWN :
							Gdx.input.isKeyJustPressed(Input.Keys.LEFT) ? Input.Keys.LEFT :
									Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) ? Input.Keys.RIGHT :
											Gdx.input.isKeyJustPressed(Input.Keys.B) ? Input.Keys.B :
													Gdx.input.isKeyJustPressed(Input.Keys.A) ? Input.Keys.A : -1;

			konamiSequence.add(key);

			if (konamiSequence.size > konamiCode.length) {
				konamiSequence.removeIndex(0);
			}

			boolean match = true;
			for (int i = 0; i < konamiSequence.size; i++) {
				if (konamiSequence.get(i) != konamiCode[i]) {
					match = false;
					break;
				}
			}

			if (match && konamiSequence.size == konamiCode.length) {
				konamiActivated = true;
				System.out.println("Konami Code Activated!");
				// Set the konami flag in the game
				game.setKonamiActivated(true);
			}
		}
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
	private String loadUsername() {
		if (GetCoreProperties.getStringProperty("USERNAME") == null || GetCoreProperties.getStringProperty("USERNAME").isEmpty()) {
			return "guest";
		}
		return GetCoreProperties.getStringProperty("USERNAME");
	}
}

