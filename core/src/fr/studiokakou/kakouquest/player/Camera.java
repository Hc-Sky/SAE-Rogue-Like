package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Represents a camera object used for rendering the game view.
 */
public class Camera {
    /** The orthographic camera instance. */
    public static OrthographicCamera camera;

    /** The player object associated with the camera. */
    Player player;

    /** Default zoom value for the camera. */
    public static float DEFAULT_ZOOM = (float) (Gdx.graphics.getHeight() * 2.5) / 720;

    /** Distance in y-axis between the camera and the player. */
    public static float CAM_Y_DISTANCE = 53;

    /** Distance in x-axis between the camera and the player. */
    public static float CAM_X_DISTANCE = 95;

    /** The zoom level of the camera. */
    public float zoom;

    /**
     * Constructs a new Camera object.
     *
     * @param player The player object to associate with the camera.
     */
    public Camera(Player player){
        this.player = player;

        // Always maintain the same zoom regardless of screen size
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
     * Updates the camera position based on the player's movement.
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
