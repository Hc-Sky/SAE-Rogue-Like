package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import fr.studiokakou.kakouquest.entity.Test;
import fr.studiokakou.kakouquest.keybinds.Keybinds;
import fr.studiokakou.kakouquest.map.Map;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.utils.Utils;
import fr.studiokakou.kakouquest.weapon.MeleeWeapon;

import java.time.LocalDateTime;

public class Player {

    //player pos
    Point pos;
    Point lastPos;

    //player stats
    public String name;
    public int hp;
    public int strength;
    public float speed;
    public int stamina;

    //weapon
    public MeleeWeapon currentWeapon;

    //dash infos
    boolean isDashing = false;
    boolean canDash = true;
    Point dashFinalPoint;
    Point dashStartPoint;
    Point dashOrientation;
    LocalDateTime dashTimer;
    float dashStateTime;

    //attack var
    public static float PLAYER_MELEE_WEAPON_DISTANCE=10f;
    public static float ATTACK_PAUSE = 200f; //en millisecondes
    static int ATTACK_STAMINA_USAGE = 2;
    LocalDateTime attackTimer;
    public boolean isAttacking=false;
    public boolean canAttack = true;
    Point attackDirection;
    Point attackPos;
    float attackEndRotation;
    float attackRotation;

    //dash stats
    static float DASH_DISTANCE = 50f;
    static float DASH_SPEED = 500f;
    static long DASH_PAUSE = 3;   //en secondes
    static int DASH_STAMINA_USAGE = 10;

    //player texture size
    public int texture_height;
    public int texture_width;

    //animation var
    float stateTime;
    boolean flip=false;       //false = regard à droite
    boolean isRunning=false;
    public boolean isPlayerSpawning=false;
    public boolean hasPlayerSpawn=false;

    //animations
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> runAnimation;
    Animation<TextureRegion> runAnimationRevert;
    Animation<TextureRegion> dashAnimation;
    Animation<TextureRegion> spawnAnimation;
    static final int FRAME_COLS = 1, FRAME_ROWS = 4;

    public Player(float x, float y,String name){

        this.name = name;

        //spawn player pos
        this.pos = new Point(x-((float) this.texture_width /2), y);
        this.lastPos = this.pos;

        //player animations
        this.idleAnimation = Utils.getAnimation("assets/player/knight_1_idle.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimation = Utils.getAnimation("assets/player/knight_1_run.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimationRevert =  Utils.getAnimationRevert("assets/player/knight_1_run.png", FRAME_COLS, FRAME_ROWS);
        this.dashAnimation = Utils.getAnimation("assets/effects/dash.png", FRAME_COLS, 5, 0.07f);
        this.spawnAnimation = Utils.getAnimation("assets/effects/player_spawn.png", 1, 16, 0.06f);
        this.stateTime=0f;

        //get player texture height and width
        this.texture_width = Utils.getAnimationWidth(this.idleAnimation);
        this.texture_height = Utils.getAnimationHeight(this.idleAnimation);

        //default values
        this.hp=100;
        this.strength=10;
        this.speed=40f;
        this.stamina = 100;

        //default weapon
        this.currentWeapon = MeleeWeapon.DEV_SWORD();
    }

    public void spawnPlayer(){   //used to play the spawning animation
        this.stateTime=0f;
        this.isPlayerSpawning=true;
    }

    public Point center(){
        return new Point(this.pos.x+((float) this.texture_width /2), this.pos.y+((float) this.texture_height /4));
    }
    public Point textureCenter(){
        return new Point(this.pos.x+((float) this.texture_width /2), this.pos.y+((float) this.texture_height /2));
    }

    public void move(float x, float y){
        this.lastPos = this.pos;
        this.pos = this.pos.add(x*Gdx.graphics.getDeltaTime()*this.speed, y*Gdx.graphics.getDeltaTime()*this.speed);
    }

    public boolean canActionWithStamina(int amount){
        return this.stamina-amount >=0;
    }

    public void dash(){    //used for the dash animation
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
                    this.pos = Utils.getPointDirection(this.pos, this.dashFinalPoint, Player.DASH_SPEED*Gdx.graphics.getDeltaTime());
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
            this.stamina-=10;
        }

    }

    public void attack() {
        if (canAttack && !this.isAttacking && this.canActionWithStamina(Player.ATTACK_STAMINA_USAGE)){
            this.isAttacking=true;
            this.canAttack = false;
            this.stamina-=Player.ATTACK_STAMINA_USAGE;
            this.attackDirection = Utils.mousePosUnproject(Camera.camera);
            this.attackRotation = Utils.getAngleWithPoint(this.center(), this.attackDirection)-this.currentWeapon.attackRange/2;
            this.attackEndRotation = this.attackRotation+this.currentWeapon.attackRange;
        }
    }

