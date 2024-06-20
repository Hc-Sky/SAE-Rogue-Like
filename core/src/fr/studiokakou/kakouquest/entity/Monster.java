package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Floor;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;
import fr.studiokakou.kakouquest.weapon.Bow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;

/**
 * Represents a Monster in the game.
 * Each monster has its name, position, speed, damage, etc.
 *
 * @version 1.0
 * @author hugocohen--cofflard
 */
public class Monster {
    public String name;
    public Point pos;

    Point randomMoveDirection;
    LocalDateTime randomMoveStart;
    boolean isRandomMoving=false;

    LocalDateTime currentAttackTime;
    public int xp;
    public int hp;
    public int detectRange;
    float height;
    float width;
    public float speed;
    public int damage;
    public float attackPause;

    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> runAnimation;
    boolean isRunning=false;
    boolean isFlip=Utils.randint(0, 1)==0;
    public Sprite sprite;

    public boolean isDying=false;
    public boolean isDead = false;

    public static int FRAME_COLS = 1;
    public static int FRAME_ROWS = 4;

    //hit vars
    public ArrayList<String> player_hitted = new ArrayList<>();
    boolean isRed;
    boolean isRedRadiant;
    LocalDateTime radiantStart=null;
    LocalDateTime hitStart=null;
    Animation<TextureRegion> bloodEffect;
    float bloodStateTime=0f;

    public static Hashtable<Integer, ArrayList<Monster>> possibleMonsters = new Hashtable<>();

    public boolean onGuard = false;

    public static Texture exclamationMark;


    /**
     * Constructs a new Monster with specified parameters.
     *
     * @param name The name of the monster.
     * @param idleAnimationPath The path to the sprite sheet for idle animation.
     * @param runAnimationPath The path to the sprite sheet for run animation.
     * @param hp The hit points of the monster.
     * @param damage The damage inflicted by the monster.
     * @param attackPause The time pause between attacks.
     * @param speed The speed of the monster.
     * @param detectRange The range in which the monster can detect the player.
     * @param currentLevel The current level of the monster.
     */
    public Monster(String name, String idleAnimationPath, String runAnimationPath, int hp, int damage, float attackPause, float speed, int detectRange, int currentLevel, int xp){
        this.name=name;
        this.speed = speed;
        this.damage = damage;
        this.attackPause=attackPause;
        this.hp = hp;
        this.detectRange = detectRange;
        this.xp = xp;
        this.idleAnimation = Utils.getAnimation(idleAnimationPath, FRAME_COLS, FRAME_ROWS);
        if (Objects.equals(this.name, "Big Zombie")){
            this.runAnimation = Utils.getAnimation(runAnimationPath, FRAME_COLS, 5);
        }
        else {
            this.runAnimation = Utils.getAnimation(runAnimationPath, FRAME_COLS, FRAME_ROWS);
        }
        this.bloodEffect = Utils.getAnimation("assets/effects/blood.png", 6, 4, 0.02f);

        this.height = idleAnimation.getKeyFrame(0f).getRegionHeight();
        this.width = idleAnimation.getKeyFrame(0f).getRegionWidth();
        this.sprite = new Sprite();
        InGameScreen.stateTime=0f;

        this.upgradeStats(currentLevel);
    }

    public static void initExclamationMark(){
        exclamationMark = new Texture("assets/effects/exclamation.png");
    }

    /**
     * Sets the position of the monster.
     *
     * @param pos The position to set.
     */
    public void place(Point pos){
        this.pos = pos;
    }

    /**
     * Upgrades the stats of the monster based on the current level.
     *
     * @param currentLevel The current level of the monster.
     */
    public void upgradeStats(int currentLevel){
        this.hp = this.hp +(this.hp * currentLevel/4);
        this.damage = this.damage + (this.damage * currentLevel /4);
        if (InGameScreen.currentLevel < 12) {
            this.speed = this.speed * (1 + (float) currentLevel /10);
        } else {
            this.speed = this.speed * (1 + (float) 12 / 20);
        }
    }

    /**
     * Calculates the center point of the monster.
     *
     * @return The center point.
     */
    public Point center(){
        return new Point(this.pos.x+ this.width /2,this.pos.y+ this.height /4);
    }

