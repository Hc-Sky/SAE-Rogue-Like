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

import java.util.ArrayList;

/**
 * The type Chest.
 * This class is used to create a chest object.
 *
 * @version 1.0
 */
public class Chest {
    /**
     * The Pos.
     */
    public Point pos;
    /**
     * The Melee weapon loot.
     */
    public MeleeWeapon meleeWeaponLoot;

    /** Indicates if the chest is opened. */
    boolean isOpened=false;
    /** Indicates if the chest is being opened. */
    boolean isOpenning = false;
    /**
     * The Can interact.
     */
    public boolean canInteract = false;

    /**
     * The Closed.
     */
    public TextureRegion closed;
    /**
     * The Opened.
     */
    public TextureRegion opened;

    /**
     * The Openning animation.
     */
    public Animation<TextureRegion> openningAnimation;

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
     * The Frame cols.
     */
    public static final int FRAME_COLS = 1;
    /**
     * The Frame rows.
     */
    public static final int FRAME_ROWS = 3;

    /**
     * Instantiates a new Chest.
     *
     * @param pos          the pos
     * @param currentLevel the current level
     */
    public Chest(Point pos, int currentLevel){
        this.pos = pos;

        this.openningAnimation = Utils.getAnimation("assets/map/chest_open.png", FRAME_COLS, FRAME_ROWS);
        this.closed = this.openningAnimation.getKeyFrames()[0];
        this.opened = this.openningAnimation.getKeyFrames()[2];

        this.interactKeyCode = GetProperties.getIntProperty("KEY_INTERRACT");
        this.interactKey = Input.Keys.toString(this.interactKeyCode);

        this.interactKeyAnimation = Utils.getAnimationHorizontal("assets/keys/animated/"+this.interactKey+".png", 2, 1, 1f);

        this.meleeWeaponLoot = getRandomMeleeWeapon(currentLevel);
    }

    /**
     * Gets random melee weapon.
     *
     * @param currentLevel the current level
     * @return the random melee weapon
     */
    public MeleeWeapon getRandomMeleeWeapon(int currentLevel){
        ArrayList<Integer> randomRarity = new ArrayList<>();

        float tmp_current_level = (float) currentLevel/3;
        if (tmp_current_level<1){
            tmp_current_level=1;
        }

        for (int i = 1; i <= tmp_current_level; i++) {
            for (int j = 0; j <= tmp_current_level-i; j++) {
                if (MeleeWeapon.possibleMeleeWeapon.get(i) != null){
                    randomRarity.add(i);
                }
            }
        }

        int rarity = randomRarity.get(Utils.randint(0, randomRarity.size() - 1));
        ArrayList<MeleeWeapon> rarityMeleeWeapon = MeleeWeapon.possibleMeleeWeapon.get(rarity);

        while (rarityMeleeWeapon==null || rarityMeleeWeapon.isEmpty()){
            rarity = randomRarity.get(Utils.randint(0, randomRarity.size() - 1));
            rarityMeleeWeapon = MeleeWeapon.possibleMeleeWeapon.get(rarity);
        }

        return rarityMeleeWeapon.get(Utils.randint(0, rarityMeleeWeapon.size()-1)).getNew();
    }

    /**
     * Drop loot.
     */
    public void dropLoot(){
        Map.onGroundMeleeWeapons.add(new OnGroundMeleeWeapon(this.pos.add(0, -Floor.TEXTURE_HEIGHT), this.meleeWeaponLoot));
    }

    /**
     * Refresh interact.
     *
     * @param player     the player
     * @param isClosest  the is closest
     */
    public void refreshInteract(Player player, boolean isClosest){

        if (this.isOpened || this.isOpenning || !isClosest){
            this.canInteract=false;
            return;
        }

        if (this.canInteract && Gdx.input.isKeyJustPressed(this.interactKeyCode)){
            this.isOpened = true;
            this.dropLoot();
        }

        if (Utils.getDistance(this.pos, player.pos) <= 40){
            this.canInteract = true;
        } else if (isClosest){
            this.canInteract = false;
        }
    }

    /**
     * Draw.
     *
     * @param batch the batch
     */
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