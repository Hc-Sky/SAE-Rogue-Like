package fr.studiokakou.kakouquest.entity;

import com.badlogic.gdx.Gdx;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * The type Monster.
 * This class is used to create a monster object.
 *
 * @version 1.0
 *
 */
public class Monster {
    public String name; // The name of the monster
    public Point pos; // The position of the monster

    public float speed; // The speed of the monster
    public int damage; // The damage the monster can inflict
    public float attackPause; // The pause between the monster's attacks

    LocalDateTime currentAttackTime; // The current attack time of the monster

    public int hp; // The hit points (hp) of the monster
    public int detectRange; // The range within which the monster can detect the player

    float height; // The height of the monster
    float width; // The width of the monster
    Animation<TextureRegion> idleAnimation; // The idle animation of the monster
    Animation<TextureRegion> runAnimation; // The run animation of the monster

    boolean isRunning=false; // The state of the monster, whether it is running or not
    boolean isFlip=Utils.randint(0, 1)==0; // The state of the monster, whether it is flipped or not

    public Sprite sprite; // The sprite representing the monster

    public boolean isDying=false; // The state of the monster, whether it is dying or not
    public boolean isDead = false; // The state of the monster, whether it is dead or not

    public static int FRAME_COLS = 1; // The number of frame columns for the monster's animation
    public static int FRAME_ROWS = 4; // The number of frame rows for the monster's animation

    //hit vars
    public ArrayList<String> playerHitted = new ArrayList<>(); // The list of players that the monster has hit
    boolean isRed; // The state of the monster, whether it is red or not
    LocalDateTime hitStart=null; // The start time of the monster's hit
    Animation<TextureRegion> bloodEffect; // The blood effect animation of the monster
    float bloodStateTime=0f; // The blood state time of the monster

    public static Dictionary<Integer, ArrayList<Monster>> possibleMonsters = new Hashtable<>(); // The possible monsters that can be created



