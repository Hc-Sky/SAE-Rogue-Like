package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.GetCoreProperties;

import static com.badlogic.gdx.Gdx.input;

public class SettingsScreen implements Screen {
    private final static int TEXT_HEIGHT = Gdx.graphics.getWidth() / 24; // Adjusted height based on overall width
    private final static int LETTER_WIDTH = Gdx.graphics.getWidth() / 40; // Base width per letter
    int ecart = 50;
    int xposText = ((Gdx.graphics.getWidth() / 4)) - 100;
    int yposUp = Gdx.graphics.getHeight() - 150;
    int yposDown = yposUp - TEXT_HEIGHT - ecart;
    int yposLeft = yposDown - TEXT_HEIGHT - ecart;
    int yposRight = yposLeft - TEXT_HEIGHT - ecart;
    int yposInteract = yposRight - TEXT_HEIGHT - ecart;
    int yposDash = yposInteract - TEXT_HEIGHT - ecart;
    int yposInventory = yposDash - TEXT_HEIGHT - ecart;

    GameSpace game;

    Texture upText;
    Texture downText;
    Texture leftText;
    Texture rightText;
    Texture interactText;
    Texture dashText;
    Texture inventoryText;

    Texture upKey;
    Texture downKey;
    Texture leftKey;
    Texture rightKey;
    Texture interactionKey;
    Texture dashKey;
    Texture inventoryKey;

    private TextureRegion leftRegionUpKey;
    private TextureRegion leftRegionDownKey;
    private TextureRegion leftRegionLeftKey;
    private TextureRegion leftRegionRightKey;
    private TextureRegion leftRegionInteractionKey;
    private TextureRegion leftRegionDashKey;
    private TextureRegion leftRegionInventoryKey;

    Texture backButton;
    Texture backButtonSelected;
    Texture resumeButton;
    Texture resumeButtonSelected;

    // Variable to track which key is being modified
    private String keyBeingModified = null;

    // Image de fond
    Texture background;

    public SettingsScreen(GameSpace game) {
        this.game = game;

        upText = new Texture("assets/window/up_text.png");
        downText = new Texture("assets/window/down_text.png");
        leftText = new Texture("assets/window/left_text.png");
        rightText = new Texture("assets/window/right_text.png");
        interactText = new Texture("assets/window/interact_text.png");
        dashText = new Texture("assets/window/dash_text.png");
        inventoryText = new Texture("assets/window/inventory_text.png");

        upKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_UP")) + ".png");
        downKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_DOWN")) + ".png");
        leftKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_LEFT")) + ".png");
        rightKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_RIGHT")) + ".png");
        interactionKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_INTERRACT")) + ".png");
        dashKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_DASH")) + ".png");
        inventoryKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_INVENTORY")) + ".png");

        leftRegionUpKey = new TextureRegion(upKey, 0, 0, upKey.getWidth() / 2, upKey.getHeight());
        leftRegionDownKey = new TextureRegion(downKey, 0, 0, downKey.getWidth() / 2, downKey.getHeight());
        leftRegionLeftKey = new TextureRegion(leftKey, 0, 0, leftKey.getWidth() / 2, leftKey.getHeight());
        leftRegionRightKey = new TextureRegion(rightKey, 0, 0, rightKey.getWidth() / 2, rightKey.getHeight());
        leftRegionInteractionKey = new TextureRegion(interactionKey, 0, 0, interactionKey.getWidth() / 2, interactionKey.getHeight());
        leftRegionDashKey = new TextureRegion(dashKey, 0, 0, dashKey.getWidth() / 2, dashKey.getHeight());
        leftRegionInventoryKey = new TextureRegion(inventoryKey, 0, 0, inventoryKey.getWidth() / 2, inventoryKey.getHeight());

        backButton = new Texture("assets/buttons/back_button.png");
        backButtonSelected = new Texture("assets/buttons/back_button_selected.png");
        resumeButton = new Texture("assets/buttons/resume_button.png");
        resumeButtonSelected = new Texture("assets/buttons/resume_button_selected.png");

        background = new Texture("assets/window/settings_background.png"); // Charger l'image de fond

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if (input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            game.setScreen(new MenuScreen(game));
        }

        Gdx.gl.glClearColor(34 / 255f, 34 / 255f, 34 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.hudBatch.begin();

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

        // Dessiner le bouton Back
        if (Gdx.input.getX() < 30 + 230 &&
                Gdx.input.getX() > 30 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < 30 + 70 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > 30) {
            game.hudBatch.draw(backButtonSelected, 30, 30, 230, 110);
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        } else {
            game.hudBatch.draw(backButton, 30, 30, 230, 110);
        }

        // Dessiner le bouton Resume si le jeu est en pause
        if (game.isPaused()) { // Vérifier si le jeu est en pause
            int resumeButtonX = 280; // Ajuster la position X pour le bouton Resume
            int resumeButtonY = 30; // Même position Y que le bouton Back
            int resumeButtonWidth = 230; // Largeur du bouton Resume
            int resumeButtonHeight = 110; // Hauteur du bouton Resume

            if (Gdx.input.getX() < resumeButtonX + resumeButtonWidth &&
                    Gdx.input.getX() > resumeButtonX &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() < resumeButtonY + resumeButtonHeight &&
                    Gdx.graphics.getHeight() - Gdx.input.getY() > resumeButtonY) {
                game.hudBatch.draw(resumeButtonSelected, resumeButtonX, resumeButtonY, resumeButtonWidth, resumeButtonHeight);
                if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                    game.setScreen(game.previousScreen);
                    if (game.previousScreen instanceof InGameScreen) {
                        ((InGameScreen) game.previousScreen).resumeGame();
                    }
                }
            } else {
                game.hudBatch.draw(resumeButton, resumeButtonX, resumeButtonY, resumeButtonWidth, resumeButtonHeight);
            }
        }

        // Draw the text and keys
        drawTextAndKeys();

        // Check for clicks and update the keyBeingModified variable
        checkForClicks();

        game.hudBatch.end();

        // If a key is being modified, capture the next key press
        if (keyBeingModified != null) {
            int newKey = captureKeyPress();
            if (newKey != -1) {
                updateKeyBinding(keyBeingModified, newKey);
                keyBeingModified = null;
            }
        }
    }

