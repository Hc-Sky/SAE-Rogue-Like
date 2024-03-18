package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;

import java.util.ArrayList;

public class Test {
    Point pos;
    float height;
    float width;

    String name;

    Texture texture;
    public Sprite sprite;

    public ArrayList<String> player_hitted = new ArrayList<>();

    public Test(float x, float y, String name){
        this.name = name;
        this.pos = new Point(x, y);
        this.texture = new Texture("assets/map/skull.png");

        this.height = texture.getHeight();
        this.width = texture.getWidth();

        this.sprite = new Sprite(this.texture);
        this.sprite.setX(this.pos.x);
        this.sprite.setY(this.pos.y);
    }

    public void hit(Player player){
        if (! this.player_hitted.contains(player.name)){
            System.out.println(this.name+" got hit by "+player.name);
            this.player_hitted.add(player.name);
        }
    }
}
