package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents a wall in the map.
 */
public class Wall {
    /** The position of the wall. */
    Point pos;

    /** The texture of the wall. */
    Texture texture;

    /**
     * Constructs a new Wall object.
     *
     * @param pos        The position of the wall.
     * @param assetPath  The path to the texture asset.
     */
    public Wall(Point pos, String assetPath){
        this.pos = pos;
        this.texture = new Texture(assetPath);
    }

    /**
     * Draws the wall on the screen.
     *
     * @param batch The sprite batch to draw the wall.
     */
    public void draw(SpriteBatch batch){
        batch.draw(this.texture, this.pos.x*Floor.TEXTURE_WIDTH, this.pos.y*Floor.TEXTURE_HEIGHT);
    }
}
