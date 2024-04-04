package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Represents a camera used to follow the player.
 */
public class Camera {
    /** The camera instance. */
    public static OrthographicCamera camera;

    /** The player being followed by the camera. */
    Player player;

    /** Default camera zoom. */
    public static float DEFAULT_ZOOM = (float) (Gdx.graphics.getHeight() * 2.5) /720;
    //public static float DEFAULT_ZOOM = 0.8f;

    /** The distance in y-axis of the camera from the player. */
    public static float CAM_Y_DISTANCE = 53;

    /** The distance in x-axis of the camera from the player. */
    public static float CAM_X_DISTANCE = 95;

    /** The zoom level of the camera. */
    public float zoom;

    /**
     * Constructs a new Camera object.
     *
     * @param player The player to follow.
     */
    public Camera(Player player){
        this.player = player;
        this.zoom = Camera.DEFAULT_ZOOM;

        // Initialize camera
        Camera.camera = new OrthographicCamera(Gdx.graphics.getWidth() / this.zoom, Gdx.graphics.getHeight() / this.zoom);
        Camera.camera.position.x = this.player.center().x;
        Camera.camera.position.y = this.player.center().y;
    }

    /**
     * Centers the camera on the player.
     */
    public void centerPlayer(){
        Camera.camera.position.x = this.player.center().x;
        Camera.camera.position.y = this.player.center().y;
    }

    /**
     * Updates the camera position.
     */
    public void update(){
        if (Camera.camera.position.x + Camera.CAM_X_DISTANCE < this.player.center().x){
            Camera.camera.position.x = this.player.center().x - Camera.CAM_X_DISTANCE;
        }
        if (Camera.camera.position.x - Camera.CAM_X_DISTANCE > this.player.center().x){
            Camera.camera.position.x = this.player.center().x + Camera.CAM_X_DISTANCE;
        }
        if (Camera.camera.position.y + Camera.CAM_Y_DISTANCE < this.player.center().y){
            Camera.camera.position.y = this.player.center().y - Camera.CAM_Y_DISTANCE;
        }
        if (Camera.camera.position.y - Camera.CAM_Y_DISTANCE > this.player.center().y){
            Camera.camera.position.y = this.player.center().y + Camera.CAM_Y_DISTANCE;
        }
        Camera.camera.update();
    }
}
