package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.Texture;
import fr.studiokakou.kakouquest.utils.Utils;

import java.util.ArrayList;

/**
 * The type Floor.
 */
public class Floor {
    /**
     * la position.
     */
    Point pos;

    /**
     * la largeur de la texture.
     */
//texture defaults
    public static float TEXTURE_WIDTH = 16;
    /**
     * la hauteur de la texture.
     */
    public static float TEXTURE_HEIGHT = 16;
    /**
     * la liste des textures possibles.
     */
    public static Texture[] POSSIBLE_TEXTURE = {new Texture("assets/map/floor_1.png"), new Texture("assets/map/floor_2.png"), new Texture("assets/map/floor_3.png"), new Texture("assets/map/floor_4.png"), new Texture("assets/map/floor_5.png"), new Texture("assets/map/floor_6.png"), new Texture("assets/map/floor_7.png"), new Texture("assets/map/floor_8.png"), };

    /**
     * La texture.
     */
//texture info
    public Texture texture;

    /**
     * Constructeur de Floor.
     * Sert à créer un objet Floor.
     *
     * @param x the x
     * @param y the y
     */
    public Floor(float x, float y){
        this.pos = new Point(x, y);

        if (Utils.randint(0,4) == 0){
            this.texture = Floor.POSSIBLE_TEXTURE[Utils.randint(1,Floor.POSSIBLE_TEXTURE.length-1)];
        }else {
            this.texture = Floor.POSSIBLE_TEXTURE[0];
        }

    }

    /**
     * Get surrounding array list.
     * Sert à obtenir les murs entourant le sol.
     *
     * @param floors the floors
     * @return the array list
     */
    public ArrayList<Wall> getSurrounding(ArrayList<Floor> floors){

        ArrayList<Point> result = new ArrayList<>();
        result.add(this.pos.add(1, 0));
        result.add(this.pos.add(-1, 0));
        result.add(this.pos.add(0, 1));
        result.add(this.pos.add(0, -1));

        for (Floor f : floors){
            if (f.pos.equals(this.pos.add(1, 0))){
                result.set(0, null);
            }
            if (f.pos.equals(this.pos.add(-1, 0))){
                result.set(1, null);
            }
            if (f.pos.equals(this.pos.add(0, 1))){
                result.set(2, null);
            }
            if (f.pos.equals(this.pos.add(0, -1))){
                result.set(3, null);
            }
        }

        ArrayList<Wall> walls = new ArrayList<>();

        Point orientaion = new Point(0, 0);
        if (result.get(0)!=null){
            orientaion = orientaion.add(1, 0);
        }
        if (result.get(1)!=null){
            orientaion = orientaion.add(-1, 0);
        }
        if (result.get(2)!=null){
            orientaion = orientaion.add(0, 1);
        }
        if (result.get(3)!=null){
            orientaion = orientaion.add(0, -1);
        }

        if (orientaion.equals(new Point(-1, -1))){
            walls.add(new Wall(this.pos.add(-1, -1), "assets/map/wall_outer_front_left.png"));
            walls.add(new Wall(this.pos.add(0, -1), "assets/map/wall_mid.png"));
            walls.add(new Wall(this.pos, "assets/map/wall_top_mid.png"));
            walls.add(new Wall(this.pos.add(-1, 0), "assets/map/wall_edge_mid_right.png"));
        }
        if (orientaion.equals(new Point(1, -1))) {
            walls.add(new Wall(this.pos.add(1, -1), "assets/map/wall_outer_front_right.png"));
            walls.add(new Wall(this.pos.add(0, -1), "assets/map/wall_mid.png"));
            walls.add(new Wall(this.pos, "assets/map/wall_top_mid.png"));
            walls.add(new Wall(this.pos.add(1, 0), "assets/map/wall_edge_mid_left.png"));
        }

        if (orientaion.equals(new Point(-1, 1))) {
            walls.add(new Wall(this.pos.add(-1, 2), "assets/map/wall_outer_top_left.png"));
            walls.add(new Wall(this.pos.add(0, 1), "assets/map/wall_mid.png"));
            walls.add(new Wall(this.pos.add(-1, 1), "assets/map/wall_edge_mid_right.png"));
            walls.add(new Wall(this.pos.add(-1, 0), "assets/map/wall_edge_mid_right.png"));
            walls.add(new Wall(this.pos.add(0, 2), "assets/map/wall_top_mid.png"));
        }
        if (orientaion.equals(new Point(1, 1))) {
            walls.add(new Wall(this.pos.add(1, 2), "assets/map/wall_outer_top_right.png"));
            walls.add(new Wall(this.pos.add(0, 1), "assets/map/wall_mid.png"));
            walls.add(new Wall(this.pos.add(1, 1), "assets/map/wall_edge_mid_left.png"));
            walls.add(new Wall(this.pos.add(1, 0), "assets/map/wall_edge_mid_left.png"));
            walls.add(new Wall(this.pos.add(0, 2), "assets/map/wall_top_mid.png"));
        }

        if (orientaion.equals(new Point(-1, 0))) {
            walls.add(new Wall(this.pos.add(-1, 0), "assets/map/wall_edge_mid_right.png"));
        } if (orientaion.equals(new Point(1, 0))) {
            walls.add(new Wall(this.pos.add(1, 0), "assets/map/wall_edge_mid_left.png"));
        }
        if (orientaion.equals(new Point(0, -1))) {
            walls.add(new Wall(this.pos.add(0, -1), "assets/map/wall_mid.png"));
            walls.add(new Wall(this.pos, "assets/map/wall_top_mid.png"));
        } if (orientaion.equals(new Point(0, 1))) {
            walls.add(new Wall(this.pos.add(0, 1), "assets/map/wall_mid.png"));
            walls.add(new Wall(this.pos.add(0, 2), "assets/map/wall_top_mid.png"));
        }

        return walls;

    }
}