    /**
     * Checks if the monster can move to the specified orientation on the map.
     *
     * @param orientation The direction to move.
     * @param map The map.
     * @return True if the monster can move, false otherwise.
     */
    public boolean canMove(Point orientation, Map map){
        Point newPos = this.pos.add(orientation.x*(this.speed)*Gdx.graphics.getDeltaTime(), orientation.y*(this.speed)*Gdx.graphics.getDeltaTime());
        Point hitboxTopLeft = newPos.add(3, this.height - Floor.TEXTURE_HEIGHT);
        Point hitboxBottomLeft = newPos.add(3, 0);
        Point hitboxTopRight = newPos.add(this.width-3, this.height - Floor.TEXTURE_HEIGHT);
        Point hitboxBottomRight = newPos.add(this.width-3, 0);

        Point[] points = {hitboxTopLeft, hitboxBottomLeft, hitboxTopRight, hitboxBottomRight};

        return map.arePointsOnFloor(points);
    }

    /**
     * Checks if the monster can move to the specified orientation on the map.
     *
     * @param orientation The direction to move.
     * @param map The map.
     * @param divider The divider to use for the speed.
     * @return True if the monster can move, false otherwise.
     */
    public boolean canMove(Point orientation, Map map, int divider){
        Point newPos = this.pos.add(orientation.x*(this.speed/divider)*Gdx.graphics.getDeltaTime(), orientation.y*(this.speed/divider)*Gdx.graphics.getDeltaTime());
        Point hitboxTopLeft = newPos.add(3, this.height - Floor.TEXTURE_HEIGHT);
        Point hitboxBottomLeft = newPos.add(3, 0);
        Point hitboxTopRight = newPos.add(this.width-3, this.height - Floor.TEXTURE_HEIGHT);
        Point hitboxBottomRight = newPos.add(this.width-3, 0);

        Point[] points = {hitboxTopLeft, hitboxBottomLeft, hitboxTopRight, hitboxBottomRight};

        return map.arePointsOnFloor(points);
    }


    /**
     * Randomly determines if the monster can move.
     *
     * @return True if the monster can move, false otherwise.
     */
    public void getRandomMove(){
        if (Utils.randint(1, 3) == 1){
            this.isRandomMoving = true;
            this.randomMoveDirection = new Point(Utils.randint(-1, 1), Utils.randint(-1, 1));
            while (this.randomMoveDirection.x == 0 && this.randomMoveDirection.y == 0){
                this.randomMoveDirection = new Point(Utils.randint(-1, 1), Utils.randint(-1, 1));
            }
            if (randomMoveDirection.x == 1){
                this.isFlip = false;
            } else if (randomMoveDirection.x == -1){
                this.isFlip = true;
            }
            this.randomMoveStart = LocalDateTime.now();
            this.isRunning = true;
        } else {
            this.isRandomMoving = false;
            this.isRunning = false;
            this.randomMoveDirection = null;
            this.randomMoveStart = LocalDateTime.now();
        }
    }

    /**
     * Inflicts radiant damage to the monster.
     */
    public void takeRadiantDamage(Player player){

        this.hp -= 10* (player.strength/10);
        this.isRedRadiant = true;
        this.radiantStart = LocalDateTime.now();

        if (this.hp <= 0){
            this.isDying=true;
            this.isDead=true;
        }
    }

