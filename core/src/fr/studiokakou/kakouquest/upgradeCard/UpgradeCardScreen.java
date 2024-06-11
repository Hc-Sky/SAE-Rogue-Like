package fr.studiokakou.kakouquest.upgradeCard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UpgradeCardScreen {

    public static boolean isUpgrading = false;
    static ArrayList<UpgradeCard> upgradeCards = new ArrayList<>();
    static ArrayList<UpgradeCard> possibleCards = new ArrayList<>();
    public static LocalDateTime appearTime;

    public static void upgrade(Player player) {
        upgradeCards.clear();
        
        if (Utils.randint(1, 7)==1){
            int random = Utils.randint(4,5);
            if (random == 4 && !player.betterDurability)
                upgradeCards.add(possibleCards.get(random));
            else if (random == 5 && !player.biggerWeapon)
                upgradeCards.add(possibleCards.get(random));
        } else if (Utils.randint(1, 15)==1) {
            upgradeCards.add(possibleCards.get(6));
        }

        while (upgradeCards.size() < 3) {
            int random = Utils.randint(0, 3);
            if (! upgradeCards.contains(possibleCards.get(random))){
                upgradeCards.add(possibleCards.get(random));
            }
        }

        isUpgrading = true;
        appearTime = LocalDateTime.now();
    }

    public static void draw(SpriteBatch batch, Player player) {
        if (isUpgrading) {         
            

            for (int i = 0; i < upgradeCards.size(); i++) {
                upgradeCards.get(i).draw(batch, i, player);
            }
        }
    }

    public static void initUpgradeCards() {
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/health_card.png"), "hp", 10));
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/speed_card.png"), "speed", 4));
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/strength_card.png"), "strength", 1));
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/stamina_card.png"), "stamina", 10));
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/better_durability_card.png"), "better_durability", 0));
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/bigger_weapon_card.png"), "bigger_weapon", 0));
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/lucky_one_card.png"), "lucky_one", 0));
    }
}
