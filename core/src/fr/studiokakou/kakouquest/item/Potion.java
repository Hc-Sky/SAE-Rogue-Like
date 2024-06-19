package fr.studiokakou.kakouquest.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.Random;

public class Potion {
    public enum PotionType {
        HEALTH, STAMINA, STRENGTH, SPEED
    }

    public Texture texture;

    private String name;
    private PotionType type;
    private int amount;

    /**
     * Constructeur de la classe Potion.
     *
     * @param type Type de la potion.
     * @param amount Quantité de la potion.
     */
    public Potion(PotionType type, int amount) {
        this.type = type;
        this.amount = amount;

        if (type == PotionType.HEALTH) {
            this.texture = new Texture("assets/items/flask_big_red.png");
            this.name = "health_potion";
        } else if (type == PotionType.STAMINA) {
            this.texture = new Texture("assets/items/flask_big_yellow.png");
            this.name = "stamina_potion";
        } else if (type == PotionType.STRENGTH) {
            this.texture = new Texture("assets/items/flask_big_blue.png");
            this.name = "strength_potion";
        } else {
            this.texture = new Texture("assets/items/flask_big_green.png");
            this.name = "speed_potion";
        }
    }

    /**
     * Génère une potion aléatoire.
     *
     * @return Potion aléatoire.
     */
    public static Potion generateRandomPotion() {
        Random random = new Random();
        PotionType type = PotionType.values()[random.nextInt(PotionType.values().length)];

        int amount = 1;

        return new Potion(type, amount);
    }

    //getters and setters
    public PotionType getType() {
        return type;
    }

    public void setType(PotionType type) {
        this.type = type;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }
}
