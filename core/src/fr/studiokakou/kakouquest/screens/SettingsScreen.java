package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.TimeUtils;
import fr.studiokakou.kakouquest.GameSpace;
import fr.studiokakou.kakouquest.GetProperties;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.hud.Hud;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;
import static com.badlogic.gdx.Gdx.input;

public class SettingsScreen implements Screen{
    //interact var
    /**
     * Constants for the text textures
     */
    private final static int TEXT_WIDTH = Gdx.graphics.getWidth()/4;
    private final static int TEXT_HEIGHT = TEXT_WIDTH/6;
    /**
     * Coords for the text textures
     */
    int ecart = 50;
    int xposText = ((Gdx.graphics.getWidth() / 4)) - 180;
    int yposUp = Gdx.graphics.getHeight() - 150;
    int yposDown = yposUp- TEXT_HEIGHT - ecart;
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

    public SettingsScreen(GameSpace game){
        this.game = game;

        upText = new Texture("assets/window/up_text.png");
        downText = new Texture("assets/window/down_text.png");
        leftText = new Texture("assets/window/left_text.png");
        rightText = new Texture("assets/window/right_text.png");
        interactText = new Texture("assets/window/interact_text.png");
        dashText = new Texture("assets/window/dash_text.png");
        inventoryText = new Texture("assets/window/inventory_text.png");

        upKey = new Texture("assets/keys/animated/"+ Input.Keys.toString(GetProperties.getIntProperty("KEY_UP"))+".png");
        downKey = new Texture("assets/keys/animated/"+ Input.Keys.toString(GetProperties.getIntProperty("KEY_DOWN"))+".png");
        leftKey = new Texture("assets/keys/animated/"+ Input.Keys.toString(GetProperties.getIntProperty("KEY_LEFT"))+".png");
        rightKey = new Texture("assets/keys/animated/"+ Input.Keys.toString(GetProperties.getIntProperty("KEY_RIGHT"))+".png");
        interactionKey = new Texture("assets/keys/animated/"+ Input.Keys.toString(GetProperties.getIntProperty("KEY_INTERRACT"))+".png");
        dashKey = new Texture("assets/keys/animated/"+ Input.Keys.toString(GetProperties.getIntProperty("KEY_DASH"))+".png");
        inventoryKey = new Texture("assets/keys/animated/"+ Input.Keys.toString(GetProperties.getIntProperty("KEY_INVENTORY"))+".png");

        leftRegionUpKey = new TextureRegion(upKey, 0, 0, upKey.getWidth() / 2, upKey.getHeight());
        leftRegionDownKey = new TextureRegion(downKey, 0, 0, downKey.getWidth() / 2, downKey.getHeight());
        leftRegionLeftKey = new TextureRegion(leftKey, 0, 0, leftKey.getWidth() / 2, leftKey.getHeight());
        leftRegionRightKey = new TextureRegion(rightKey, 0, 0, rightKey.getWidth() / 2, rightKey.getHeight());
        leftRegionInteractionKey = new TextureRegion(interactionKey, 0, 0, interactionKey.getWidth() / 2, interactionKey.getHeight());
        leftRegionDashKey = new TextureRegion(dashKey, 0, 0, dashKey.getWidth() / 2, dashKey.getHeight());
        leftRegionInventoryKey = new TextureRegion(inventoryKey, 0, 0, inventoryKey.getWidth() / 2, inventoryKey.getHeight());


        backButton = new Texture("assets/buttons/back_button.png");


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        /*
          Echap pour quitter l'écran
         */
        if (input.isKeyPressed(Input.Keys.ESCAPE)){
            this.dispose();
            game.setScreen(new MenuScreen(game));
        }
        /*
          Initialisation de l'écran
         */
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.batch.draw(backButton, 30, 30, 230, 70);
        if (Gdx.input.getX() < 30 + 230 &&
                Gdx.input.getX() > 30 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < 30 + 70 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > 30) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }

        game.batch.draw(upText, xposText, yposUp, TEXT_WIDTH, TEXT_HEIGHT);
        game.batch.draw(downText, xposText, yposDown, TEXT_WIDTH, TEXT_HEIGHT);
        game.batch.draw(leftText, xposText, yposLeft, TEXT_WIDTH, TEXT_HEIGHT);
        game.batch.draw(rightText, xposText, yposRight, TEXT_WIDTH, TEXT_HEIGHT);
        game.batch.draw(interactText, xposText, yposInteract, TEXT_WIDTH, TEXT_HEIGHT);
        game.batch.draw(dashText, xposText, yposDash, TEXT_WIDTH, TEXT_HEIGHT);
        game.batch.draw(inventoryText, xposText, yposInventory, TEXT_WIDTH, TEXT_HEIGHT);

        game.batch.draw(leftRegionUpKey, xposText + 1000, yposUp, TEXT_HEIGHT, TEXT_HEIGHT);
        if (Gdx.input.getX() < xposText+1000 + TEXT_HEIGHT &&
                Gdx.input.getX() > xposText+1000 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposUp + TEXT_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposUp) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
        game.batch.draw(leftRegionDownKey, xposText + 1000, yposDown, TEXT_HEIGHT, TEXT_HEIGHT);
        if (Gdx.input.getX() < xposText+1000 + TEXT_HEIGHT &&
                Gdx.input.getX() > xposText+1000 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposDown + TEXT_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposDown) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
        game.batch.draw(leftRegionLeftKey, xposText + 1000, yposLeft, TEXT_HEIGHT, TEXT_HEIGHT);
        if (Gdx.input.getX() < xposText+1000 + TEXT_HEIGHT &&
                Gdx.input.getX() > xposText+1000 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposLeft + TEXT_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposLeft) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
        game.batch.draw(leftRegionRightKey, xposText + 1000, yposRight, TEXT_HEIGHT, TEXT_HEIGHT);
        if (Gdx.input.getX() < xposText+1000 + TEXT_HEIGHT &&
                Gdx.input.getX() > xposText+1000 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposRight + TEXT_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposRight) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
        game.batch.draw(leftRegionInteractionKey, xposText + 1000, yposInteract, TEXT_HEIGHT, TEXT_HEIGHT);
        if (Gdx.input.getX() < xposText+1000 + TEXT_HEIGHT &&
                Gdx.input.getX() > xposText+1000 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposInteract + TEXT_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposInteract) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
        game.batch.draw(leftRegionDashKey, xposText + 1000, yposDash, TEXT_HEIGHT, TEXT_HEIGHT);
        if (Gdx.input.getX() < xposText+1000 + TEXT_HEIGHT &&
                Gdx.input.getX() > xposText+1000 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposDash + TEXT_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposDash) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }
        game.batch.draw(leftRegionInventoryKey, xposText + 1000, yposInventory, TEXT_HEIGHT, TEXT_HEIGHT);
        if (Gdx.input.getX() < xposText+1000 + TEXT_HEIGHT &&
                Gdx.input.getX() > xposText+1000 &&
                Gdx.graphics.getHeight() - Gdx.input.getY() < yposInventory + TEXT_HEIGHT &&
                Gdx.graphics.getHeight() - Gdx.input.getY() > yposInventory) {
            if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {
                this.dispose();
                game.setScreen(new MenuScreen(game));
            }
        }


        game.batch.end();
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

    }
}
