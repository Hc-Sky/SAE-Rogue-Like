package fr.studiokakou.kakouquest.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GetCoreProperties;
import fr.studiokakou.kakouquest.map.Floor;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;
import fr.studiokakou.kakouquest.item.Potion;

public class OnGroundPotion {
    public Potion potion;
    public Point pos;
    public boolean canInteract = false;
    public boolean toDelete = false;
    public OnGroundPotion toAdd;

    public String interactKey;
    public int interactKeyCode;
    public Animation<TextureRegion> interactKeyAnimation;

    /**
     * Constructor of the OnGroundPotion class
     *
     * @param pos    the pos
     * @param potion the potion
     */
    public OnGroundPotion(Point pos, Potion potion){
        this.pos = pos;
        this.potion = potion;

        this.interactKeyCode = GetCoreProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 1f);
    }

    /**
     * Refresh interact.
     *
     * @param player     the player
     * @param isClosest the is closest
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
     * Interact with the player to add the potion to the player's inventory
     *
     * @param player the player
     */
    public void interact(Player player) {
        if (this.canInteract) {
            Potion.PotionType potionType = this.potion.getType();
            int currentAmount = player.potions.getOrDefault(potionType, 0);
            int newAmount = currentAmount + this.potion.getAmount();
            player.potions.put(potionType, newAmount);
            System.out.println(player.potions);
            this.toDelete = true;
        }
    }

    /**
     * Draw the potion on the screen
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch){
        if (canInteract){
            TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
            batch.draw(currentKeyFrame, this.pos.x - ((float) this.potion.texture.getWidth() /2), this.pos.y + 20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
        }

        batch.draw(this.potion.texture, this.pos.x, this.pos.y);
    }
}
