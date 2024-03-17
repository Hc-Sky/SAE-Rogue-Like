package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.utils.Utils;

import java.time.LocalDateTime;

public class Player {

    //player pos
    Point pos = new Point(-1000, -1000);
    Point lastPos;

    //player stats
    public String name;
    public int hp;
    public int strength;
    public float speed;

    //dash infos
    boolean isDashing = false;
    boolean canDash = true;
    Point dashFinalPoint;
    Point dashStartPoint;
    Point dashOrientation;
    LocalDateTime dashTimer;
    float dashStateTime;

    //dash stats
    static float DASH_DISTANCE = 50f;
    static float DASH_SPEED = 500f;
    static long DASH_PAUSE = 3;

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

    public Player(String name){

        this.name = name;

        this.idleAnimation = Utils.getAnimation("assets/player/knight_1_idle.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimation = Utils.getAnimation("assets/player/knight_1_run.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimationRevert =  Utils.getAnimationRevert("assets/player/knight_1_run.png", FRAME_COLS, FRAME_ROWS);
        this.dashAnimation = Utils.getAnimation("assets/effects/dash.png", FRAME_COLS, 5, 0.07f);
        this.spawnAnimation = Utils.getAnimation("assets/effects/player_spawn.png", 1, 16, 0.06f);
        this.stateTime=0f;

        this.texture_width = Utils.getAnimationWidth(this.idleAnimation);
        this.texture_height = Utils.getAnimationHeight(this.idleAnimation);

        //default values
        this.hp=100;
        this.strength=10;
        this.speed=40f;
    }

    public void spawnPlayer(float x, float y, Camera camera){
        this.pos = new Point(x-((float) this.texture_width /2), y);
        this.lastPos = this.pos;
        camera.centerPlayer();
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

    public void dash(OrthographicCamera camera){
        if (this.isDashing){
            if (this.dashFinalPoint == null && this.dashOrientation==null){
                Point mousePos = Utils.mousePosUnproject(camera);
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
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.SHIFT_LEFT) && this.canDash) {
            this.canDash=false;
            this.isDashing=true;
        }

    }

    public void getOrientation(OrthographicCamera camera){
        Point mousePos = Utils.mousePosUnproject(camera);

        this.flip= mousePos.x < this.center().x;
    }

    public void getKeyboardMove(){
        if (!this.isDashing){
            if (Gdx.input.isKeyPressed(Input.Keys.W)){
                this.move(0, 1);
                this.isRunning=true;
            } if (Gdx.input.isKeyPressed(Input.Keys.S)){
                this.move(0, -1);
                this.isRunning=true;
            } if (Gdx.input.isKeyPressed(Input.Keys.A)){
                this.move(-1, 0);
                this.isRunning=true;
            } if (Gdx.input.isKeyPressed(Input.Keys.D)){
                this.move(1, 0);
                this.isRunning=true;
            } if (!(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.D))){
                this.isRunning=false;
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
    }
}
