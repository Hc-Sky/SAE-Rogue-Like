package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Le type Camera. Cette classe est utilisée pour créer un objet Camera.
 *
 * @version 1.0
 */
public class Camera {
    /**
     * La caméra.
     */
    public static OrthographicCamera camera;
    /**
     * le joueur.
     */
    Player player;

    /**
     * paramètres de la caméra par défaut
     */
    public static float DEFAULT_ZOOM = (float) (Gdx.graphics.getHeight() * 2.5) /720;
    //public static float DEFAULT_ZOOM = 1f;
    /**
     * la distance en y de la caméra par rapport au joueur.
     */
    public static float CAM_Y_DISTANCE = 53;
    /**
     * la distance en x de la caméra par rapport au joueur.
     */
    public static float CAM_X_DISTANCE = 95;

    /**
     * le zoom de la caméra.
     */
    public float zoom;

    /**
     * Constructeur de Camera.
     * Sert à créer un objet Camera.
     *
     * @param player the player
     */
    public Camera(Player player){
        this.player = player;

        //toujours avoir le même zoom peut importe la taille de l'écran
        this.zoom = Camera.DEFAULT_ZOOM;

        //init camera
        Camera.camera = new OrthographicCamera(Gdx.graphics.getWidth()/this.zoom, Gdx.graphics.getHeight()/this.zoom);
        Camera.camera.position.x = this.player.center().x;
        Camera.camera.position.y = this.player.center().y;
    }

    /**
     * Centre le joueur.
     */
    public void centerPlayer(){
        Camera.camera.position.x = this.player.center().x;
        Camera.camera.position.y = this.player.center().y;
    }

    /**
     * Update la caméra.
     */
    public void update(){
        if (Camera.camera.position.x+Camera.CAM_X_DISTANCE < this.player.center().x){
            Camera.camera.position.x=this.player.center().x-Camera.CAM_X_DISTANCE;
        }
        if (Camera.camera.position.x-Camera.CAM_X_DISTANCE > this.player.center().x){
            Camera.camera.position.x=this.player.center().x+Camera.CAM_X_DISTANCE;
        }
        if (Camera.camera.position.y+Camera.CAM_Y_DISTANCE < this.player.center().y){
            Camera.camera.position.y=this.player.center().y-Camera.CAM_Y_DISTANCE;
        }
        if (Camera.camera.position.y-Camera.CAM_Y_DISTANCE > this.player.center().y){
            Camera.camera.position.y=this.player.center().y+Camera.CAM_Y_DISTANCE;
        }
        Camera.camera.update();
    }

}
