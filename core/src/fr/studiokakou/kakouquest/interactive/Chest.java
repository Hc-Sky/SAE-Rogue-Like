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

public class Chest {
    Point pos;

    boolean isOpened=false;
    boolean isOpenning = false;
    boolean canInteract = false;

    TextureRegion closed;
    TextureRegion opened;

    Animation<TextureRegion> openningAnimation;

    //interact var
    String interactKey;
    int interactKeyCode;
    Animation<TextureRegion> interactKeyAnimation;

    float animationStateTime;

    static final int FRAME_COLS = 1;
    static final int FRAME_ROWS = 3;

    public Chest(Point pos, int currentLevel){
        this.pos = pos;

        this.openningAnimation = Utils.getAnimation("assets/map/chest_open.png", FRAME_COLS, FRAME_ROWS);
        this.closed = this.openningAnimation.getKeyFrames()[0];
        this.opened = this.openningAnimation.getKeyFrames()[2];

        this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 40f);
    }

    public void dropLoot(){

    }

    public void refreshInteract(Player player){
        if (this.isOpened || this.isOpenning){
            this.canInteract=false;
            return;
        }

        if (this.canInteract && Gdx.input.isKeyJustPressed(this.interactKeyCode)){
            this.isOpened = true;
            this.dropLoot();
        }

        if (Utils.getDistance(this.pos, player.pos) <= 40){
            this.canInteract = true;
        } else {
            this.canInteract = false;
        }
    }

    public void draw(SpriteBatch batch){
        if (canInteract && !this.isOpened && !this.isOpenning){
            TextureRegion currentKeyFrame = this.interactKeyAnimation.getKeyFrame(InGameScreen.stateTime, true);
            batch.draw(currentKeyFrame, this.pos.x, this.pos.y+20, Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
        }

        if (! isOpened){
            batch.draw(this.closed, this.pos.x, this.pos.y);
        }
        else {
            batch.draw(this.opened, this.pos.x, this.pos.y);
        }
    }
}
