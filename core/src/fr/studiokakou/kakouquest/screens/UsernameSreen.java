package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.GameSpace;


public class UsernameSreen implements Screen {

    public static String username = "guest";

    GameSpace gameSpace;

    SpriteBatch batcher;

    public UsernameSreen(GameSpace gameSpace) {
        this.gameSpace = gameSpace;
        batcher = gameSpace.batch;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override public boolean keyUp (int keycode) {
                if (keycode == Input.Keys.ENTER && username.length() > 3 )  {
                    gameSpace.setScreen(new AvatarChoiceScreen(gameSpace));
                } else if (keycode == Input.Keys.BACKSPACE) {
                    if (UsernameSreen.username.length() > 0) {
                        UsernameSreen.username = UsernameSreen.username.substring(0, UsernameSreen.username.length() - 1);
                    }
                } else if (keycode == Input.Keys.SPACE) {
                    UsernameSreen.username += " ";
                } else if (username.length() < 20){
                    UsernameSreen.username += Input.Keys.toString(keycode).toLowerCase();
                }
                return true;
            }
        });
    }

    @Override
    public void render(float v) {
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batcher.begin();

        gameSpace.font.draw(batcher, "Entrez votre nom d'utilisateur", (float) Gdx.graphics.getWidth() /2 - 85, (float) Gdx.graphics.getHeight() - 200);

        gameSpace.font.draw(batcher, "Username : "+UsernameSreen.username+"|", (float) Gdx.graphics.getWidth() /2 - 50, (float) Gdx.graphics.getHeight() /2);
        gameSpace.font.draw(batcher, "Username should be at least 3 characters long", (float) Gdx.graphics.getWidth() /2 - 140, (float) Gdx.graphics.getHeight() /2 - 40);

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

    }
}
