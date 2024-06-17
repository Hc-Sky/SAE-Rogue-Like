package fr.studiokakou.kakouquest.upgradeCard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.interactive.OnGroundMeleeWeapon;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;

public class UpgradeCard {
    Texture texture;
    Sprite sprite;
    String upgradeName;
    int upgradeAmount;
    static int height = 502;
    static int width = 397;

    /**
     * Constructeur de la carte d'upgrade.
     *
     * @param texture Texture de la carte.
     * @param upgradeName Nom de l'upgrade.
     * @param upgradeAmount Montant de l'upgrade.
     */
    public UpgradeCard(Texture texture, String upgradeName, int upgradeAmount) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.upgradeName = upgradeName;
        this.upgradeAmount = upgradeAmount;
    }

    /**
     * Méthode pour appliquer l'upgrade au joueur lorsqu'il clique sur la carte.
     *
     * @param player Joueur.
     */
    public void clicked(Player player) {
        switch (upgradeName) {
            case "hp":
                player.max_hp += upgradeAmount;
                player.hp = player.max_hp;
                break;
            case "stamina":
                player.max_stamina += upgradeAmount;
                player.stamina = player.max_stamina;
                break;
            case "speed":
                player.speed += upgradeAmount;
                break;
            case "strength":
                player.strength += upgradeAmount;
                break;
            case "better_durability":
                player.betterDurability = true;
                break;
            case "bigger_weapon":
                player.biggerWeapon = true;
                break;
            case "lucky_one":
                player.currentWeapon = MeleeWeapon.LAVISH_SWORD();
//            System.out.println("Size : " + player.weapons.size());
                if (player.weapons.size() > 2) {
                    if (player.indexWeapon != -1) {
                        System.out.println(player.weapons);
                        System.out.println(player.indexWeapon);
                        player.weapons.set(player.indexWeapon, MeleeWeapon.LAVISH_SWORD());
                        System.out.println(player.weapons);
                    }
                } else {
                    player.weapons.add(MeleeWeapon.LAVISH_SWORD());
                    player.indexWeapon = player.weapons.size() - 1;
                }
                break;
            case "radiant":
                player.isRadiant = true;
                break;
            case "xp":
                player.xpBoost = true;
                break;

        }
    }

    /**
     * Méthode pour dessiner la carte d'upgrade.
     *
     * @param batch Batch.
     * @param pos Position.
     * @param player Joueur.
     */
    public void draw(SpriteBatch batch, int pos, Player player) {
        this.sprite.setX(((float) Gdx.graphics.getWidth() / 3)-((float) width /2) + (pos*width + (pos-1)*40));
        this.sprite.setY(((float) Gdx.graphics.getHeight() / 2) - ((float) height /2));
        this.sprite.draw(batch);

        if (Gdx.input.justTouched() && UpgradeCardScreen.appearTime.plusSeconds(1).isBefore(java.time.LocalDateTime.now())) {
            if (Gdx.input.getX() > this.sprite.getX() && Gdx.input.getX() < this.sprite.getX() + width && Gdx.input.getY() > Gdx.graphics.getHeight() - this.sprite.getY() - height && Gdx.input.getY() < Gdx.graphics.getHeight() - this.sprite.getY()){
                clicked(player);
                UpgradeCardScreen.isUpgrading = false;
            }
        }
    }
}
