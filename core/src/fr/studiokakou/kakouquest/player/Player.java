package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.keybinds.Keybinds;
import fr.studiokakou.kakouquest.map.Floor;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;

import java.time.LocalDateTime;

/**
 * Represents the player entity.
 */
public class Player {

    /** The position of the player. */
    public Point pos;
    /** The last position of the player. */
    Point lastPos;

    /** The name of the player. */
    public String name;
    /** The hit points of the player. */
    public int hp;
    /** The maximum hit points of the player. */
    public int max_hp;
    /** The strength of the player. */
    public int strength;
    /** The speed of the player. */
    public float speed;
    /** The stamina of the player. */
    public float stamina;
    /** The maximum stamina of the player. */
    public int max_stamina;

    /** The current weapon equipped by the player. */
    public MeleeWeapon currentWeapon;

    /** Indicates if the player is dashing. */
    boolean isDashing = false;
    /** Indicates if the player can dash. */
    boolean canDash = true;
    /** The final point of the dash. */
    Point dashFinalPoint;
    /** The start point of the dash. */
    Point dashStartPoint;
    /** The orientation of the dash. */
    Point dashOrientation;
    /** The timer of the dash. */
    LocalDateTime dashTimer;
    /** The state time of the dash animation. */
    float dashStateTime;

    /** The distance of the player's melee weapon. */
    public static float PLAYER_MELEE_WEAPON_DISTANCE=10f;
    /** The pause between attacks. */
    public static float ATTACK_PAUSE = 200f;
    /** The stamina usage for attacking. */
    static int ATTACK_STAMINA_USAGE = 2;

    /** The timer for stamina regeneration. */
    LocalDateTime staminaTimer;
    /** The timer for the attack. */
    LocalDateTime attackTimer;
    /** Indicates if the player is attacking. */
    public boolean isAttacking=false;
    /** Indicates if the player can attack. */
    public boolean canAttack = true;
    /** The direction of the attack. */
    Point attackDirection;
    /** The position of the attack. */
    Point attackPos;
    /** The end rotation of the attack. */
    float attackEndRotation;
    /** The rotation of the attack. */
    float attackRotation;

    /** The distance of the dash. */
    static float DASH_DISTANCE = 50f;
    /** The speed of the dash. */
    static float DASH_SPEED = 500f;
    /** The pause between dashes. */
    static long DASH_PAUSE = 3;
    /** The stamina usage for dashing. */
    static int DASH_STAMINA_USAGE = 10;

    /** The height of the player's texture. */
    public int texture_height;
    /** The width of the player's texture. */
    public int texture_width;

    /** Indicates if the player is flipped. */
    boolean flip=false;
    /** Indicates if the player is running. */
    boolean isRunning=false;
    /** Indicates if the player is spawning. */
    public boolean isPlayerSpawning=false;
    /** Indicates if the player has spawned. */
    public boolean hasPlayerSpawn=false;

    /** The idle animation of the player. */
    Animation<TextureRegion> idleAnimation;
    /** The running animation of the player. */
    Animation<TextureRegion> runAnimation;
    /** The reversed running animation of the player. */
    Animation<TextureRegion> runAnimationRevert;
    /** The dash animation of the player. */
    Animation<TextureRegion> dashAnimation;
    /** The spawn animation of the player. */
    Animation<TextureRegion> spawnAnimation;
    /** The blood effect animation. */
    Animation<TextureRegion> bloodEffect;

    /** The state time of the blood effect animation. */
    float bloodStateTime=0f;

    /** The number of columns in the animation. */
    static final int FRAME_COLS = 1;
    /** The number of rows in the animation. */
    static final int FRAME_ROWS = 4;