    private void drawTextAndKeys() {
        // Dynamically calculate text widths
        int widthUp = "Monter".length() * LETTER_WIDTH;
        int widthDown = "Descendre".length() * LETTER_WIDTH;
        int widthLeft = "Gauche".length() * LETTER_WIDTH;
        int widthRight = "Droite".length() * LETTER_WIDTH;
        int widthInteract = "Interagir".length() * LETTER_WIDTH;
        int widthDash = "Dash".length() * LETTER_WIDTH;
        int widthInventory = "Inventaire".length() * LETTER_WIDTH;

        game.hudBatch.draw(upText, xposText, yposUp, widthUp, TEXT_HEIGHT);
        game.hudBatch.draw(downText, xposText, yposDown, widthDown, TEXT_HEIGHT);
        game.hudBatch.draw(leftText, xposText, yposLeft, widthLeft, TEXT_HEIGHT);
        game.hudBatch.draw(rightText, xposText, yposRight, widthRight, TEXT_HEIGHT);
        game.hudBatch.draw(interactText, xposText, yposInteract, widthInteract, TEXT_HEIGHT);
        game.hudBatch.draw(dashText, xposText, yposDash, widthDash, TEXT_HEIGHT);
        game.hudBatch.draw(inventoryText, xposText, yposInventory, widthInventory, TEXT_HEIGHT);

        game.hudBatch.draw(leftRegionUpKey, xposText + 1000, yposUp, TEXT_HEIGHT, TEXT_HEIGHT);
        game.hudBatch.draw(leftRegionDownKey, xposText + 1000, yposDown, TEXT_HEIGHT, TEXT_HEIGHT);
        game.hudBatch.draw(leftRegionLeftKey, xposText + 1000, yposLeft, TEXT_HEIGHT, TEXT_HEIGHT);
        game.hudBatch.draw(leftRegionRightKey, xposText + 1000, yposRight, TEXT_HEIGHT, TEXT_HEIGHT);
        game.hudBatch.draw(leftRegionInteractionKey, xposText + 1000, yposInteract, TEXT_HEIGHT, TEXT_HEIGHT);
        game.hudBatch.draw(leftRegionDashKey, xposText + 1000, yposDash, TEXT_HEIGHT, TEXT_HEIGHT);
        game.hudBatch.draw(leftRegionInventoryKey, xposText + 1000, yposInventory, TEXT_HEIGHT, TEXT_HEIGHT);
    }

