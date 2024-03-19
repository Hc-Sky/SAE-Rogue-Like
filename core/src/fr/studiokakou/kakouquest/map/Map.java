package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Test;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;
import java.util.ArrayList;

/**
 * le type Map.
 * Cette classe est utilisée pour créer un objet Map.
 *
 * @version 1.0
 */
public class Map {
    /**
     * le sol de la map. C'est une liste de sol.
     */
    public ArrayList<Floor> floors = new ArrayList<Floor>();
    /**
     * la liste des tests
     */
    public static ArrayList<Test> tests = new ArrayList<>();

    /**
     * La hauteur de la map.
     */
    public int map_height;
    /**
     * La largeur de la map.
     */
    public int map_width;

    /**
     * La liste des salles.
     */
//map gen var
    ArrayList<Room> rooms =  new ArrayList<>();

    /**
     * la hauteur minimale d'une salle.
     */
//room settings
    public static int ROOM_MIN_HEIGHT=5;
    /**
     * la largeur minimale d'une salle.
     */
    public static int ROOM_MIN_WIDTH=5;
    /**
     * la hauteur maximale d'une salle.
     */
    public static int ROOM_MAX_HEIGHT=19;
    /**
     * la largeur maximale d'une salle.
     */
    public static int ROOM_MAX_WIDTH=19;

    /**
     * Constructeur de Map.
     * Sert à créer un objet Map.
     *
     * @param width  the width
     * @param height the height
     */
    public Map(int width, int height){
        this.map_height = height;
        this.map_width = width;

        Map.tests.add(new Test(120, 120, "test1"));
        Map.tests.add(new Test(140, 140, "test2"));

        this.initMap();
    }

    /**
     * Initialise la map.
     * Permet d'initialiser la map.
     * Cette méthode est utilisée pour générer les salles et les sols.
     *
     * @see Map#generateRooms()
     * @see Map#genFloors()
     */
    public void initMap(){
        generateRooms();
        this.genFloors();
    }


    /**
     * permet de mettre à jour les tests.
     * Elle permet de mettre à jour les animations de coups.
     * Elle permet aussi de mettre à jour les joueurs touchés.
     *
     * @param player the player
     */
    public static void clearAttackedPlayers(Player player){
        for (Test t : tests){
            t.player_hitted.remove(player.name);
        }
    }

    /**
     * met à jour les animations de coups.
     *
     * @param batch the batch
     */
    public void updateHitsAnimation(SpriteBatch batch){
        for (Test t : tests){
            t.updateHitAnimation(batch);
        }
    }

    /**
     * Dessine la map.
     *
     * @param batch the batch
     */
    public void drawMap(SpriteBatch batch){
        for (Floor f : this.floors){
            batch.draw(f.texture, f.pos.x, f.pos.y);
        }

        for (Test t : Map.tests) {
            t.sprite.draw(batch);
        }
    }


    /**
     * Génère les salles.
     */
    public void generateRooms(){
        for (int i = 0; i < 50; i++) {
            int startX = Utils.randint(0, this.map_width-Map.ROOM_MAX_WIDTH);
            int startY = Utils.randint(0, this.map_height-Map.ROOM_MAX_HEIGHT);
            int endX = startX+Utils.randint(Map.ROOM_MIN_WIDTH,Map.ROOM_MAX_WIDTH);
            int endY = startY+Utils.randint(Map.ROOM_MIN_HEIGHT,Map.ROOM_MAX_HEIGHT);
            Room r = new Room(startX, startY, endX, endY, false);
            boolean canAdd = true;
            for (Room room : this.rooms){
                if (r.collideRoom(room)){
                    canAdd = false;
                }
            }
            if (canAdd){
                this.rooms.add(r);
            }
        }
    }

    /**
     * Génère les sols.
     */
    public void genFloors(){
        for (Room r : this.rooms){
            for (int i = (int) r.start.x ; i < r.end.x ; i++) {
                for (int j = (int) r.start.y; j < r.end.y; j++) {
                    this.floors.add(new Floor(i*Floor.TEXTURE_WIDTH, j*Floor.TEXTURE_HEIGHT));
                }
            }
        }
    }

    /**
     * Retourne le spawn du joueur.
     *
     * @return the point
     */
    public Point getPlayerSpawn(){
        return this.rooms.get(0).getCenterOutOfMap();
    }
}
