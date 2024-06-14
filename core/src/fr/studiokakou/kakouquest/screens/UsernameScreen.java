package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.GetCoreProperties;

public class UsernameScreen implements Screen {

    public static String username;

    GameSpace game;

    SpriteBatch batcher;

    // Image de fond
    Texture background;

    Texture backButton;
    Texture backButtonSelected;

    // Police avec échelle ajustée
    BitmapFont scaledFont;

    public UsernameScreen(GameSpace game) {
        this.game = game;
        batcher = game.batch;
        backButton = new Texture("assets/buttons/back_button.png");
        backButtonSelected = new Texture("assets/buttons/back_button_selected.png");

        background = new Texture("assets/window/username_background.png");

        // Ajuster l'échelle de la police
        scaledFont = new BitmapFont(); // Utilisez votre police actuelle ici si elle est différente
        scaledFont.getData().setScale(3.0f); // Agrandir la police par un facteur de 3

        // Charger le nom d'utilisateur depuis le fichier properties
        loadUsername();
    }

    private void loadUsername() {
        username = GetCoreProperties.getStringProperty("USERNAME");
        if (username == null || username.isEmpty()) {
            username = "guest";
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.ENTER && username.length() > 3) {
                    GetCoreProperties.setStringProperty("USERNAME", username);
                    game.setScreen(new AvatarChoiceScreen(game));
                } else if (keycode == Input.Keys.BACKSPACE) {
                    if (UsernameScreen.username.length() > 0) {
                        UsernameScreen.username = UsernameScreen.username.substring(0, UsernameScreen.username.length() - 1);
                    }
                } else if (keycode == Input.Keys.SPACE) {
                    UsernameScreen.username += " ";
                } else if (keycode == Input.Keys.ESCAPE) {
                    game.setScreen(new MenuScreen(game));
                } else if (username.length() < 20) {
                    String character = getAzertyCharacter(keycode);
                    if (character != null) {
                        UsernameScreen.username += character;
                    }
                }
                return true;
            }
        });
    }

    private String getAzertyCharacter(int keycode) {
        switch (keycode) {
            case Input.Keys.A: return "q";
            case Input.Keys.Q: return "a";
            case Input.Keys.W: return "z";
            case Input.Keys.Z: return "w";
            case Input.Keys.M: return ",";
            case Input.Keys.SEMICOLON: return "m";
            case Input.Keys.LEFT_BRACKET: return "^";
            case Input.Keys.RIGHT_BRACKET: return "$";
            case Input.Keys.BACKSLASH: return "*";
            case Input.Keys.EQUALS: return "=";
            default:
                return Input.Keys.toString(keycode).toLowerCase();
        }
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.hudBatch.begin();
        // Dessiner l'image de fond
        game.hudBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        int backWidth = 230;
        int backHeight = 110;
        int backX = Gdx.graphics.getWidth() / 2 - backWidth / 2;
        int backY = Gdx.graphics.getHeight() / 2 - backHeight / 2 - 150;

        // Bouton Back
        if (Gdx.input.getX() < backX + backWidth &&
                Gdx.input.getX() > backX &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < backY + backHeight &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > backY) {
            game.hudBatch.draw(backButtonSelected, backX, backY, backWidth, backHeight);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        } else {
            game.hudBatch.draw(backButton, backX, backY, backWidth, backHeight);
        }
        game.hudBatch.end();

        batcher.begin();

        scaledFont.draw(batcher, "Entrez votre nom d'utilisateur", (float) Gdx.graphics.getWidth() / 2 - 240, (float) Gdx.graphics.getHeight() - 350);
        scaledFont.draw(batcher, "Username : " + UsernameScreen.username + "|", (float) Gdx.graphics.getWidth() / 2 - 125, (float) Gdx.graphics.getHeight() / 2 + 60);
        scaledFont.draw(batcher, "Le nom d'utilisateur doit faire au moins 3 caractères", (float) Gdx.graphics.getWidth() / 2 - 450, (float) Gdx.graphics.getHeight() / 2);

        batcher.end();
    }

    @Override
    public void resize(int i, int i1) {

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
        scaledFont.dispose(); // Disposez de la nouvelle police lors de la destruction de l'écran
        background.dispose(); // Disposez de la texture de fond
        backButton.dispose(); // Disposez de la texture du bouton de retour
        backButtonSelected.dispose(); // Disposez de la texture du bouton de retour sélectionné
    }
}
