package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.GameSpace;

import java.util.Random;

public class LoadingScreen implements Screen {
    private GameSpace game;
    private SpriteBatch hudBatch;
    private BitmapFont font;
    private String loadingMessage;
    private Texture texture;

    private static final String[] MESSAGES = {
            "Chargement du prochain étage...",
            "Les monstres arrivent...",
            "Vous n'arriverez pas au bout, chevalier...",
            "Vous pouvez empêcher les arnaques aux KakouBucks, ne partagez pas votre mot de passe avec des tiers...",
            "On prépare la suite pour vous Sir Kakou..."
    };

    // Existing code...

    public LoadingScreen(GameSpace game) {
        this.game = game;
        this.hudBatch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2);  // Agrandir la police par un facteur de 2
        this.loadingMessage = getRandomMessage();
        this.texture = null;
    }


    /**
     * Récupérer un message aléatoire parmi les messages de chargement.
     *
     * @return Message aléatoire.
     */
    private String getRandomMessage() {
        Random random = new Random();
        int index = random.nextInt(MESSAGES.length);
        return MESSAGES[index];
    }

    /**
     * Récupérer une texture aléatoire parmi les textures disponibles.
     *
     * @return Texture aléatoire.
     */
    private Texture getRandomTexture() {
        Random random = new Random();
        // 1 chance sur 15 de charger "fede.png" au lieu de "icon.png"
        if (game.isKonamiActivated()){
            Gdx.app.log("LoadingScreen", "Easter Egg found!");
            return new Texture(Gdx.files.internal("assets/window/easter-egg.png"));
        }
        else if (random.nextInt(15) == 0) {
            Gdx.app.log("LoadingScreen", "Loading fede.png");
            return new Texture(Gdx.files.internal("assets/window/fede.png"));
        } else {
            Gdx.app.log("LoadingScreen", "Loading icon.png");
            return new Texture(Gdx.files.internal("assets/window/icon.png"));
        }
    }

    /**
     * Afficher l'écran de chargement.
     */
    @Override
    public void show() {
        // Réinitialiser le message et l'image chaque fois que l'écran de chargement est affiché
        this.loadingMessage = getRandomMessage();
        this.texture = getRandomTexture();
    }

    /**
     * Dessiner l'écran de chargement.
     *
     * @param delta Delta time.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hudBatch.begin();

        if (texture != null) {
            // Dessiner l'image
            float textureWidth = texture.getWidth() * 3f; // Agrandir l'image proportionnellement
            float textureHeight = texture.getHeight() * 3f;
            float textureX = (Gdx.graphics.getWidth() - textureWidth) / 2;
            float textureY = (Gdx.graphics.getHeight() - textureHeight) / 2;
            hudBatch.draw(texture, textureX, textureY, textureWidth, textureHeight);
        } else {
            Gdx.app.log("LoadingScreen", "Texture is null");
        }

        GlyphLayout layout = new GlyphLayout(font, loadingMessage);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = (Gdx.graphics.getHeight() + layout.height) / 2 - texture.getHeight() * 2f; // Décalage en fonction de la hauteur de l'image

        font.draw(hudBatch, layout, x, y);
        hudBatch.end();
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
        // Libérer la texture lorsque l'écran est caché
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    @Override
    public void dispose() {
        font.dispose();
        hudBatch.dispose();
        if (texture != null) {
            texture.dispose();
        }
    }
}
