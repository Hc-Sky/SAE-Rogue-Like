package fr.studiokakou.kakouquest.upgradeCard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UpgradeCardScreen {

    public static boolean isUpgrading = false;
    static ArrayList<UpgradeCard> upgradeCards = new ArrayList<>();
    static ArrayList<UpgradeCard> possibleCards = new ArrayList<>();
    public static LocalDateTime appearTime;

    /**
     * Méthode pour lancer l'écran d'upgrade.
     *
     * @param player Joueur.
     */
    public static void upgrade(Player player) {
        upgradeCards.clear();
        
        if (Utils.randint(1, 7)==1){
            int random = Utils.randint(1, 3);
            if (random == 1 && !player.betterDurability)
                upgradeCards.add(possibleCards.get(4));
            else if (random == 2 && !player.biggerWeapon)
                upgradeCards.add(possibleCards.get(5));
            else if (random == 3 && !player.xpBoost){
                upgradeCards.add(possibleCards.get(8));
            }
        } else if (Utils.randint(1, 15)==1) {
            int random = Utils.randint(6,7);
            if (random == 6)
                upgradeCards.add(possibleCards.get(random));
            else if (random == 7 && !player.isRadiant)
                upgradeCards.add(possibleCards.get(random));
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
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/radiant_card.png"), "radiant", 0));
        possibleCards.add(new UpgradeCard(new Texture("assets/upgrade_cards/xp_upgrade.png"), "xp", 0));
    }
}
