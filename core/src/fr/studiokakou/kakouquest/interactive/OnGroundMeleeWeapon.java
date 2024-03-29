package fr.studiokakou.kakouquest.interactive;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.GetProperties;
import fr.studiokakou.kakouquest.map.Floor;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;

public class OnGroundMeleeWeapon {
    public MeleeWeapon meleeWeapon;

    public Point pos;

    boolean canInteract = false;
    public boolean toDelete = false;
    public OnGroundMeleeWeapon toAdd;


    //interact var
    String interactKey;
    int interactKeyCode;
    Animation<TextureRegion> interactKeyAnimation;

    public OnGroundMeleeWeapon(Point pos, MeleeWeapon meleeWeapon){
        this.pos = pos;
        this.meleeWeapon = meleeWeapon;

        this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 1f);

    }

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

    public void interact(Player player){
        if (this.canInteract){
            toAdd = new OnGroundMeleeWeapon(player.pos, player.currentWeapon);
            player.currentWeapon = this.meleeWeapon;
            player.currentWeapon.regen();
            this.toDelete=true;
        }
    }

    public void draw(SpriteBatch batch){

        if (canInteract){
            TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
            batch.draw(currentKeyFrame, this.pos.x - ((float) this.meleeWeapon.texture.getWidth() /2), this.pos.y+20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
        }

        batch.draw(this.meleeWeapon.texture, this.pos.x, this.pos.y);
    }

}
