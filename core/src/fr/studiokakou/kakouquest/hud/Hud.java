package fr.studiokakou.kakouquest.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

import java.util.ArrayList;

public class Hud {
    Player player;

    ArrayList<Texture> healthBar = new ArrayList<>();
    Texture healthBarOutside;

    ArrayList<Texture> staminaBar = new ArrayList<>();

    int currentLevel;
    float hudSize;

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
