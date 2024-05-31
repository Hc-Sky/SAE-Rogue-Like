package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.map.Point;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import fr.studiokakou.kakouquest.player.Player;

import static com.badlogic.gdx.Gdx.input;

public class AvatarChoiceScreen implements Screen {

    private GameSpace game;
    private Stage stage;
    private String[] texturePaths;

    // Déclaration des BitmapFonts pour le titre et les noms
    private BitmapFont titleFont;
    private BitmapFont nameFont;

    public AvatarChoiceScreen(GameSpace game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        // Initialisation des BitmapFonts avec les fichiers de police
        titleFont = new BitmapFont();
        nameFont = new BitmapFont();
        Gdx.input.setInputProcessor(stage);

        // Charger les chemins de textures
        texturePaths = new String[]{
                "knight_1", "angel", "doc", "dwarf_f", "dwarf_m",
                "elf_f", "elf_m", "lizard_m", "necromancer",
                "pumpkin_dude", "wizzard_f", "wizzard_m"
        };

        // Créer la table
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Ajouter les textures à la table en tant qu'images
        for (int i = 0; i < texturePaths.length; i++) {
            String texturePath = texturePaths[i];
            Texture texture = new Texture(Gdx.files.internal("assets/entities/" + texturePath + ".png"));
            Image image = new Image(texture);
            final int index = i;
            image.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    // Gestion du clic sur une image
                    System.out.println("Avatar " + (index + 1) + " clicked!");
                    // Création du joueur avec l'avatar sélectionné
                    Player player = new Player(new Point(0, 0), "PlayerName", texturePaths[index]);
                    game.setPlayer(player);
                    game.removeAvatarChoiceScreen(); // Supprime l'écran de sélection d'avatar
                    game.setScreen(new InGameScreen(game,texturePath));
                }
            });
            table.add(image).size(160, 200).expand().pad(10);
            if ((i + 1) % 4 == 0) {
                table.row();
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        /*
          Echap pour quitter l'écran
        */
        if (input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }

        Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        // Dessiner le titre en haut de l'écran
        // Définir une échelle pour le titre
        float scale = 5.0f; // Facteur d'échelle, ajustez selon vos besoins
        // Appliquer l'échelle au BitmapFont du titre
        titleFont.getData().setScale(scale);
        SpriteBatch batch = (SpriteBatch) stage.getBatch();
        batch.begin();
        titleFont.draw(batch, "Choisis ton aventurier", (float) (Gdx.graphics.getWidth() /2) - 350, Gdx.graphics.getHeight() - 20);
        scale = 3.0f;
        titleFont.getData().setScale(scale);
        titleFont.draw(batch, "Sir Kakou", 150, Gdx.graphics.getHeight() - 300);
        titleFont.draw(batch, "Stupidon", 640, Gdx.graphics.getHeight() - 300);
        titleFont.draw(batch, "Doc", 1165, Gdx.graphics.getHeight() - 300);
        titleFont.draw(batch, "Yatmaeg Warbrow", 1500, Gdx.graphics.getHeight() - 300);
        titleFont.draw(batch, "Agreada Chaospike", 70, Gdx.graphics.getHeight() - 660);
        titleFont.draw(batch, "Galadriel", 630, Gdx.graphics.getHeight() - 660);
        titleFont.draw(batch, "Ecthelion", 1115, Gdx.graphics.getHeight() - 660);
        titleFont.draw(batch, "Fournay", 1600, Gdx.graphics.getHeight() - 660);
        titleFont.draw(batch, "Le Necromancien", 75, Gdx.graphics.getHeight() - 1020);
        titleFont.draw(batch, "Pumpkin Dude", 580, Gdx.graphics.getHeight() - 1020);
        titleFont.draw(batch, "Aurelia", 1130, Gdx.graphics.getHeight() - 1020);
        titleFont.draw(batch, "Dumbledalf", 1585, Gdx.graphics.getHeight() - 1020);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        stage.dispose();
    }
}
