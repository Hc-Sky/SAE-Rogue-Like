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
 * The type On ground melee weapon.
 * This class is used to create an on-ground melee weapon object.
 *
 * @version 1.0
 */
public class OnGroundMeleeWeapon {
    /**
     * The Melee weapon.
     */
    public MeleeWeapon meleeWeapon;

    /**
     * The Pos.
     */
    public Point pos;

    /**
     * The Can interact.
     */
    public boolean canInteract = false;
    /**
     * The To delete.
     */
    public boolean toDelete = false;
    /**
     * The To add.
     */
    public OnGroundMeleeWeapon toAdd;


    //interact var
    /**
     * The Interact key.
     */
    public String interactKey;
    /**
     * The Interact key code.
     */
    public int interactKeyCode;
    /**
     * The Interact key animation.
     */
    public Animation<TextureRegion> interactKeyAnimation;

    /**
     * Instantiates a new On ground melee weapon.
     *
     * @param pos          the pos
     * @param meleeWeapon  the melee weapon
     */
    public OnGroundMeleeWeapon(Point pos, MeleeWeapon meleeWeapon){
        this.pos = pos;
        this.meleeWeapon = meleeWeapon;

        this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 1f);
    }

    /**
     * Refresh interact.
     *
     * @param player     the player
     * @param isClosest  the is closest
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
     * Interact.
     *
     * @param player  the player
     */
    public void interact(Player player){
        if (this.canInteract){
            toAdd = new OnGroundMeleeWeapon(player.pos, player.currentWeapon);
            player.currentWeapon = this.meleeWeapon;
            this.toDelete=true;
        }
    }

    /**
     * Draw.
     *
     * @param batch  the batch
     */
    public void draw(SpriteBatch batch){

        if (canInteract){
            TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
            batch.draw(currentKeyFrame, this.pos.x - ((float) this.meleeWeapon.texture.getWidth() /2), this.pos.y+20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
        }

        batch.draw(this.meleeWeapon.texture, this.pos.x, this.pos.y);
    }

}
