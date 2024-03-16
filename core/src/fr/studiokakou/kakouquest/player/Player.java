package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import fr.studiokakou.kakouquest.map.Point;
import fr.studiokakou.kakouquest.utils.Utils;

public class Player {

    //player pos
    Point pos;

    //player stats
    public String name;
    public int hp;
    public int strength;
    public float speed;

    //dash infos
    boolean isDashing = false;
    static float dashDistance = 100f;
    Point dashFinalPoint;


    //player texture size
    public int texture_height;
    public int texture_width;

    //animation var
    float stateTime;
    boolean flip=false;       //false = regard à droite
    boolean isRunning=false;

    //animations
    Animation<TextureRegion> idleAnimation;
    Animation<TextureRegion> runAnimation;
    static final int FRAME_COLS = 1, FRAME_ROWS = 4;

    public Player(float x, float y, String name){

        this.name = name;

        this.idleAnimation = Utils.getAnimation("assets/player/knight_1_idle.png", FRAME_COLS, FRAME_ROWS);
        this.runAnimation = Utils.getAnimation("assets/player/knight_1_run.png", FRAME_COLS, FRAME_ROWS);
        this.stateTime=0f;

        this.texture_width = Utils.getAnimationWidth(this.idleAnimation);
        this.texture_height = Utils.getAnimationHeight(this.idleAnimation);

        this.pos = new Point(x-((float) this.texture_width /2), y);

        //default values
        this.hp=100;
        this.strength=10;
        this.speed=50f;
    }

    public Point center(){
        return new Point(this.pos.x+((float) this.texture_width /2), this.pos.y+((float) this.texture_height /2));
    }

    public void move(float x, float y){
        this.pos = this.pos.add(x*Gdx.graphics.getDeltaTime()*this.speed, y*Gdx.graphics.getDeltaTime()*this.speed);
    }

    public void dash(OrthographicCamera camera){
        if (this.isDashing){
            if (this.dashFinalPoint == null){
                Point mousePos = Utils.mousePosUnproject(camera);
                this.dashFinalPoint = Utils.getPointDirection(this.pos, mousePos, Player.dashDistance);
            }else {
                if (Utils.getDistance(this.pos, this.dashFinalPoint) > 40){
                    this.pos = Utils.getPointDirection(this.pos, this.dashFinalPoint, 400f*Gdx.graphics.getDeltaTime());
                } else {
                    this.isDashing=false;
                    this.dashFinalPoint=null;
                }
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.E)) {
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
            } if (Gdx.input.isKeyPressed(Input.Keys.P)){    //pour dev uniquement à supprimer
                Gdx.app.exit();
            }
        }
    }

    public void draw(SpriteBatch batch){
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame;
        if (this.isRunning){
            currentFrame = this.runAnimation.getKeyFrame(stateTime, true);
        }else {
            currentFrame = this.idleAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, flip ? this.pos.x+this.texture_width : this.pos.x, this.pos.y, this.flip ? -this.texture_width : this.texture_width, this.texture_height);
    }
}
