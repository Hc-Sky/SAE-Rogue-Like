package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Test;
import fr.studiokakou.kakouquest.player.Player;

import java.util.ArrayList;

public class Map {
    public ArrayList<Floor> floors = new ArrayList<>();
    public static ArrayList<Test> tests = new ArrayList<>();

    public int map_height;
    public int map_width;

    public Map(int width, int height){
        this.map_height = height;
        this.map_width = width;

        Map.tests.add(new Test(120, 120, "test1"));
        Map.tests.add(new Test(140, 140, "test2"));

        for (int i = 0; i < this.map_height; i++) {
            for (int j = 0; j < this.map_width; j++) {
                this.floors.add(new Floor(i*Floor.TEXTURE_WIDTH, j*Floor.TEXTURE_HEIGHT));
            }
        }
    }

    public void drawMap(SpriteBatch batch){
        for (Floor f : this.floors){
            batch.draw(f.texture, f.pos.x, f.pos.y);
        }

        for (Test t : this.tests) {
            t.sprite.draw(batch);
        }
    }

    public static void clearAttackedPlayers(Player player){
        for (Test t : tests){
            t.player_hitted.remove(player.name);
        }
    }
}
