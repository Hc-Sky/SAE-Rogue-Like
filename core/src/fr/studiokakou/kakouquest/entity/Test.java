package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Test.
 */
public class Test {
    /**
     * La position.
     */
    Point pos;
    /**
     * La hauteur.
     */
    float height;
    /**
     * La largeur.
     */
    float width;

    /**
     * Le nom.
     */
    String name;

    /**
     * Boolean si c'est rouge.
     */
    boolean isRed;

    /**
     * Le lancement du temps.
     */
    LocalDateTime hitStart=null;

    /**
     * Les effets de sang.
     */
    Animation<TextureRegion> bloodEffect;
    /**
     * The Blood state time.
     */
    float bloodStateTime=-1;

    /**
     * La texture.
     */
    Texture texture;
    /**
     * Le sprite .
     */
    public Sprite sprite;

    /**
     * Joeur Touchée .
     */
    public ArrayList<String> player_hitted = new ArrayList<>();

    /**
     * Instantiates a new Test.
     *
     * @param x    the x
     * @param y    the y
     * @param name the name
     */
    public Test(float x, float y, String name){
        this.name = name;
        this.pos = new Point(x, y);
        this.texture = new Texture("assets/map/skull.png");

        this.bloodEffect = Utils.getAnimation("assets/effects/blood.png", 6, 4, 0.02f);

        this.height = texture.getHeight();
        this.width = texture.getWidth();

        this.sprite = new Sprite(this.texture);
        this.sprite.setX(this.pos.x);
        this.sprite.setY(this.pos.y);
    }

    /**
     * Update hit animation.
     * Affiche l'animation de sang.
     * Si le monstre est touché, il devient rouge.
     * Si le monstre est rouge, il redevient normal après 200ms.
     * Si le monstre est rouge, il ne peut pas être touché.
     *
     * @param batch the batch
     */
    public void updateHitAnimation(SpriteBatch batch){
        if (bloodStateTime>=0){
            bloodStateTime+= Gdx.graphics.getDeltaTime();
            TextureRegion currentBloodFrame = this.bloodEffect.getKeyFrame(bloodStateTime, false);
            batch.draw(currentBloodFrame,
                    this.pos.x - (float) currentBloodFrame.getRegionWidth() /4 + (float) this.width/2,
                    this.pos.y - (float) currentBloodFrame.getRegionHeight() /4 + (float) this.height/2,
                    (float) currentBloodFrame.getRegionWidth() /2,
                    (float) currentBloodFrame.getRegionHeight() /2
            );
        }
        if (this.bloodEffect.isAnimationFinished(bloodStateTime)){
            this.bloodStateTime=-1;
        }

        if (hitStart!= null && this.isRed && this.hitStart.plusNanos(200000000).isBefore(LocalDateTime.now())){
            this.sprite.setColor(1, 1, 1, 1);
            this.isRed=false;
            this.hitStart=null;
        }
    }

    /**
     * Si le joueur est touché.
     * Si le joueur n'est pas déjà touché, il devient rouge.
     * Si le joueur est rouge, il redevient normal après 200ms.
     * Si le joueur est rouge, il ne peut pas être touché.
     * Si le joueur est touché, il est ajouté à la liste des joueurs touchés.
     *
     * @param player the player
     * @return the boolean
     */
    public boolean hit(Player player){
        if (!this.player_hitted.contains(player.name)){
            this.sprite.setColor(1, 0, 0, 1f);
            this.bloodStateTime=0f;
            this.isRed=true;
            System.out.println(this.name+" got hit by "+player.name);
            this.player_hitted.add(player.name);
            this.hitStart=LocalDateTime.now();
            return true;
        }
        return false;
    }
}
