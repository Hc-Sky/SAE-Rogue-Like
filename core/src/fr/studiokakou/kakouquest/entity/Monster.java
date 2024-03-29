package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

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
    public int rarity; //chance of spawn at x level
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

    public static Dictionary<Integer, ArrayList<Monster>> possibleMonsters = new Hashtable<>();



    public Monster(String name, String idleAnimationPath, String runAnimationPath, int hp, int damage, float attackPause, float speed, int detectRange, int currentLevel){
        this.name=name;
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
        InGameScreen.stateTime=0f;

        this.upgradeStats(currentLevel);
    }

    public void place(Point pos){
        this.pos = pos;
    }

    public void upgradeStats(int currentLevel){
        this.hp = this.hp +(this.hp * currentLevel/4);
        this.damage = this.damage + (this.damage * currentLevel /4);
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
        if (Utils.distance(playerPos, this.pos)<=10){
            this.attack(player);
        } else {
            if (detectPlayer(playerPos)){
                this.isRunning = true;
                this.getOrientation(player);
                this.orientation = Point.getOrientation(this.pos, playerPos);
                this.pos = this.pos.add(this.orientation.x*(this.speed)*Gdx.graphics.getDeltaTime(), this.orientation.y*(this.speed)*Gdx.graphics.getDeltaTime());
            }else {
                this.isRunning=false;
            }
        }
    }

    public void getOrientation(Player player){
        if (player.center().x-1<this.center().x){
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
            player.takeDamage(this.damage);
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
            currentFrame = this.runAnimation.getKeyFrame(InGameScreen.stateTime, true);
        } else {
            currentFrame = this.idleAnimation.getKeyFrame(InGameScreen.stateTime, true);
        }


        this.sprite = new Sprite(currentFrame);

        this.sprite.setX(this.pos.x);
        this.sprite.setY(this.pos.y);

        this.sprite.flip(this.isFlip, false);

        if (isRed){
            this.sprite.setColor(1, 0, 0, 1f);
        }

        this.sprite.draw(batch);
    }

    public void updateHitAnimation(SpriteBatch batch){
        if (bloodStateTime>=0){
            bloodStateTime+= Gdx.graphics.getDeltaTime();
            TextureRegion currentBloodFrame = this.bloodEffect.getKeyFrame(bloodStateTime, false);
            batch.draw(currentBloodFrame,
                this.center().x - (float) currentBloodFrame.getRegionWidth() /2 + (float) this.width/2,
                this.center().y - (float) currentBloodFrame.getRegionHeight() /2 + (float) this.height/2,
                (float) currentBloodFrame.getRegionWidth(),
                (float) currentBloodFrame.getRegionHeight()
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



    public static void createPossibleMonsters(int currentLevel){
        possibleMonsters = new Hashtable<>();
        possibleMonsters.put(1, new ArrayList<>());
        possibleMonsters.put(2, new ArrayList<>());
        possibleMonsters.put(3, new ArrayList<>());
        possibleMonsters.put(4, new ArrayList<>());
        possibleMonsters.put(5, new ArrayList<>());
        possibleMonsters.put(6, new ArrayList<>());
        possibleMonsters.put(7, new ArrayList<>());
        possibleMonsters.put(8, new ArrayList<>());
        possibleMonsters.put(9, new ArrayList<>());
        possibleMonsters.put(10, new ArrayList<>());
        possibleMonsters.put(11, new ArrayList<>());
        possibleMonsters.put(12, new ArrayList<>());


        possibleMonsters.get(10).add(BIG_DEMON(currentLevel));
        possibleMonsters.get(12).add(BIG_ZOMBIE(currentLevel));
        possibleMonsters.get(5).add(CHORT(currentLevel));
        possibleMonsters.get(1).add(GOBLIN(currentLevel));
        possibleMonsters.get(3).add(IMP(currentLevel));
        possibleMonsters.get(2).add(MASKED_ORC(currentLevel));
        possibleMonsters.get(5).add(MUDDY(currentLevel));
        possibleMonsters.get(12).add( OGRE(currentLevel));
        possibleMonsters.get(1).add(ORC_WARRIOR(currentLevel));
        possibleMonsters.get(2).add(SKELET(currentLevel));
        possibleMonsters.get(7).add(SWAMPY(currentLevel));
        possibleMonsters.get(1).add(TINY_ZOMBIE(currentLevel));
        possibleMonsters.get(4).add(WOGOL(currentLevel));
    }

    static Monster BIG_DEMON(int currentLevel){
        return new Monster("Big Demon", "assets/entities/big_demon_idle.png", "assets/entities/big_demon_run.png", 400, 25, 1200, 40f, 150, currentLevel);
    }
    static Monster BIG_ZOMBIE(int currentLevel){
        return new Monster("Big Zombie", "assets/entities/big_zombie_idle.png", "assets/entities/big_zombie_run.png", 450, 35, 1500, 45f, 200, currentLevel);
    }
    static Monster CHORT(int currentLevel){
        return new Monster("Chort", "assets/entities/chort_idle.png", "assets/entities/chort_run.png", 70, 15, 700, 60f, 80, currentLevel);
    }
    static Monster GOBLIN(int currentLevel){
        return new Monster("Goblin", "assets/entities/goblin_idle.png", "assets/entities/goblin_run.png", 60, 10, 700, 50f, 100, currentLevel);
    }
    static Monster IMP(int currentLevel){
        return new Monster("Imp", "assets/entities/imp_idle.png", "assets/entities/imp_run.png", 35, 15, 600, 60f, 100, currentLevel);
    }
    static Monster MASKED_ORC(int currentLevel){
        return new Monster("Masked Orc", "assets/entities/masked_orc_idle.png", "assets/entities/masked_orc_run.png", 150, 20, 600, 50f, 120, currentLevel);
    }
    static Monster MUDDY(int currentLevel){
        return new Monster("Muddy", "assets/entities/muddy.png", "assets/entities/muddy.png", 250, 40, 600, 15f, 200, currentLevel);
    }
    static Monster OGRE(int currentLevel){
        return new Monster("Ogre", "assets/entities/ogre_idle.png", "assets/entities/ogre_run.png", 500, 25, 2000, 50f, 200, currentLevel);
    }
    static Monster ORC_WARRIOR(int currentLevel){
        return new Monster("Orc Warrior", "assets/entities/orc_warrior_idle.png", "assets/entities/orc_warrior_run.png", 120, 20, 600, 50f, 120, currentLevel);
    }
    static Monster SKELET(int currentLevel){
        return new Monster("Skelet", "assets/entities/skelet_idle.png", "assets/entities/skelet_run.png", 30, 30, 300, 50f, 120, currentLevel);
    }
    static Monster SWAMPY(int currentLevel){
        return new Monster("Swampy", "assets/entities/swampy.png", "assets/entities/swampy.png", 400, 50, 800, 18f, 200, currentLevel);
    }
    static Monster TINY_ZOMBIE(int currentLevel){
        return new Monster("Tiny Zombie", "assets/entities/tiny_zombie_idle.png", "assets/entities/tiny_zombie_run.png", 20, 25, 600, 55f, 100, currentLevel);
    }
    static Monster WOGOL(int currentLevel){
        return new Monster("Wogol", "assets/entities/wogol_idle.png", "assets/entities/wogol_run.png", 200, 20, 600, 50f, 150, currentLevel);
    }

}