    /**
     * Moves the monster towards the player if in detection range.
     *
     * @param player The player.
     * @param map The map.
     */
    public void move(Player player, Map map, LocalDateTime radiantTimer){
        if (isDying || isRed || !player.hasPlayerSpawn){
            return;
        }
        Point playerPos = player.pos;
        if (Utils.distance(playerPos, this.pos)<=10){
            this.attack(player);
        }
        if (player.isRadiant && Utils.getDistance(playerPos, this.pos) <= 40){
            if (radiantTimer==null || radiantTimer.plusNanos(1000000000).isBefore(LocalDateTime.now())){
                this.takeRadiantDamage(player);
                player.radiantTimer = LocalDateTime.now();
            }
        }
        if (detectPlayer(playerPos)){
            this.isRunning = true;
            this.getOrientation(player);
            Point orientation = Point.getOrientation(this.pos, playerPos);
            if (canMove(new Point(orientation.x, 0), map)){
                this.pos = this.pos.add(orientation.x*(this.speed)*Gdx.graphics.getDeltaTime(), 0);
            }
            if (canMove(new Point(0, orientation.y), map)){
                this.pos = this.pos.add(0, orientation.y*(this.speed)*Gdx.graphics.getDeltaTime());
            }
        }else {
            if (this.randomMoveStart == null || this.randomMoveStart.plusSeconds(1).isBefore(LocalDateTime.now())){
                this.getRandomMove();
            }
            if (this.isRandomMoving){
                if (canMove(this.randomMoveDirection, map, 3)){
                    this.pos = this.pos.add(this.randomMoveDirection.x*(this.speed/3)*Gdx.graphics.getDeltaTime(), this.randomMoveDirection.y*(this.speed/3)*Gdx.graphics.getDeltaTime());
                } else {
                    this.isRunning = false;
                    this.isRandomMoving = false;
                }
            }
        }
    }

    /**
     * Determines the orientation of the monster towards the player.
     *
     * @param player The player.
     */
    public void getOrientation(Player player){
        if (player.center().x-1<this.center().x){
            this.isFlip = true;
        }else {
            this.isFlip=false;
        }
    }

    /**
     * Detects if the player is within detection range.
     *
     * @param playerPos The position of the player.
     * @return True if the player is detected, false otherwise.
     */
    public boolean detectPlayer(Point playerPos){
        if (onGuard){
            return Utils.distance(this.pos, playerPos) <= this.detectRange*2;
        }
        return Utils.distance(this.pos, playerPos) <= this.detectRange;
    }

    /**
     * Attacks the player.
     *
     * @param player The player to attack.
     */
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
     * Inflicts damage to the monster.
     *
     * @param player The player attacking the monster.
     */
    public void takeDamage(Player player){
        this.hp -= player.currentWeapon.damage*(player.strength/10);
        if (this.hp <= 0){
            this.isDying=true;
            player.gainExperience(this.xp);
            InGameScreen.score += this.xp;
        }
    }

    /**
     * Draws the monster on the screen.
     *
     * @param batch The SpriteBatch used for drawing.
     */
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

        if (isRed || isRedRadiant){
            this.sprite.setColor(1, 0, 0, 1f);
        }

        this.sprite.draw(batch);