    /**
     * Constructs a new Player object.
     *
     * @param spawn The spawn point of the player.
     * @param name The name of the player.
     */
    public Player(Point spawn,String name){

        this.name = name;

        // Load player animations
        this.idleAnimation = Utils.getAnimation("assets/player/knight_1_idle.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimation = Utils.getAnimation("assets/player/knight_1_run.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimationRevert =  Utils.getAnimationRevert("assets/player/knight_1_run.png", FRAME_COLS, FRAME_ROWS);
        this.dashAnimation = Utils.getAnimation("assets/effects/dash.png", FRAME_COLS, 5, 0.07f);
        this.spawnAnimation = Utils.getAnimation("assets/effects/player_spawn.png", 1, 16, 0.06f);
        this.bloodEffect = Utils.getAnimation("assets/effects/blood.png", 6, 4, 0.02f);

        // Get player texture height and width
        this.texture_width = Utils.getAnimationWidth(this.idleAnimation);
        this.texture_height = Utils.getAnimationHeight(this.idleAnimation);

        // Set player spawn position
        this.pos = new Point(spawn.x-((float) this.texture_width /2), spawn.y);
        this.lastPos = this.pos;

        // Set default values
        this.max_hp=100;
        this.hp=100;
        this.strength=10;
        this.speed=40f;
        this.max_stamina=100;
        this.stamina = 100;

        // Set default weapon
        this.currentWeapon = MeleeWeapon.RUSTY_SWORD();
    }

    /**
     * Spawns the player, playing the spawn animation.
     */
    public void spawnPlayer(){
        InGameScreen.stateTime=0f;
        this.isPlayerSpawning=true;
    }

    /**
     * gère la mort du player
     */
    public void playerDeath(){
        //default values
        this.max_hp=100;
        this.hp=100;
        this.strength=10;
        this.speed=40f;
        this.max_stamina=100;
        this.stamina = 100;
        this.currentWeapon = MeleeWeapon.RUSTY_SWORD();
    }

    /**
     * sert a déplacer le joueur à un poit précis
     * @param pos
     */
    public void setPos(Point pos){
        this.pos = pos;
    }

    /**
     * Retrieves the center point of the player.
     *
     * @return The center point.
     */
    public Point center(){
        return new Point(this.pos.x+((float) this.texture_width /2), this.pos.y+((float) this.texture_height /4));
    }

    /**
     * Retrieves the center point of the player's texture.
     *
     * @return The texture center point.
     */
    public Point textureCenter(){
        return new Point(this.pos.x+((float) this.texture_width /2), this.pos.y+((float) this.texture_height /2));
    }

    /**
     * Vérifie si le player peut aller à sa prochaine position avec les collisions
     * @param newPos
     * @param map
     * @return
     */
    public boolean canMove(Point newPos, Map map){
        Point hitboxTopLeft = newPos.add(3, this.texture_height-5 - Floor.TEXTURE_HEIGHT);
        Point hitboxBottomLeft = newPos.add(3, 0);
        Point hitboxTopRight = newPos.add(this.texture_width-3, this.texture_height-5 - Floor.TEXTURE_HEIGHT);
        Point hitboxBottomRight = newPos.add(this.texture_width-3, 0);

        Point[] points = {hitboxTopLeft, hitboxBottomLeft, hitboxTopRight, hitboxBottomRight};

        return map.arePointsOnFloor(points);
    }

    /**
     * Moves the player.
     *
     * @param x The movement along the x-axis.
     * @param y The movement along the y-axis.
     * @param map The game map.
     */
    public void move(float x, float y, Map map){
        if (canMove(this.pos.add(x*Gdx.graphics.getDeltaTime()*this.speed, y*Gdx.graphics.getDeltaTime()*this.speed), map)){
            this.lastPos = this.pos;
            this.pos = this.pos.add(x*Gdx.graphics.getDeltaTime()*this.speed, y*Gdx.graphics.getDeltaTime()*this.speed);
        }
    }

    /**
     * Checks if the player has enough stamina to perform an action.
     *
     * @param amount The amount of stamina needed.
     * @return True if the player has enough stamina, false otherwise.
     */
    public boolean canActionWithStamina(int amount){
        return this.stamina-amount >=0;
    }

    /**
     * Performs a dash action.
     *
     * @param map The game map.
     */
    public void dash(Map map){
        if (this.isDashing){
            if (this.dashFinalPoint == null && this.dashOrientation==null){
                Point mousePos = Utils.mousePosUnproject(Camera.camera);
                this.dashFinalPoint = Utils.getPointDirection(this.pos, mousePos, Player.DASH_DISTANCE);
                this.dashStartPoint = this.pos;
                this.dashOrientation = Point.getOrientation(this.pos, this.dashFinalPoint);
                this.dashTimer = LocalDateTime.now();
                this.dashStateTime = 0f;
            }else {
                if (!Point.isPointExceeded(this.pos, this.dashFinalPoint, this.dashOrientation)){
                    assert this.dashFinalPoint != null;
                    Point nextPos = Utils.getPointDirection(this.pos, this.dashFinalPoint, Player.DASH_SPEED*Gdx.graphics.getDeltaTime());

                    boolean canMove1 = true;

                    if (canMove(new Point(this.pos.x, nextPos.y), map)){
                        this.pos = new Point(this.pos.x, nextPos.y);
                    } else {
                        canMove1=false;
                    }
                    if (canMove(new Point(nextPos.x, this.pos.y), map)){
                        this.pos = new Point(nextPos.x, this.pos.y);
                    } else if (!canMove1) {
                        this.isDashing=false;
                        this.dashFinalPoint=null;
                    }
                } else {
                    this.isDashing=false;
                    this.dashFinalPoint=null;
                }
            }
        } else if (!this.canDash && this.dashTimer.plusSeconds(Player.DASH_PAUSE).isBefore(LocalDateTime.now())) {
            this.dashStartPoint=null;
            this.dashOrientation=null;
            this.canDash=true;
        } else if (Gdx.input.isKeyJustPressed(Keybinds.DASH_KEY) && this.canDash && this.canActionWithStamina(10)) {
            this.canDash=false;
            this.isDashing=true;
            this.stamina-=Player.DASH_STAMINA_USAGE;
            this.staminaTimer = LocalDateTime.now();
        }
    }

    /**
     * Initiates an attack action.
     */
    public void attack() {
        if (canAttack && !this.isAttacking && this.canActionWithStamina(Player.ATTACK_STAMINA_USAGE)){
            this.isAttacking=true;
            this.canAttack = false;

            this.stamina-=Player.ATTACK_STAMINA_USAGE;
            this.staminaTimer = LocalDateTime.now();

            this.attackDirection = Utils.mousePosUnproject(Camera.camera);
            this.attackRotation = Utils.getAngleWithPoint(this.center(), this.attackDirection)-this.currentWeapon.attackRange/2;
            this.attackEndRotation = this.attackRotation+this.currentWeapon.attackRange;
        }
    }

    /**
     * redonne de l'énergie au joueur au bout d'un certain temps
     */
    public void regainStamina(){
        if (this.staminaTimer==null || this.staminaTimer.plusSeconds(5).isBefore(LocalDateTime.now())){
            if (this.stamina < this.max_stamina){
                this.stamina = this.stamina + 20*Gdx.graphics.getDeltaTime();
                if (this.stamina>100){
                    this.stamina=100;
                }
            }
        }
    }

    /**
     * Checks if the player has hit a monster.
     */
    public void checkHit(){
        Rectangle meleeWeaponRectangle = this.currentWeapon.sprite.getBoundingRectangle();
        for (Monster m : Map.monsters){
            Rectangle mRectangle = m.sprite.getBoundingRectangle();
            if (meleeWeaponRectangle.overlaps(mRectangle)){
                boolean damaged = m.hit(this);
                if (damaged){
                    this.currentWeapon.resistance-=1;
                    System.out.println(this.currentWeapon.resistance);
                    if (currentWeapon.resistance<=0 && currentWeapon.resistance>-100){
                        this.currentWeapon = MeleeWeapon.RUSTY_SWORD();
                    }
                }
            }
        }
    }

    /**
     * Displays the player's attack animation.
     *
     * @param batch The sprite batch to draw the animation.
     */
    public void showAttack(SpriteBatch batch){
        if (this.attackRotation <= this.attackEndRotation){
            this.attackPos = Point.getPosWithAngle(this.center(), Player.PLAYER_MELEE_WEAPON_DISTANCE, this.attackRotation);

            this.currentWeapon.sprite.setPosition(this.attackPos.x-this.currentWeapon.width/2, this.attackPos.y);
            this.currentWeapon.sprite.setRotation(this.attackRotation-90f);

            this.currentWeapon.sprite.draw(batch);

            this.checkHit();

            this.attackRotation += this.currentWeapon.attackSpeed*Gdx.graphics.getDeltaTime()*1000;
            this.attackPos = Point.getPosWithAngle(this.center(), Player.PLAYER_MELEE_WEAPON_DISTANCE, this.attackRotation);
        }else if (this.attackTimer==null){
            this.isAttacking = false;
            this.attackTimer = LocalDateTime.now();
        } else if (this.attackTimer.plusNanos((long) (Player.ATTACK_PAUSE*1000000)).isBefore(LocalDateTime.now())) {
            this.canAttack=true;
            this.attackTimer=null;
        }
    }

    /**
     * Retrieves the player's orientation.
     */
    public void getOrientation(){
        Point mousePos = Utils.mousePosUnproject(Camera.camera);
        this.flip= mousePos.x < this.center().x;
    }

    /**
     * Retrieves keyboard input for player movement.
     *
     * @param map The game map.
     */
    public void getKeyboardMove(Map map){
        if (!this.isDashing){
            if (Gdx.input.isKeyPressed(Keybinds.UP_KEY)){
                this.move(0, 1, map);
                this.isRunning=true;
            } else if (Gdx.input.isKeyPressed(Keybinds.DOWN_KEY)){
                this.move(0, -1, map);
                this.isRunning=true;
            } if (Gdx.input.isKeyPressed(Keybinds.LEFT_KEY)){
                this.move(-1, 0, map);
                this.isRunning=true;
            } else if (Gdx.input.isKeyPressed(Keybinds.RIGHT_KEY)){
                this.move(1, 0, map);
                this.isRunning=true;
            } if (!(Gdx.input.isKeyPressed(Keybinds.UP_KEY) || Gdx.input.isKeyPressed(Keybinds.DOWN_KEY) || Gdx.input.isKeyPressed(Keybinds.LEFT_KEY) || Gdx.input.isKeyPressed(Keybinds.RIGHT_KEY))){
                this.isRunning=false;
            } if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.attack();
            }
        }
    }

