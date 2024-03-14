package fr.studiokakou.kakouquest.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.GameSpace;

public class InGameScreen implements Screen {

    GameSpace game;
    SpriteBatch batch;
    Sprite exapleSprite;

    public InGameScreen(GameSpace game){
        this.game=game;
        this.batch = game.batch;
    }

    @Override
    public void show() {
        Texture texture = new Texture("assets/knight_f_run_anim_f0.png");
        this.exapleSprite = new Sprite(texture, texture.getWidth()*5, texture.getHeight()*5);
        this.exapleSprite.setY(100);
        this.exapleSprite.setX(100);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(34/255f, 34/255f, 34/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            exapleSprite.setY(exapleSprite.getY()+1);
        }else  if (Gdx.input.isKeyPressed(Input.Keys.S)){
            exapleSprite.setY(exapleSprite.getY()-1);
        }else  if (Gdx.input.isKeyPressed(Input.Keys.A)){
            exapleSprite.setX(exapleSprite.getX()-1);
        }else  if (Gdx.input.isKeyPressed(Input.Keys.D)){
            exapleSprite.setX(exapleSprite.getX()+1);
        }

        batch.begin();

        this.exapleSprite.draw(batch);

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

    }

    @Override
    public void dispose() {

    }
}
