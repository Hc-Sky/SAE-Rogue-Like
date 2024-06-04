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
    private SpriteBatch batch;
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

    public LoadingScreen(GameSpace game) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.font = new BitmapFont();
        this.font.getData().setScale(2);  // Agrandir la police par un facteur de 2
        this.loadingMessage = getRandomMessage();
        this.texture = null;
    }

    private String getRandomMessage() {
        Random random = new Random();
        int index = random.nextInt(MESSAGES.length);
        return MESSAGES[index];
    }

    private Texture getRandomTexture() {
        Random random = new Random();
        // 1 chance sur 20 de charger "fede.png" au lieu de "icon.png"
        if (random.nextInt(15) == 0) {
            Gdx.app.log("LoadingScreen", "Loading fede.png");
            return new Texture(Gdx.files.internal("assets/window/fede.png"));
        } else {
            Gdx.app.log("LoadingScreen", "Loading icon.png");
            return new Texture(Gdx.files.internal("assets/window/icon.png"));
        }
    }

    @Override
    public void show() {
        // Réinitialiser le message et l'image chaque fois que l'écran de chargement est affiché
        this.loadingMessage = getRandomMessage();
        this.texture = getRandomTexture();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        if (texture != null) {
            // Dessiner l'image
            float textureX = (float) (Gdx.graphics.getWidth() / 2 -250);
            float textureY = ((float) Gdx.graphics.getHeight() / 2 -100);
            batch.draw(texture, textureX, textureY, 500,500);
        } else {
            Gdx.app.log("LoadingScreen", "Texture is null");
        }

        GlyphLayout layout = new GlyphLayout(font, loadingMessage);
        float x = (Gdx.graphics.getWidth() - layout.width) / 2;
        float y = (Gdx.graphics.getHeight() + layout.height) / 2 - 250; // Décalage de 50 pixels vers le bas

        font.draw(batch, layout, x, y);
        batch.end();
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
        batch.dispose();
        if (texture != null) {
            texture.dispose();
        }
    }
}