        if (onGuard) {
            batch.draw(exclamationMark, this.pos.x + this.width / 2 - 3, this.pos.y + this.height + 3, 5, 14);
        }

    }

    /**
     * Updates hit animation and handles monster death animation.
     *
     * @param batch The SpriteBatch used for drawing.
     */
    public void updateHitAnimation(SpriteBatch batch){
        if (bloodStateTime>=0){
            bloodStateTime+= Gdx.graphics.getDeltaTime();
            TextureRegion currentBloodFrame = this.bloodEffect.getKeyFrame(bloodStateTime, false);
            batch.draw(currentBloodFrame,
                    this.center().x - (float) currentBloodFrame.getRegionWidth() /2 + this.width/2,
                    this.center().y - (float) currentBloodFrame.getRegionHeight() /2 + this.height/2,
                    (float) currentBloodFrame.getRegionWidth(),
                    (float) currentBloodFrame.getRegionHeight()
            );
        }

        if (this.bloodEffect.isAnimationFinished(bloodStateTime)){
            this.bloodStateTime=-1;
        }

        if (hitStart!= null && this.isRed && this.hitStart.plusNanos(200000000).isBefore(LocalDateTime.now())){
            this.isRed=false;
            if (this.player_hitted.size()>0){
                this.player_hitted.remove(this.player_hitted.get(0));
            }
            this.hitStart=null;
            if (isDying){
                this.isDead=true;
            }
        }

        if (this.isRedRadiant && radiantStart!=null && radiantStart.plusNanos(150000000).isBefore(LocalDateTime.now())){
            this.isRedRadiant = false;
        }
    }

    /**
     * Handles monster getting hit by the player.
     *
     * @param player The player hitting the monster.
     * @return True if the monster was successfully hit, false otherwise.
     */
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

    /**
     * Handles monster getting hit by an arrow.
     *
     * @param player The player shooting the arrow.
     */
    public void arrowHit(Player player){
        this.hp -= Bow.BOW_DAMAGE*(player.strength/10);
        this.bloodStateTime=0f;
        this.isRed=true;
        this.hitStart=LocalDateTime.now();
        this.onGuard = true;

        if (this.hp <= 0){
            this.isDying=true;
            player.gainExperience(this.xp);
            InGameScreen.score += this.xp;
        }
    }



    /**
     * Creates possible monsters for a given level.
     *
     * @param currentLevel The current level.
     */
    public static void createPossibleMonsters(int currentLevel){
        possibleMonsters.clear();
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
        possibleMonsters.put(13, new ArrayList<>());


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
        possibleMonsters.get(13).add(Boss.createSlimeBoss(currentLevel));
    }

    public void dispose(){
        this.idleAnimation = null;
        this.runAnimation = null;
        this.bloodEffect = null;
    }

    static Monster BIG_DEMON(int currentLevel){
        return new Monster("Big Demon", "assets/entities/big_demon_idle.png", "assets/entities/big_demon_run.png", 400, 25, 1200, 40f, 150, currentLevel, 280);
    }
    static Monster BIG_ZOMBIE(int currentLevel){
        return new Monster("Big Zombie", "assets/entities/big_zombie_idle.png", "assets/entities/big_zombie_run.png", 450, 35, 1500, 45f, 200, currentLevel, 330);
    }
    static Monster CHORT(int currentLevel){
        return new Monster("Chort", "assets/entities/chort_idle.png", "assets/entities/chort_run.png", 70, 15, 700, 60f, 80, currentLevel, 155);
    }
    static Monster GOBLIN(int currentLevel){
        return new Monster("Goblin", "assets/entities/goblin_idle.png", "assets/entities/goblin_run.png", 60, 10, 700, 50f, 100, currentLevel, 55);
    }
    static Monster IMP(int currentLevel){
        return new Monster("Imp", "assets/entities/imp_idle.png", "assets/entities/imp_run.png", 35, 15, 600, 60f, 100, currentLevel, 105);
    }
    static Monster MASKED_ORC(int currentLevel){
        return new Monster("Masked Orc", "assets/entities/masked_orc_idle.png", "assets/entities/masked_orc_run.png", 150, 20, 600, 50f, 120, currentLevel, 80);
    }
    static Monster MUDDY(int currentLevel){
        return new Monster("Muddy", "assets/entities/muddy.png", "assets/entities/muddy.png", 250, 40, 600, 15f, 200, currentLevel, 155);
    }
    static Monster OGRE(int currentLevel){
        return new Monster("Ogre", "assets/entities/ogre_idle.png", "assets/entities/ogre_run.png", 500, 25, 2000, 50f, 200, currentLevel, 330);
    }
    static Monster ORC_WARRIOR(int currentLevel){
        return new Monster("Orc Warrior", "assets/entities/orc_warrior_idle.png", "assets/entities/orc_warrior_run.png", 120, 20, 600, 50f, 120, currentLevel, 55);
    }
    static Monster SKELET(int currentLevel){
        return new Monster("Skelet", "assets/entities/skelet_idle.png", "assets/entities/skelet_run.png", 30, 30, 300, 50f, 120, currentLevel, 80);
    }
    static Monster SWAMPY(int currentLevel){
        return new Monster("Swampy", "assets/entities/swampy.png", "assets/entities/swampy.png", 400, 50, 800, 18f, 200, currentLevel, 205);
    }
    static Monster TINY_ZOMBIE(int currentLevel){
        return new Monster("Tiny Zombie", "assets/entities/tiny_zombie_idle.png", "assets/entities/tiny_zombie_run.png", 20, 25, 600, 55f, 100, currentLevel, 55);
    }
    static Monster WOGOL(int currentLevel){
        return new Monster("Wogol", "assets/entities/wogol_idle.png", "assets/entities/wogol_run.png", 200, 20, 600, 50f, 150, currentLevel, 130);
    }
}
