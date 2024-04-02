package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Wall {
    Point pos;

    Texture texture;

    public Wall(Point pos, String assetPath){
        this.pos = pos;

        this.texture = new Texture(assetPath);
    }

    public void draw(SpriteBatch batch){
        batch.draw(this.texture, this.pos.x*Floor.TEXTURE_WIDTH, this.pos.y*Floor.TEXTURE_HEIGHT);
    }
}
