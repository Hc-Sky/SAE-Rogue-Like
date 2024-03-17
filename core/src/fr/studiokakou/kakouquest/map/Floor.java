package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.utils.Utils;

public class Floor {
    Point pos;

    //texture defaults
    public static float TEXTURE_WIDTH = 16;
    public static float TEXTURE_HEIGHT = 16;
    public static String[] POSSIBLE_TEXTURE = {"assets/map/floor_1.png", "assets/map/floor_2.png", "assets/map/floor_3.png", "assets/map/floor_4.png", "assets/map/floor_5.png", "assets/map/floor_6.png", "assets/map/floor_7.png", "assets/map/floor_8.png", };

    //texture info
    public Texture texture;

    public Floor(float x, float y){
        this.pos = new Point(x, y);

        if (Utils.randint(0,4) == 0){
            this.texture = new Texture(Floor.POSSIBLE_TEXTURE[Utils.randint(1,Floor.POSSIBLE_TEXTURE.length-1)]);
        }else {
            this.texture = new Texture(Floor.POSSIBLE_TEXTURE[0]);
        }

    }
}
