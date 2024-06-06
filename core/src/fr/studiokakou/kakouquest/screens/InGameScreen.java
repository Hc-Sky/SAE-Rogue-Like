package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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
 * Écran de jeu principal.
 * Cette classe gère l'écran de jeu principal.
 *
 * @version 1.0
 */
public class InGameScreen implements Screen {

	/**
	 * Durée entre chaque frame.
	 */
	public static float FRAME_DURATION=0.17f;

	/**
	 * Espace de jeu.
	 */
	GameSpace game;
	/**
	 * Batch pour le rendu.
	 */
	SpriteBatch batch;
	/**
	 * Batch pour l'HUD.
	 */
	SpriteBatch hudBatch;

	/**
	 * Temps écoulé depuis le début du jeu.
	 */
	public static float stateTime=0f;

	/**
	 * Joueur.
	 */
	Player player;
	/**
	 * Caméra.
	 */
	Camera cam;

	/**
	 * HUD du jeu.
	 */
	Hud hud;
	BitmapFont font;

	/**
	 * Niveau actuel.
	 */
	int currentLevel;
	/**
	 * Carte du jeu.
	 */
	Map map;
	/**
	 * Hauteur de la carte.
	 */
	public int map_height;
	/**
	 * Largeur de la carte.
	 */
	public int map_width;

	/**
	 * Temps de départ du jeu.
	 */
	long startTime;

	/**
	 * Constructeur de l'écran de jeu.
	 *
	 * @param game Le jeu
	 */
	public InGameScreen(GameSpace game){
		this.game = game;
		this.batch = game.batch;
		this.hudBatch = game.hudBatch;

		this.currentLevel = 1;

		Monster.createPossibleMonsters(currentLevel);
		MeleeWeapon.createPossibleMeleeWeapons();

		// Initialisation de la carte
		this.map_height = 80;
		this.map_width = 80;
		this.map = new Map(this.map_width, this.map_height);

		// Initialisation du joueur
		this.player = new Player(map.getPlayerSpawn(),"player");
		this.cam = new Camera(this.player);
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
							InGameScreen.stateTime = 0f;
							System.out.println("next level");
							currentLevel += 1;

							map = new Map(map_width, map_height);
							player.hasPlayerSpawn = false;
							player.setPos(map.getPlayerSpawn());

							startTime = TimeUtils.millis();

							map.spawnMonsters(currentLevel);
							map.genInteractive(currentLevel, InGameScreen.this);

							game.setScreen(InGameScreen.this);
						}
					});
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * Affiche l'écran de jeu.
	 */
	@Override
	public void show() {

		InGameScreen.stateTime=0f;

		// Définit le curseur
		Pixmap pm = new Pixmap(Gdx.files.internal("assets/cursor/melee_attack.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth()/2, pm.getHeight()/2));
		pm.dispose();

		this.hud = new Hud(this.player, this.currentLevel, this.cam.zoom);
		font = new BitmapFont();
		hud.setFont(font);

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
			player.getKeyboardMove(this.map);
			player.getKeyboardWeapon();
			player.getKeyboardPotion();
			player.getOrientation();
			player.dash(this.map);
		}

		cam.update();

		// Met à jour la position des monstres
		this.map.moveMonsters(this.player);
		this.map.updateInteractive(this.player);

		batch.setProjectionMatrix(Camera.camera.combined);

		batch.begin();

		// Dessine la carte
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
	 * Redimensionne l'écran de jeu.
	 *
	 * @param width Largeur
	 * @param height Hauteur
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

	/**
	 * Libère les ressources utilisées par l'écran de jeu.
	 */
	@Override
	public void dispose() {
		this.game.dispose();
		this.map.dispose();
	}
}