    /**
     * Constructs a new Monster object with the given parameters.
     *
     * @param name The name of the monster.
     * @param idleAnimationPath The file path to the idle animation for the monster.
     * @param runAnimationPath The file path to the running animation for the monster.
     * @param hp The hit points (health) of the monster.
     * @param damage The damage the monster can inflict.
     * @param attackPause The pause time between the monster's attacks.
     * @param speed The speed at which the monster moves.
     * @param detectRange The range within which the monster can detect the player.
     * @param currentLevel The current level of the monster.
     */
    public Monster(String name, String idleAnimationPath, String runAnimationPath, int hp, int damage, float attackPause, float speed, int detectRange, int currentLevel){
        this.name=name;
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

    /**
     * Sets the position of the monster.
     *
     * @param pos The new position of the monster.
     */
    public void place(Point pos){
        this.pos = pos;
    }

    /**
     * Upgrades the stats of the monster based on the current level.
     * The hit points (hp) and damage of the monster are increased by a quarter of their current value times the current level.
     *
     * @param currentLevel The current level of the monster.
     */
    public void upgradeStats(int currentLevel){
        this.hp = this.hp +(this.hp * currentLevel/4);
        this.damage = this.damage + (this.damage * currentLevel /4);
    }

    /**
     * Calculates and returns the center point of the monster.
     * The center point is calculated as the position of the monster plus half of its width and a quarter of its height.
     *
     * @return The center point of the monster.
     */
    public Point center(){
        return new Point(this.pos.x+ this.width /2,this.pos.y+ this.height /4);
    }

    /**
     * Checks if the monster can move in the given orientation on the map.
     * The method calculates a new position based on the current position, speed, and orientation of the monster.
     * It then calculates the four corners of the hitbox for the monster at the new position.
     * Finally, it checks if all four corners of the hitbox are on the floor of the map.
     *
     * @param orientation The direction in which the monster wants to move.
     * @param map The map on which the monster is moving.
     * @return true if all four corners of the hitbox are on the floor of the map, false otherwise.
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
     * Moves the monster towards the player if the player is within the monster's detection range.
     * If the monster is dying, red, or the player has not spawned, the method returns without moving the monster.
     * If the player is within a distance of 10 from the monster, the monster attacks the player.
     * If the player is within the monster's detection range, the monster moves towards the player.
     * The monster's new position is calculated based on its current position, speed, and the orientation towards the player.
     * The monster only moves if it can move in the calculated direction on the map.
     * If the player is not within the monster's detection range, the monster stops running.
     *
     * @param player The player that the monster is moving towards or attacking.
     * @param map The map on which the monster is moving.
     */
    public void move(Player player, Map map){
        if (isDying || isRed || !player.hasPlayerSpawn){
            return;
        }
        Point playerPos = player.pos;
        if (Utils.distance(playerPos, this.pos)<=10){
            this.attack(player);
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
            this.isRunning=false;
        }
    }

    /**
     * Determines the orientation of the monster relative to the player.
     * If the player's x-coordinate (minus 1) is less than the monster's x-coordinate, the monster is considered flipped.
     * Otherwise, the monster is not considered flipped.
     *
     * @param player The player whose position is used to determine the monster's orientation.
     */
    public void getOrientation(Player player){
        if (player.center().x-1<this.center().x){
            this.isFlip = true;
        }else {
            this.isFlip=false;
        }
    }

    /**
     * Checks if the player is within the monster's detection range.
     * The method calculates the distance between the monster's position and the player's position.
     * It then checks if this distance is less than or equal to the monster's detection range.
     *
     * @param playerPos The position of the player.
     * @return true if the player is within the monster's detection range, false otherwise.
     */
    public boolean detectPlayer(Point playerPos){
        return Utils.distance(this.pos, playerPos) <= this.detectRange;
    }


    /**
     * Attacks the player if the monster is not dying and the player has spawned.
     * If the monster's current attack time is null or the time since the last attack is greater than the monster's attack pause,
     * the monster inflicts damage on the player and the current attack time is updated to the current time.
     *
     * @param player The player that the monster is attacking.
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
     * Reduces the monster's hit points (hp) based on the damage inflicted by the player's current weapon and the player's strength.
     * The damage inflicted is calculated as the damage of the player's current weapon multiplied by a tenth of the player's strength.
     * If the monster's hit points (hp) drop to 0 or below, the monster is marked as dying.
     *
     * @param player The player who is inflicting damage on the monster.
     */
    public void takeDamage(Player player){
        this.hp -= player.currentWeapon.damage*(player.strength/10);
        if (this.hp <= 0){
            this.isDying=true;
        }
    }

    /**
     * Draws the monster on the screen.
     * The method first determines the current frame of the monster's animation based on whether the monster is running or not.
     * It then creates a new sprite with the current frame and sets the position of the sprite to the position of the monster.
     * If the monster is flipped, the sprite is also flipped.
     * If the monster is red, the color of the sprite is set to red.
     * Finally, the sprite is drawn on the batch.
     *
     * @param batch The batch on which the sprite is drawn.
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

        if (isRed){
            this.sprite.setColor(1, 0, 0, 1f);
        }

        this.sprite.draw(batch);
    }

    /**
     * Updates the hit animation for the monster.
     * If the blood state time is greater than or equal to 0, the method increments the blood state time by the delta time.
     * It then gets the current frame of the blood effect animation and draws it on the batch at the center of the monster.
     * If the blood effect animation is finished, the blood state time is set to -1.
     * If the monster is red and the time since the start of the hit is greater than 200 milliseconds, the monster is no longer red.
     * The first player in the list of players that the monster has hit is removed from the list and the start time of the hit is set to null.
     * If the monster is dying, it is marked as dead.
     *
     * @param batch The batch on which the hit animation is drawn.
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
            this.playerHitted.remove(this.playerHitted.get(0));
            this.hitStart=null;
            if (isDying){
                this.isDead=true;
            }
        }
    }

    /**
     * Inflicts damage on the player if the player has not already been hit by the monster.
     * The method first checks if the player's name is in the list of players that the monster has hit.
     * If the player's name is not in the list, the monster inflicts damage on the player, the blood state time is set to 0,
     * the monster is marked as red, the player's name is added to the list of players that the monster has hit,
     * and the start time of the hit is set to the current time.
     * The method then returns true to indicate that the player has been hit.
     * If the player's name is in the list, the method returns false to indicate that the player has not been hit.
     *
     * @param player The player that the monster is trying to hit.
     * @return true if the player has been hit, false otherwise.
     */
    public boolean hit(Player player){
        if (!this.playerHitted.contains(player.name)){
            this.takeDamage(player);
            this.bloodStateTime=0f;
            this.isRed=true;
            this.playerHitted.add(player.name);
            this.hitStart=LocalDateTime.now();
            return true;
        }
        return false;
    }



    /**
     * Creates a dictionary of possible monsters that can be created at the current level.
     * The dictionary is initialized with 12 keys, each representing a level from 1 to 12.
     * Each key is associated with an ArrayList of Monster objects.
     * Depending on the current level, different types of monsters are added to the corresponding ArrayList.
     * For example, at level 10, a Big Demon is added to the ArrayList associated with the key 10.
     *
     * @param currentLevel The current level at which the monsters are being created.
     */
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
        possibleMonsters.get(12).add(OGRE(currentLevel));
        possibleMonsters.get(1).add(ORC_WARRIOR(currentLevel));
        possibleMonsters.get(2).add(SKELET(currentLevel));
        possibleMonsters.get(7).add(SWAMPY(currentLevel));
        possibleMonsters.get(1).add(TINY_ZOMBIE(currentLevel));
        possibleMonsters.get(4).add(WOGOL(currentLevel));
    }


    //creates the monsters in static for better optimisation
    //It uses functions to get new Monsters every times
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