package fr.studiokakou.kakouquest.upgradeCard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.player.Player;

public class UpgradeCard {
    Texture texture;
    Sprite sprite;
    String upgradeName;
    int upgradeAmount;

    public UpgradeCard(Texture texture, String upgradeName, int upgradeAmount) {
        this.texture = texture;
        this.sprite = new Sprite(texture);
        this.upgradeName = upgradeName;
        this.upgradeAmount = upgradeAmount;
    }

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
        }
    }

    public void draw(SpriteBatch batch, int pos, Player player) {
        this.sprite.setX(((float) Gdx.graphics.getWidth() / 3)-((float) this.texture.getWidth() /2) + (pos*texture.getWidth()));
        this.sprite.setY(((float) Gdx.graphics.getHeight() / 2) - ((float) this.texture.getHeight() /2));
        this.sprite.draw(batch);

        if (Gdx.input.justTouched() && UpgradeCardScreen.appearTime.plusSeconds(1).isBefore(java.time.LocalDateTime.now())) {
            if (Gdx.input.getX() > this.sprite.getX() && Gdx.input.getX() < this.sprite.getX() + this.sprite.getWidth() && Gdx.input.getY() > Gdx.graphics.getHeight() - this.sprite.getY() - this.sprite.getHeight() && Gdx.input.getY() < Gdx.graphics.getHeight() - this.sprite.getY()){
                clicked(player);
                UpgradeCardScreen.isUpgrading = false;
            }
        }
    }
}
