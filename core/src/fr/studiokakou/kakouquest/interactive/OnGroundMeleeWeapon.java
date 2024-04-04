package fr.studiokakou.kakouquest.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GetProperties;
import fr.studiokakou.kakouquest.map.Floor;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;

/**
 * Represents a melee weapon on the ground that can be picked up by the player.
 */
public class OnGroundMeleeWeapon {
    /** The melee weapon object. */
    public MeleeWeapon meleeWeapon;

    /** The position of the melee weapon on the ground. */
    public Point pos;

    /** Indicates if the melee weapon can be interacted with. */
    boolean canInteract = false;
    /** Indicates if the melee weapon should be deleted. */
    public boolean toDelete = false;
    /** The melee weapon to be added to the player's inventory. */
    public OnGroundMeleeWeapon toAdd;

    /** Key for interacting with the melee weapon. */
    String interactKey;
    /** Key code for interacting with the melee weapon. */
    int interactKeyCode;
    /** Animation for the interact key. */
    Animation<TextureRegion> interactKeyAnimation;

    /**
     * Constructs a melee weapon on the ground at the given position.
     * @param pos The position of the melee weapon.
     * @param meleeWeapon The melee weapon object.
     */
    public OnGroundMeleeWeapon(Point pos, MeleeWeapon meleeWeapon){
        this.pos = pos;
        this.meleeWeapon = meleeWeapon;

        this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 1f);
    }

    /**
     * Refreshes the interaction state of the melee weapon.
     * @param player The player object.
     * @param isClosest Indicates if the melee weapon is the closest to the player.
     */
    public void refreshInteract(Player player, boolean isClosest){
        if (this.canInteract && Gdx.input.isKeyJustPressed(this.interactKeyCode)){
            this.interact(player);
        }

        if (Utils.getDistance(this.pos, player.pos) <= 40 &&  isClosest){
            this.canInteract = true;
        } else {
            this.canInteract = false;
        }
    }

    /**
     * Handles the interaction of the melee weapon with the player.
     * @param player The player object.
     */
    public void interact(Player player){
        if (this.canInteract){
            toAdd = new OnGroundMeleeWeapon(player.pos, player.currentWeapon);
            player.currentWeapon = this.meleeWeapon;
            this.toDelete=true;
        }
    }

    /**
     * Draws the melee weapon on the ground.
     * @param batch The sprite batch to draw with.
     */
    public void draw(SpriteBatch batch){

        if (canInteract){
            TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
            batch.draw(currentKeyFrame, this.pos.x - ((float) this.meleeWeapon.texture.getWidth() /2), this.pos.y+20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
        }

        batch.draw(this.meleeWeapon.texture, this.pos.x, this.pos.y);
    }

}
