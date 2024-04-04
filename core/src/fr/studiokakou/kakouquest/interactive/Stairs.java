package fr.studiokakou.kakouquest.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GetProperties;
import fr.studiokakou.kakouquest.map.Floor;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;

/**
 * Represents a set of stairs that the player can interact with to move to the next level.
 */
public class Stairs {
    /** The position of the stairs. */
    public Point pos;

    /** Indicates if the stairs can be interacted with. */
    boolean canInteract = false;

    /** Texture for the stairs. */
    Texture texture;

    /** Reference to the in-game screen. */
    InGameScreen gameScreen;

    /** Key for interacting with the stairs. */
    String interactKey;
    /** Key code for interacting with the stairs. */
    int interactKeyCode;
    /** Animation for the interact key. */
    Animation<TextureRegion> interactKeyAnimation;

    /**
     * Constructs a set of stairs at the specified position.
     * @param pos The position of the stairs.
     * @param gameScreen The in-game screen object.
     */
    public Stairs(Point pos, InGameScreen gameScreen){
        this.pos = pos;
        this.gameScreen = gameScreen;

        this.texture = new Texture("assets/map/floor_ladder.png");

        this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 1f);
    }

    /**
     * Refreshes the interaction state of the stairs.
     * @param player The player object.
     * @param isClosest Indicates if the stairs are the closest to the player.
     */
    public void refreshInteract(Player player, boolean isClosest){

        if (this.canInteract && Gdx.input.isKeyJustPressed(this.interactKeyCode)){
            this.interact();
        }

        if (Utils.getDistance(this.pos, player.pos) <= 40 &&  isClosest){
            this.canInteract = true;
        } else {
            this.canInteract = false;
        }
    }

    /**
     * Handles the interaction with the stairs, advancing to the next level.
     */
    public void interact(){
        if (this.canInteract){
            this.gameScreen.nextLevel();
        }
    }

    /**
     * Draws the stairs.
     * @param batch The sprite batch to draw with.
     */
    public void draw(SpriteBatch batch){
        if (canInteract){
            TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
            batch.draw(currentKeyFrame, this.pos.x, this.pos.y+20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
        }

        batch.draw(this.texture, this.pos.x, this.pos.y);
    }
}