    private void checkForClicks() {
        if (isKeyClicked(xposText + 1000, yposUp, TEXT_HEIGHT, TEXT_HEIGHT)) {
            keyBeingModified = "KEY_UP";
        } else if (isKeyClicked(xposText + 1000, yposDown, TEXT_HEIGHT, TEXT_HEIGHT)) {
            keyBeingModified = "KEY_DOWN";
        } else if (isKeyClicked(xposText + 1000, yposLeft, TEXT_HEIGHT, TEXT_HEIGHT)) {
            keyBeingModified = "KEY_LEFT";
        } else if (isKeyClicked(xposText + 1000, yposRight, TEXT_HEIGHT, TEXT_HEIGHT)) {
            keyBeingModified = "KEY_RIGHT";
        } else if (isKeyClicked(xposText + 1000, yposInteract, TEXT_HEIGHT, TEXT_HEIGHT)) {
            keyBeingModified = "KEY_INTERRACT";
        } else if (isKeyClicked(xposText + 1000, yposDash, TEXT_HEIGHT, TEXT_HEIGHT)) {
            keyBeingModified = "KEY_DASH";
        } else if (isKeyClicked(xposText + 1000, yposInventory, TEXT_HEIGHT, TEXT_HEIGHT)) {
            keyBeingModified = "KEY_INVENTORY";
        }
    }

    private boolean isKeyClicked(int x, int y, int width, int height) {
        return Gdx.input.getX() < x + width && Gdx.input.getX() > x &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < y + height &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > y &&
                Gdx.input.isButtonJustPressed(Input.Buttons.LEFT);
    }

    private int captureKeyPress() {
        for (int i = 0; i < Input.Keys.MAX_KEYCODE; i++) {
            if (input.isKeyPressed(i)) {
                return i;
            }
        }
        return -1;
    }

    private void updateKeyBinding(String key, int newKey) {
        GetCoreProperties.setIntProperty(key, newKey);
        reloadKeyTextures();
    }

    private void reloadKeyTextures() {
        upKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_UP")) + ".png");
        downKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_DOWN")) + ".png");
        leftKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_LEFT")) + ".png");
        rightKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_RIGHT")) + ".png");
        interactionKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_INTERRACT")) + ".png");
        dashKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_DASH")) + ".png");
        inventoryKey = new Texture("assets/keys/animated/" + Input.Keys.toString(GetCoreProperties.getIntProperty("KEY_INVENTORY")) + ".png");

        leftRegionUpKey = new TextureRegion(upKey, 0, 0, upKey.getWidth() / 2, upKey.getHeight());
        leftRegionDownKey = new TextureRegion(downKey, 0, 0, downKey.getWidth() / 2, downKey.getHeight());
        leftRegionLeftKey = new TextureRegion(leftKey, 0, 0, leftKey.getWidth() / 2, leftKey.getHeight());
        leftRegionRightKey = new TextureRegion(rightKey, 0, 0, rightKey.getWidth() / 2, rightKey.getHeight());
        leftRegionInteractionKey = new TextureRegion(interactionKey, 0, 0, interactionKey.getWidth() / 2, interactionKey.getHeight());
        leftRegionDashKey = new TextureRegion(dashKey, 0, 0, dashKey.getWidth() / 2, dashKey.getHeight());
        leftRegionInventoryKey = new TextureRegion(inventoryKey, 0, 0, inventoryKey.getWidth() / 2, inventoryKey.getHeight());
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
    }

    @Override
    public void dispose() {
        upText.dispose();
        downText.dispose();
        leftText.dispose();
        rightText.dispose();
        interactText.dispose();
        dashText.dispose();
        inventoryText.dispose();
        upKey.dispose();
        downKey.dispose();
        leftKey.dispose();
        rightKey.dispose();
        interactionKey.dispose();
        dashKey.dispose();
        inventoryKey.dispose();
        backButton.dispose();
        resumeButton.dispose();
    }
}
