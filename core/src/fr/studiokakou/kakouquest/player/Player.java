package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.map.Point;

public class Player {

    //player pos
    Point pos;

    //player infos
    public String name;
    public int hp;
    public int strength;
    public float speed;

    //player texture & sprite
    Texture texture;
    public Sprite sprite;

    public Player(float x, float y, String name){

        this.pos = new Point(x, y);
        this.name = name;

        this.texture = new Texture("assets/knight_f_run_anim_f0.png");
        this.sprite = new Sprite(this.texture);

        this.move(0, 0);

        //default values
        this.hp=100;
        this.strength=10;
        this.speed=50f;
    }

    public void move(float x, float y){
        this.pos = this.pos.add(x, y);
        this.sprite.setX(this.pos.x);
        this.sprite.setY(this.pos.y);
    }

    public void getKeyboardMove(){
        if (Gdx.input.isKeyPressed(Input.Keys.W)){
            this.move(0, 1);
        } if (Gdx.input.isKeyPressed(Input.Keys.S)){
            this.move(0, -1);
        } if (Gdx.input.isKeyPressed(Input.Keys.A)){
            this.move(-1, 0);
        } if (Gdx.input.isKeyPressed(Input.Keys.D)){
            this.move(1, 0);
        }
    }

    public void draw(SpriteBatch batch){
        this.sprite.draw(batch);
    }
}
