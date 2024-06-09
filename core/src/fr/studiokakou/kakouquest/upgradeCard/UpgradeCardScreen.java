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

    public static void upgrade() {
        upgradeCards.clear();

        while (upgradeCards.size() < 3) {
            int random = Utils.randint(0, possibleCards.size() - 1);
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
    }
}
