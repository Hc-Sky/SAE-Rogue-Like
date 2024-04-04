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
	 * le temps entre chaque frame pour les animations.
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

	/**
	 * variable stateTime utilisée pour gérer les animations en fonctions des images par secondes du joueur pour éviter d'avoir un changement de vitesse d'animation
	 */
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

		//utilisé pour gérer la rareté des armes et des monstres car ils sont dans des dictionnaires qu'il faut initialiser
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
	 * Créé l'écran du jeu
	 */
	@Override
	public void show() {

		//on initialise le stateTime à 0 a la création du InGameScreen
		InGameScreen.stateTime=0f;

		//On définie l'image du curseur
		Pixmap pm = new Pixmap(Gdx.files.internal("assets/cursor/melee_attack.png"));
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, pm.getWidth()/2, pm.getHeight()/2));
		pm.dispose();

		//On créer le HUD
		this.hud = new Hud(this.player, this.currentLevel, this.cam.zoom);

		//On récupère le temps actuel à la création pour gérer l'animation d'apparition du joueur
		startTime = TimeUtils.millis();

		//On fait apparaître les monstres et les objets interractifs sur la map
		this.map.spawnMonsters(currentLevel);
		this.map.genInteractive(currentLevel, this);
	}

	/**
	 * Function called on each new frame
	 * @param delta The time in seconds since the last render.
	 */
    @Override
    public void render(float delta) {
		//on augmente le stateTime courant
        InGameScreen.stateTime += delta;

		//uniquement au developpement : permet de quitter le jeu avec la touche échap
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
			Gdx.app.exit();
		}

		//on efface tout ce qu'il y a sur l'écran en choisissant la couleur de fond
		Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//si ça fait une seconde que la partie est lancée : on lance l'animation d'apparition du joueur
		if (TimeUtils.millis() - startTime >= 1000 && !player.hasPlayerSpawn && !player.isPlayerSpawning){
			player.spawnPlayer();
		}

		//si le joueur est apparue : on actualise les touches du clavier et ses mouvements
		if (player.hasPlayerSpawn && !player.isPlayerSpawning){
			player.getKeyboardMove(this.map);
			player.getOrientation();
			player.dash(this.map);
		}

		//on actualise la position de la caméra
		cam.update();

		//update monsters pos
		this.map.moveMonsters(this.player);
		this.map.updateInteractive(this.player);

		//on choisit la vue de la caméra pour afficher l'écran
		batch.setProjectionMatrix(Camera.camera.combined);

		//on commence à "dessiner"
		batch.begin();

		//map draw
		this.map.drawMap(this.batch);
		this.map.drawInteractive(this.batch);
		this.map.drawMonsters(batch);
		this.map.updateHitsAnimation(this.batch);

		//on appel la fonction qui permet au joueur de regagner son énergie si besoin
		player.regainStamina();

		//on affiche le player
		player.draw(this.batch);

		batch.end();

		//on vérifie si des monstres sont morts
		this.map.checkDeadMonster();

		//on dessine le HUD
		hudBatch.begin();
		this.hud.draw(hudBatch);
		hudBatch.end();

		//si le joueur est mort : on recommence à 0
		if (player.hp<=0){
			this.currentLevel=0;
			this.player.playerDeath();
			this.nextLevel();
		}

		//on actualise les objets avec lesquels le joueur a intérragie
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
