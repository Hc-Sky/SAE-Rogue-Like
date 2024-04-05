package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Represents a wall object in the game map.
 */
public class Wall {
    /** The position of the wall on the map. */
    Point pos;

    /** The texture used to render the wall. */
    Texture texture;

    /**
     * Constructs a new wall object.
     *
     * @param pos         The position of the wall on the map.
     * @param assetPath   The file path to the texture asset used for rendering the wall.
     */
    public Wall(Point pos, String assetPath){
        this.pos = pos;
        this.texture = new Texture(assetPath);
    }

    /**
     * Draws the wall on the screen.
     *
     * @param batch   The sprite batch used for rendering.
     */
    public void draw(SpriteBatch batch){
        batch.draw(this.texture, this.pos.x*Floor.TEXTURE_WIDTH, this.pos.y*Floor.TEXTURE_HEIGHT);
    }
}