    /**
     * Draws the player.
     *
     * @param batch The sprite batch to draw the player.
     */
    public void draw(SpriteBatch batch){

        if (hasPlayerSpawn) {
            TextureRegion currentFrame;
            if (this.isRunning){
                if (!flip && this.lastPos.x > this.pos.x){
                    currentFrame = this.runAnimationRevert.getKeyFrame(InGameScreen.stateTime, true);
                } else if (flip && this.lastPos.x < this.pos.x) {
                    currentFrame = this.runAnimationRevert.getKeyFrame(InGameScreen.stateTime, true);
                } else {
                    currentFrame = this.runAnimation.getKeyFrame(InGameScreen.stateTime, true);
                }
            }else {
                currentFrame = this.idleAnimation.getKeyFrame(InGameScreen.stateTime, true);
            }

            // Dash animation
            if (!this.canDash && this.dashOrientation!=null && !this.dashAnimation.isAnimationFinished(this.dashStateTime)){
                this.dashStateTime += Gdx.graphics.getDeltaTime();
                TextureRegion currentDashFrame = this.dashAnimation.getKeyFrame(this.dashStateTime, false);
                batch.draw(currentDashFrame, this.dashOrientation.x >= 0 ? this.dashStartPoint.x- (float) currentDashFrame.getRegionWidth() /4 : this.dashStartPoint.x+ (float) currentDashFrame.getRegionWidth()/2, this.dashStartPoint.y, this.dashOrientation.x >= 0 ? (float) currentDashFrame.getRegionWidth() /2 : (float) -currentDashFrame.getRegionWidth() /2, (float) currentDashFrame.getRegionHeight() /2);
            }
            batch.draw(currentFrame, flip ? this.pos.x+this.texture_width : this.pos.x, this.pos.y, this.flip ? -this.texture_width : this.texture_width, this.texture_height);

            // Blood animation
            if (bloodStateTime>=0){
                bloodStateTime+= Gdx.graphics.getDeltaTime();
                TextureRegion currentBloodFrame = this.bloodEffect.getKeyFrame(bloodStateTime, false);
                batch.draw(currentBloodFrame,
                        this.pos.x - (float) currentBloodFrame.getRegionWidth() /4 + (float) this.texture_width/2,
                        this.pos.y - (float) currentBloodFrame.getRegionHeight() /4 + (float) this.texture_height/2,
                        (float) currentBloodFrame.getRegionWidth() /2,
                        (float) currentBloodFrame.getRegionHeight() /2
                );
            }

            if (this.bloodEffect.isAnimationFinished(bloodStateTime)){
                this.bloodStateTime=-1;
            }

        }

        if (isPlayerSpawning){
            TextureRegion currentSpawnFrame = this.spawnAnimation.getKeyFrame(InGameScreen.stateTime, false);
            batch.draw(currentSpawnFrame, this.center().x- (float) currentSpawnFrame.getRegionWidth() /2, this.pos.y);
            if (this.spawnAnimation.getKeyFrameIndex(InGameScreen.stateTime) == 3){
                this.hasPlayerSpawn=true;
                this.isPlayerSpawning=false;
            }
        }
    }

    /**
     * gère les dégàts fais au joueur
     * @param damage
     */
    public void takeDamage(int damage){
        this.hp -= damage;
        this.bloodStateTime=0f;
    }
}
