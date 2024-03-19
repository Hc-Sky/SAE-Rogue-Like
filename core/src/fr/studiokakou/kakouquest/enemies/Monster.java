package fr.studiokakou.kakouquest.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.utils.Utils;

/**
 * The type Monster.
 * This class is used to create a monster object.
 *
 * @version 1.0
 * @author hugocohen--cofflard
 *
 */
public class Monster {
    /**
     * The Pos.
     */
    public Point pos;
    /**
     * The Orientation.
     */
    public Point orientation;
    /**
     * The Speed.
     */
    public int speed;
    /**
     * The Damage.
     */
    public int damage;
    /**
     * The Hp.
     */
    public int hp;
    /**
     * The Detect range.
     */
    public int detect_range;
    /**
     * The Idle animation.
     */
    Animation<TextureRegion> idleAnimation;
    /**
     * The Run animation.
     */
    Animation<TextureRegion> runAnimation;
    /**
     * The Run animation revert.
     */
    Animation<TextureRegion> runAnimationRevert;

    /**
     * The Frame cols.
     */
    static final int FRAME_COLS = 1, /**
     * The Frame rows.
     */
    FRAME_ROWS = 4;

    /**
     * Instantiates a new Monster.
     *
     * @param x the x
     * @param y the y
     */
    public Monster(float x, float y){
        this.pos = new Point(x, y);
        this.orientation = new Point(0, 0);
        this.speed = 1;
        this.damage = 1;
        this.hp = 1;
        this.detect_range = 5;
        this.idleAnimation = Utils.getAnimation("assets/entities/skelet_idle.png.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimation = Utils.getAnimation("assets/player/skelet_run.png.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimationRevert =  Utils.getAnimationRevert("assets/player/skelet_run.png", FRAME_COLS, FRAME_ROWS);
    }

    /**
     * Move.
     *
     * @param playerPos the player pos
     */
    public void move(Point playerPos){
        if (this.hp > 0){
            if (this.pos.x == playerPos.x && this.pos.y == playerPos.y){
                this.attack();
            } else {
                if (detectPlayer(playerPos)){
                    this.orientation = Point.getOrientation(this.pos, playerPos);
                    this.pos = this.pos.add(this.orientation.x*this.speed, this.orientation.y*this.speed);
                }
            }
        }
    }

    /**
     * Detect player boolean.
     *
     * @param playerPos the player pos
     * @return the boolean
     */
    public boolean detectPlayer(Point playerPos){
        return Utils.distance(this.pos, playerPos) <= this.detect_range;
    }


    private void attack() {
        //TODO
    }

    /**
     * Take damage.
     *
     * @param damage the damage
     */
    public void takeDamage(int damage){
        this.hp -= damage;
        if (this.hp <= 0){
            this.die();
        }
    }

    /**
     * Die.
     */
    public void die(){
        //TODO
    }
}