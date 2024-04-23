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
    GameSpace game;
    Texture backButton;

    public SettingsScreen(GameSpace game){
        this.game = game;
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
