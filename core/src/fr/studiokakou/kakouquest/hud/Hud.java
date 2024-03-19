package fr.studiokakou.kakouquest.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

import java.util.ArrayList;

/**
 * Le type Hud. Cette classe est utilisée pour créer un objet Hud.
 *
 * @version 1.0
 *
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
     * Constructeur de l'HUD.
     * Sert à créer un objet Hud.
     *
     * @param player       the player
     * @param currentLevel the current level
     * @param hudSizeMult  the hud size mult
     */
    public Hud(Player player, int currentLevel, float hudSizeMult){
        this.player = player;
        this.currentLevel = currentLevel;

        this.hudSize = hudSizeMult;

        //health bar textures
        this.healthBarOutside = new Texture("assets/hud/health/outside.png");
        for (int i = 6; i >= 1; i--) {
            this.healthBar.add(new Texture("assets/hud/health/"+i+".png"));
        }

        for (int i = 6; i >= 1; i--) {
            this.staminaBar.add(new Texture("assets/hud/stamina/"+i+".png"));
        }
    }

    /**
     * Dessine l'HUD.
     *
     * @param batch the batch
     */
    public void draw(SpriteBatch batch){
        int healthAmount = ((this.player.hp*6)/100) - 1;
        if (healthAmount < 0) {
            healthAmount=0;
        } else if (healthAmount==0 && this.player.hp>=0) {
            healthAmount=1;
        }
        batch.draw(this.healthBar.get(healthAmount), 100, Gdx.graphics.getHeight()-100, this.healthBar.get(0).getWidth()*this.hudSize, this.healthBar.get(0).getHeight()*this.hudSize);
        batch.draw(this.healthBarOutside, 100, Gdx.graphics.getHeight()-100, this.healthBarOutside.getWidth()*this.hudSize, this.healthBarOutside.getHeight()*this.hudSize);

        int staminaAmount = (((this.player.stamina+16)*6)/100) - 1;
        if (staminaAmount < 0) {
            staminaAmount=0;
        } else if (staminaAmount==0 && this.player.stamina>=0) {
            staminaAmount=1;
        }
        batch.draw(this.staminaBar.get(staminaAmount), 78, Gdx.graphics.getHeight()-100-this.staminaBar.get(0).getHeight()*this.hudSize/2, this.staminaBar.get(0).getWidth()*this.hudSize, this.staminaBar.get(0).getHeight()*this.hudSize);
    }
}
