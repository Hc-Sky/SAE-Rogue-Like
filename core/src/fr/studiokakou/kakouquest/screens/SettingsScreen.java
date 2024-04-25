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
import fr.studiokakou.kakouquest.GetProperties;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.hud.Hud;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.player.Camera;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;
import static com.badlogic.gdx.Gdx.input;

public class SettingsScreen implements Screen{
    /**
     * Constants for the text textures
     */
    private final static int TEXT_WIDTH = Gdx.graphics.getWidth()/4;
    private final static int TEXT_HEIGHT = TEXT_WIDTH/6;
    /**
     * Coords for the text textures
     */
    int ecart = 50;
    int xposText = Gdx.graphics.getWidth()/4 * 1;
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
