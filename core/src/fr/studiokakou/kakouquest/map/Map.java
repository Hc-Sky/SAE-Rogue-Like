package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Map {
    public ArrayList<Floor> floors = new ArrayList<Floor>();

    public int map_height;
    public int map_width;

    public Map(int width, int height){
        this.map_height = height;
        this.map_width = width;

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
    }
}
