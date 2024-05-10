package fr.studiokakou.kakouquest.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.item.Potion;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Pixmap;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Le type Hud. Cette classe est utilisée pour créer un objet Hud.
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

    BitmapFont font;

    /**
     * The SnapeRenderer
     */
    private ShapeRenderer shapeRenderer;

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

        shapeRenderer = new ShapeRenderer();
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
        int healthAmount = getHealthAmount();

        Point healthBarPos = new Point(100, Gdx.graphics.getHeight()-100);
        Point staminaBarPos = new Point(78, Gdx.graphics.getHeight()-120);
        Point defaultWeaponIconPos = new Point(Gdx.graphics.getWidth() - 110, 50);
        Point weaponIcon1Pos = new Point(Gdx.graphics.getWidth() - 110, 180);
        Point weaponIcon2Pos = new Point(Gdx.graphics.getWidth() - 110, 310);
        Point weaponIcon3Pos = new Point(Gdx.graphics.getWidth() - 110, 440);


        if (healthAmount>=0){
            batch.draw(this.healthBar.get(healthAmount), healthBarPos.x, healthBarPos.y, this.healthBar.get(0).getWidth()*this.hudSize, this.healthBar.get(0).getHeight()*this.hudSize);
        }
        batch.draw(this.healthBarOutside, healthBarPos.x, healthBarPos.y, this.healthBarOutside.getWidth()*this.hudSize, this.healthBarOutside.getHeight()*this.hudSize);

        batch.draw(this.staminaBar.get(getStaminaAmount()), staminaBarPos.x, staminaBarPos.y, this.staminaBar.get(0).getWidth()*this.hudSize, this.staminaBar.get(0).getHeight()*this.hudSize);

        Texture square = drawSquare(Color.WHITE);
        batch.draw(square, weaponIcon1Pos.x - 33, weaponIcon1Pos.y - 55);
        batch.draw(square, weaponIcon2Pos.x - 33, weaponIcon2Pos.y - 55);
        batch.draw(square, weaponIcon3Pos.x - 33, weaponIcon3Pos.y - 55);

        Texture defaultWeaponIcon = this.player.defaultWeapon.texture;
        batch.draw(defaultWeaponIcon, defaultWeaponIconPos.x, defaultWeaponIconPos.y, defaultWeaponIcon.getWidth() * hudSize * 1f, defaultWeaponIcon.getHeight() * hudSize * 1f);
        if (this.player.weapons.size() > 0) {
            if (this.player.indexWeapon == 0) {
                Texture redSquare = drawSquare(Color.RED);
                batch.draw(redSquare, weaponIcon1Pos.x - 33, weaponIcon1Pos.y - 55);
            }
            Texture weaponIcon1 = this.player.weapons.get(0).texture;
            batch.draw(weaponIcon1, weaponIcon1Pos.x, weaponIcon1Pos.y, weaponIcon1.getWidth() * hudSize * 1f, weaponIcon1.getHeight() * hudSize * 1f);
        }
        if (this.player.weapons.size() > 1) {
            if (this.player.indexWeapon == 1) {
                Texture redSquare = drawSquare(Color.RED);
                batch.draw(redSquare, weaponIcon2Pos.x - 33, weaponIcon2Pos.y - 55);
            }
            Texture weaponIcon2 = this.player.weapons.get(1).texture;
            batch.draw(weaponIcon2, weaponIcon2Pos.x, weaponIcon2Pos.y, weaponIcon2.getWidth() * hudSize * 1f, weaponIcon2.getHeight() * hudSize * 1f);
        }
        if (this.player.weapons.size() > 2) {
            if (this.player.indexWeapon == 2) {
                Texture redSquare = drawSquare(Color.RED);
                batch.draw(redSquare, weaponIcon3Pos.x - 33, weaponIcon3Pos.y - 55);
            }
            Texture weaponIcon3 = this.player.weapons.get(2).texture;
            batch.draw(weaponIcon3, weaponIcon3Pos.x, weaponIcon3Pos.y, weaponIcon3.getWidth() * hudSize * 1f, weaponIcon3.getHeight() * hudSize * 1f);
        }

        // Parcours de la HashMap de potions
        for (HashMap.Entry<Potion.PotionType, Integer> entry : this.player.potions.entrySet()) {
            Potion.PotionType potionType = entry.getKey();
            int potionCount = entry.getValue();

            // Dessiner l'icône de la potion en fonction du type
            Texture potionTexture = getTextureForPotion(potionType);
            Point potionIconPos = getPointForPotion(potionType);

            batch.draw(potionTexture, potionIconPos.x, potionIconPos.y, potionTexture.getWidth() * hudSize, potionTexture.getHeight() * hudSize);

            String potionCountText = "x" + potionCount;
            font.draw(batch, potionCountText, potionIconPos.x + 35, potionIconPos.y + 15);
        }
    }

    public void setFont(BitmapFont font) {
        this.font = font;
    }

    private Texture getTextureForPotion(Potion.PotionType potionType) {
        switch (potionType) {
            case HEALTH:
                return new Texture("assets/items/flask_big_red.png");
            case STAMINA:
                return new Texture("assets/items/flask_big_yellow.png");
            case STRENGTH:
                return new Texture("assets/items/flask_big_blue.png");
            case SPEED:
                return new Texture("assets/items/flask_big_green.png");
            default:
                return null;
        }
    }

    private Point getPointForPotion(Potion.PotionType potionType) {
        switch (potionType) {
            case HEALTH:
                return new Point (Gdx.graphics.getWidth() - 150, 600);
            case STAMINA:
                return new Point (Gdx.graphics.getWidth() - 95, 600);
            case STRENGTH:
                return new Point (Gdx.graphics.getWidth() - 150, 540);
            case SPEED:
                return new Point (Gdx.graphics.getWidth() - 95, 540);
            default:
                return null;
        }
    }

    private Texture drawSquare(Color color) {
        Pixmap pixmap = new Pixmap(150, 150, Pixmap.Format.RGBA8888);

        pixmap.setColor(color);
        pixmap.drawRectangle(0, 0, 100, 100);

        pixmap.setColor(new Color(1, 1, 1, 0.5f));
        pixmap.fillRectangle(1, 1, 98, 98);

        Texture texture = new Texture(pixmap);

        pixmap.dispose();

        return texture;
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
