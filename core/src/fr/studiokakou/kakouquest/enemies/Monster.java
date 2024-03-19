package fr.studiokakou.kakouquest.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.utils.Utils;

public class Monster {
    public Point pos;
    public Point orientation;
    public int speed;
    public int damage;
    public int hp;
    public int detect_range;
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> runAnimation;
    Animation<TextureRegion> runAnimationRevert;

    static final int FRAME_COLS = 1, FRAME_ROWS = 4;

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

    public boolean detectPlayer(Point playerPos){
        return Utils.distance(this.pos, playerPos) <= this.detect_range;
    }


    private void attack() {
        //TODO
    }

    public void takeDamage(int damage){
        this.hp -= damage;
        if (this.hp <= 0){
            this.die();
        }
    }

    public void die(){
        //TODO
    }
}