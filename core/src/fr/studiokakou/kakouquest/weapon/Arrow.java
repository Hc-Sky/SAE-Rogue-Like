package fr.studiokakou.kakouquest.weapon;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

public class Arrow {
    public static Texture arrowTexture;
    Sprite sprite;

    Point startPos;
    float rotation;

    public static final float ARROW_SPEED = 500f;

    public boolean toRemove = false;

    public static void initTexture() {
        arrowTexture = new Texture("assets/weapon/weapon_arrow.png");
    }

    /**
     * Méthode pour vérifier si l'arrow a touché un monstre.
     *
     * @param player
     */
    public void checkHit(Player player){
        Rectangle meleeWeaponRectangle = this.sprite.getBoundingRectangle();
        for (Monster m : Map.monsters){
            Rectangle mRectangle = m.sprite.getBoundingRectangle();
            if (meleeWeaponRectangle.overlaps(mRectangle)){
                m.arrowHit(player);
                toRemove = true;
            }
        }
    }

    /**
     * Constructeur de la flèche.
     *
     * @param startPos Position de départ.
     * @param rotation Rotation de la flèche.
     */
    public Arrow(Point startPos, float rotation) {
        this.startPos = startPos;
        this.sprite = new Sprite(arrowTexture);
        this.rotation = rotation;
        sprite.setOrigin((float) arrowTexture.getWidth() /2, (float) arrowTexture.getHeight() /2);
        sprite.setRotation(rotation-90f);
    }

    /**
     * Méthode pour vérifier si la flèche est sur le sol.
     *
     * @param map
     * @return
     */
    public boolean isArrowOnFloor(Map map){
        return map.arePointsOnFloor(new Point[]{startPos.add((float) arrowTexture.getWidth() /2, (float) arrowTexture.getHeight() /2)});
    }

    /**
     * Méthode pour mettre à jour la flèche.
     *
     * @param delta Temps écoulé depuis le dernier rendu.
     * @param batch Batch pour le rendu.
     * @param map Carte.
     * @param player Joueur.
     */
    public void update(float delta, SpriteBatch batch, Map map, Player player) {
        startPos.x += ARROW_SPEED * delta * (float) Math.cos(Math.toRadians(rotation));
        startPos.y += ARROW_SPEED * delta * (float) Math.sin(Math.toRadians(rotation));
        draw(batch);
        checkHit(player);
        if (!isArrowOnFloor(map)){
            toRemove = true;
        }
    }

    /**
     * Méthode pour dessiner la flèche.
     *
     * @param batch Batch pour le rendu.
     */
    public void draw(SpriteBatch batch) {
        sprite.setPosition(startPos.x, startPos.y);
        sprite.draw(batch);
    }
}
