package fr.studiokakou.kakouquest.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import fr.studiokakou.kakouquest.map.Point;

public class Camera {
    public OrthographicCamera camera;
    Player player;

    public static float DEFAULT_ZOOM = (float) (Gdx.graphics.getHeight() * 3) /720;

    public float zoom;

    public Camera(Player player){
        this.player = player;

        //toujours avoir le même zoom peut importe la taille de l'écran
        this.zoom = Camera.DEFAULT_ZOOM;

        //init camera
        this.camera = new OrthographicCamera(Gdx.graphics.getWidth()/this.zoom, Gdx.graphics.getHeight()/this.zoom);
        this.update();
    }

    public void update(){
        this.camera.position.x=this.player.center().x;
        this.camera.position.y=this.player.center().y;
        this.camera.update();
    }

}
