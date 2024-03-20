package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Monster.
 * This class is used to create a monster object.
 *
 * @version 1.0
 * @author hugocohen--cofflard
 *
 */
public class Monster {
    public String name;
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
    public float speed;
    /**
     * The Damage.
     */
    public int damage;
    public float attackPause;

    LocalDateTime currentAttackTime;
    /**
     * The Hp.
     */
    public int hp;
    /**
     * The Detect range.
     */
    public int detectRange;
    /**
     * La hauteur.
     */
    float height;
    /**
     * La largeur.
     */
    float width;
    /**
     * The Idle animation.
     */
    Animation<TextureRegion> idleAnimation;
    /**
     * The Run animation.
     */
    Animation<TextureRegion> runAnimation;
    boolean isRunning=false;
    boolean isFlip=Utils.randint(0, 1)==0;
    public Sprite sprite;

    float stateTime=0f;

    public boolean isDying=false;
    public boolean isDead = false;

    /**
     * The Frame cols.
     */
    public static int FRAME_COLS = 1; /**
     * The Frame rows.
     */
    public static int FRAME_ROWS = 4;


    //hit vars
    public ArrayList<String> player_hitted = new ArrayList<>();
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
    float bloodStateTime=0f;

    public Monster(Point pos, String name, String idleAnimationPath, String runAnimationPath, int hp, int damage, float attackPause, float speed, int detectRange, int currentLevel){
        this.name=name;
        this.pos = pos;
        this.orientation = new Point(0, 0);
        this.speed = speed;
        this.damage = damage;
        this.attackPause=attackPause;
        this.hp = hp;
        this.detectRange = detectRange;
        this.idleAnimation = Utils.getAnimation(idleAnimationPath, FRAME_COLS, FRAME_ROWS);
        this.runAnimation = Utils.getAnimation(runAnimationPath, FRAME_COLS, FRAME_ROWS);
        this.bloodEffect = Utils.getAnimation("assets/effects/blood.png", 6, 4, 0.02f);

        this.height = idleAnimation.getKeyFrame(0f).getRegionHeight();
        this.width = idleAnimation.getKeyFrame(0f).getRegionWidth();
        this.sprite = new Sprite();
        this.stateTime=0f;

        this.upgradeStats(currentLevel);
    }

    public void upgradeStats(int currentLevel){
        this.speed = this.speed * currentLevel/2;
        this.hp = this.hp * currentLevel/2;
        this.damage = this.damage * currentLevel /2;
    }

    public Point center(){
        return new Point(this.pos.x+ this.width /2,this.pos.y+ this.height /4);
    }

    /**
     * Move.
     *
     */
    public void move(Player player){
        if (isDying || isRed || !player.hasPlayerSpawn){
            return;
        }
        Point playerPos = player.pos;
        if (Utils.distance(playerPos, this.pos)<=5){
            this.attack(player);
        } else {
            if (detectPlayer(playerPos)){
                this.isRunning = true;
                this.getOrientation(player);
                this.orientation = Point.getOrientation(this.pos, playerPos);
                this.pos = this.pos.add(this.orientation.x*this.speed*Gdx.graphics.getDeltaTime(), this.orientation.y*this.speed*Gdx.graphics.getDeltaTime());
            }else {
                this.isRunning=false;
            }
        }
    }

    public void getOrientation(Player player){
        if (player.center().x<this.center().x){
            this.isFlip = true;
        }else {
            this.isFlip=false;
        }
    }

    /**
     * Detect player boolean.
     *
     * @param playerPos the player pos
     * @return the boolean
     */
    public boolean detectPlayer(Point playerPos){
        return Utils.distance(this.pos, playerPos) <= this.detectRange;
    }


    private void attack(Player player) {
        if (this.isDying || !player.hasPlayerSpawn){
            return;
        }
        if (this.currentAttackTime==null || this.currentAttackTime.plusNanos((long) (1000000*this.attackPause)).isBefore(LocalDateTime.now())){
            player.takeDamage(this.damage, this.center());
            this.currentAttackTime = LocalDateTime.now();
        }
    }

    /**
     * Take damage.
     *
     */
    public void takeDamage(Player player){
        this.hp -= player.currentWeapon.damage*(player.strength/10);
        if (this.hp <= 0){
            this.isDying=true;
        }
    }

    public void draw(SpriteBatch batch){
        TextureRegion currentFrame;
        if(isRunning){
            currentFrame = this.runAnimation.getKeyFrame(this.stateTime, true);
        } else {
            currentFrame = this.idleAnimation.getKeyFrame(this.stateTime, true);
        }


        this.sprite = new Sprite(currentFrame);

        this.sprite.setX(this.pos.x);
        this.sprite.setY(this.pos.y);

        this.sprite.flip(this.isFlip, false);

        if (isRed){
            this.sprite.setColor(1, 0, 0, 1f);
        }

        this.sprite.draw(batch);

        this.stateTime+= Gdx.graphics.getDeltaTime();
    }

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
            this.isRed=false;
            this.player_hitted.remove(this.player_hitted.get(0));
            this.hitStart=null;
            if (isDying){
                this.isDead=true;
            }
        }
    }

    public boolean hit(Player player){
        if (!this.player_hitted.contains(player.name)){
            this.takeDamage(player);
            this.bloodStateTime=0f;
            this.isRed=true;
            this.player_hitted.add(player.name);
            this.hitStart=LocalDateTime.now();
            return true;
        }
        return false;
    }








    //monsters static
    public static Monster BIG_DEMON(int currentLevel, Point pos){
        return new Monster(pos, "Big Demon", "assets/entities/big_demon_idle.png", "assets/entities/big_demon_run.png", 300, 40, 1000, 40f, 100, currentLevel);
    }
}