package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import fr.studiokakou.kakouquest.map.Point;

public class Camera {
    public OrthographicCamera camera;
    Player player;

    public static float DEFAULT_ZOOM = (float) (Gdx.graphics.getHeight() * 3) /720;
    public static float CAM_Y_DISTANCE = 53;
    public static float CAM_X_DISTANCE = 95;

    public float zoom;

    public Camera(Player player){
        this.player = player;

        //toujours avoir le même zoom peut importe la taille de l'écran
        this.zoom = Camera.DEFAULT_ZOOM;

        //init camera
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth()/this.zoom, Gdx.graphics.getHeight()/this.zoom);
        this.camera.position.x = this.player.center().x;
        this.camera.position.y = this.player.center().y;
    }

    public void update(){
        if (this.camera.position.x+Camera.CAM_X_DISTANCE < this.player.center().x){
            this.camera.position.x=this.player.center().x-Camera.CAM_X_DISTANCE;
        }
        if (this.camera.position.x-Camera.CAM_X_DISTANCE > this.player.center().x){
            this.camera.position.x=this.player.center().x+Camera.CAM_X_DISTANCE;
        }
        if (this.camera.position.y+Camera.CAM_Y_DISTANCE < this.player.center().y){
            this.camera.position.y=this.player.center().y-Camera.CAM_Y_DISTANCE;
        }
        if (this.camera.position.y-Camera.CAM_Y_DISTANCE > this.player.center().y){
            this.camera.position.y=this.player.center().y+Camera.CAM_Y_DISTANCE;
        }
        this.camera.update();
    }

}