    public void checkHit(Sprite meleeWeapon){
        Rectangle meleeWeaponRectangle = meleeWeapon.getBoundingRectangle();
        for (Test t : Map.tests){
            Rectangle tRectangle = t.sprite.getBoundingRectangle();
            if (meleeWeaponRectangle.overlaps(tRectangle)){
                boolean damaged = t.hit(this);
                if (damaged){
                    this.currentWeapon.resistance-=1;
                }
            }
        }
    }

    public void showAttack(SpriteBatch batch){
        if (this.attackRotation <= this.attackEndRotation){
            this.attackPos = Point.getPosWithAngle(this.center(), Player.PLAYER_MELEE_WEAPON_DISTANCE, this.attackRotation);

            this.currentWeapon.sprite.setPosition(this.attackPos.x-this.currentWeapon.width/2, this.attackPos.y);
            this.currentWeapon.sprite.setRotation(this.attackRotation-90f);

            this.currentWeapon.sprite.draw(batch);

            this.checkHit(this.currentWeapon.sprite);

            this.attackRotation += this.currentWeapon.attackSpeed*Gdx.graphics.getDeltaTime()*1000;
            this.attackPos = Point.getPosWithAngle(this.center(), Player.PLAYER_MELEE_WEAPON_DISTANCE, this.attackRotation);
        }else if (this.attackTimer==null){
            this.isAttacking = false;
            this.attackTimer = LocalDateTime.now();
            Map.clearAttackedPlayers(this);
        } else if (this.attackTimer.plusNanos((long) (Player.ATTACK_PAUSE*1000000)).isBefore(LocalDateTime.now())) {
            this.canAttack=true;
            this.attackTimer=null;
        }
    }

    public void getOrientation(){
        Point mousePos = Utils.mousePosUnproject(Camera.camera);
        this.flip= mousePos.x < this.center().x;
    }

    public void getKeyboardMove(){
        if (!this.isDashing){
            if (Gdx.input.isKeyPressed(Keybinds.UP_KEY)){
                this.move(0, 1);
                this.isRunning=true;
            } else if (Gdx.input.isKeyPressed(Keybinds.DOWN_KEY)){
                this.move(0, -1);
                this.isRunning=true;
            } if (Gdx.input.isKeyPressed(Keybinds.LEFT_KEY)){
                this.move(-1, 0);
                this.isRunning=true;
            } else if (Gdx.input.isKeyPressed(Keybinds.RIGHT_KEY)){
                this.move(1, 0);
                this.isRunning=true;
            } if (!(Gdx.input.isKeyPressed(Keybinds.UP_KEY) || Gdx.input.isKeyPressed(Keybinds.DOWN_KEY) || Gdx.input.isKeyPressed(Keybinds.LEFT_KEY) || Gdx.input.isKeyPressed(Keybinds.RIGHT_KEY))){
                this.isRunning=false;
            } if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)){
                this.attack();
            }
        }
    }

    public void draw(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();

        if (hasPlayerSpawn) {
            TextureRegion currentFrame;
            if (this.isRunning){
                if (!flip && this.lastPos.x > this.pos.x){
                    currentFrame = this.runAnimationRevert.getKeyFrame(stateTime, true);
                } else if (flip && this.lastPos.x < this.pos.x) {
                    currentFrame = this.runAnimationRevert.getKeyFrame(stateTime, true);
                } else {
                    currentFrame = this.runAnimation.getKeyFrame(stateTime, true);
                }
            }else {
                currentFrame = this.idleAnimation.getKeyFrame(stateTime, true);
            }

            //dash animation
            if (!this.canDash && this.dashOrientation!=null && !this.dashAnimation.isAnimationFinished(this.dashStateTime)){
                this.dashStateTime += Gdx.graphics.getDeltaTime();
                TextureRegion currentDashFrame = this.dashAnimation.getKeyFrame(this.dashStateTime, false);
                batch.draw(currentDashFrame, this.dashOrientation.x >= 0 ? this.dashStartPoint.x- (float) currentDashFrame.getRegionWidth() /4 : this.dashStartPoint.x+ (float) currentDashFrame.getRegionWidth()/2, this.dashStartPoint.y, this.dashOrientation.x >= 0 ? (float) currentDashFrame.getRegionWidth() /2 : (float) -currentDashFrame.getRegionWidth() /2, (float) currentDashFrame.getRegionHeight() /2);
            }

            batch.draw(currentFrame, flip ? this.pos.x+this.texture_width : this.pos.x, this.pos.y, this.flip ? -this.texture_width : this.texture_width, this.texture_height);
        }

        if (isPlayerSpawning){
            TextureRegion currentSpawnFrame = this.spawnAnimation.getKeyFrame(stateTime, false);
            batch.draw(currentSpawnFrame, this.center().x- (float) currentSpawnFrame.getRegionWidth() /2, this.pos.y);
            if (this.spawnAnimation.getKeyFrameIndex(stateTime) == 3){
                this.hasPlayerSpawn=true;
            }
            if (this.spawnAnimation.isAnimationFinished(stateTime)){
                this.isPlayerSpawning = false;
            }
        }

        if (!this.canAttack) {
            this.showAttack(batch);
        }
    }
}
