package fr.studiokakou.kakouquest.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;

import java.util.ArrayList;

/**
 * The type Hud. This class is used to create a Hud object.
 *
 * @version 1.0
 *
 */
public class Hud {
    /**
     * The Player.
     */
    Player player;

    /**
     * The Health bar.
     */
    ArrayList<Texture> healthBar = new ArrayList<>();
    /**
     * The Health bar outside.
     */
    Texture healthBarOutside;

    /**
     * The Stamina bar.
     */
    ArrayList<Texture> staminaBar = new ArrayList<>();

    /**
     * The Current level.
     */
    int currentLevel;
    /**
     * The Hud size.
     */
    float hudSize;

    /**
     * Constructor of the HUD.
     * Used to create a Hud object.
     *
     * @param player       the player
     * @param currentLevel the current level
     * @param hudSizeMult  the hud size mult
     */
    public Hud(Player player, int currentLevel, float hudSizeMult){
        this.player = player;
        this.currentLevel = currentLevel;

        this.hudSize = hudSizeMult;

        this.healthBarOutside = new Texture("assets/hud/health/outside.png");
        for (int i = 6; i >= 1; i--) {
            this.healthBar.add(new Texture("assets/hud/health/"+i+".png"));
        }

        for (int i = 6; i >= 1; i--) {
            this.staminaBar.add(new Texture("assets/hud/stamina/"+i+".png"));
        }
    }

    /**
     * Draws the HUD.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch){
        int healthAmount = getHealthAmount();

        Point healthBarPos = new Point(100, Gdx.graphics.getHeight()-100);
        Point staminaBarPos = new Point(78, Gdx.graphics.getHeight()-120);

        if (healthAmount>=0){
            batch.draw(this.healthBar.get(healthAmount), healthBarPos.x, healthBarPos.y, this.healthBar.get(0).getWidth()*this.hudSize, this.healthBar.get(0).getHeight()*this.hudSize);
        }
        batch.draw(this.healthBarOutside, healthBarPos.x, healthBarPos.y, this.healthBarOutside.getWidth()*this.hudSize, this.healthBarOutside.getHeight()*this.hudSize);

        batch.draw(this.staminaBar.get(getStaminaAmount()), staminaBarPos.x, staminaBarPos.y, this.staminaBar.get(0).getWidth()*this.hudSize, this.staminaBar.get(0).getHeight()*this.hudSize);
    }

    private int getStaminaAmount() {
        int staminaAmount;
        if ((this.player.stamina*100)/this.player.max_stamina >= 98) {
            staminaAmount=5;
        } else if ((this.player.stamina*100)/this.player.max_stamina >= 70) {
            staminaAmount=4;
        } else if ((this.player.stamina*100)/this.player.max_stamina >= 50) {
            staminaAmount=3;
        }else if ((this.player.stamina*100)/this.player.max_stamina >= 30) {
            staminaAmount=2;
        }else if ((this.player.stamina*100)/this.player.max_stamina > 4) {
            staminaAmount=1;
        }else {
            staminaAmount=0;
        }
        return staminaAmount;
    }

    private int getHealthAmount() {
        int healthAmount;
        if ((this.player.hp*100)/this.player.max_hp >= 98) {
            healthAmount=5;
        } else if ((this.player.hp*100)/this.player.max_hp >= 83) {
            healthAmount=4;
        } else if ((this.player.hp*100)/this.player.max_hp >= 66) {
            healthAmount=3;
        }else if ((this.player.hp*100)/this.player.max_hp >= 49) {
            healthAmount=2;
        }else if ((this.player.hp*100)/this.player.max_hp >= 32) {
            healthAmount=1;
        }else if ((this.player.hp*100)/this.player.max_hp > 15){
            healthAmount=0;
        } else {
            healthAmount=-1;
        }
        return healthAmount;
    }
}
