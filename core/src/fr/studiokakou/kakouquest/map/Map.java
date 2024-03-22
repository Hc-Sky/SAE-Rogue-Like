package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.interactive.Chest;
import fr.studiokakou.kakouquest.interactive.Stairs;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;
import java.util.ArrayList;
import java.util.List;

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

    public static ArrayList<Monster> monsters = new ArrayList<>();
    ArrayList<Chest> chests = new ArrayList<>();

    ArrayList<Stairs> stairsArrayList = new ArrayList<>();

    Stairs stairs = new Stairs(new Point(0,0));

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
    ArrayList<Bridge> bridges = new ArrayList<>();
    ArrayList<Wall> walls = new ArrayList<>();
    /**
     * la hauteur minimale d'une salle.
     */
//room settings
    public static int ROOM_MIN_HEIGHT=7;
    /**
     * la largeur minimale d'une salle.
     */
    public static int ROOM_MIN_WIDTH=7;
    /**
     * la hauteur maximale d'une salle.
     */
    public static int ROOM_MAX_HEIGHT=21;
    /**
     * la largeur maximale d'une salle.
     */
    public static int ROOM_MAX_WIDTH=21;

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

        this.sortRooms();

        generateBridges();

        this.genFloors();

        this.genWalls();

        this.getRealSize();
    }

    /**
     * met à jour les animations de coups.
     *
     * @param batch the batch
     */
    public void updateHitsAnimation(SpriteBatch batch){
        for (Monster m : Map.monsters){
            m.updateHitAnimation(batch);
        }
    }

    public void getRealSize(){
        for (Floor f : this.floors){
            f.pos = f.pos.mult(Floor.TEXTURE_WIDTH);
        }
    }

    public void genWalls(){
        for (Floor f : this.floors){
            ArrayList<Wall> surroundWalls = f.getSurrounding(this.floors);
            this.walls.addAll(surroundWalls);
        }

        for (Bridge b : this.bridges){
            ArrayList<Wall> toAddWalls = b.genBridgeWall(this.rooms, this.bridges);
            this.walls.addAll(toAddWalls);
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

        for (Wall w : this.walls){
            w.draw(batch);
        }
    }

    public void drawMonsters(SpriteBatch batch){
        for (Monster m : Map.monsters){
            m.draw(batch);
        }
    }

    public void drawInteractive(SpriteBatch batch){
        for (Chest chest : this.chests){
            chest.draw(batch);
        }
    }

    public void checkDeadMonster(){
        ArrayList<Monster> tmp = new ArrayList<>();
        for (Monster m : Map.monsters){
            if (!m.isDead){
                tmp.add(m);
            }
        }

        Map.monsters.clear();
        Map.monsters = tmp;

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
            if (! r.isColliding(this.rooms)){
                this.rooms.add(r);
            }
        }
    }

    public void generateBridges(){
        if (this.rooms.size()==1){
            return;
        }
        for (int i = 0; i < this.rooms.size() - 1; i++) {
            this.bridges.add(new Bridge(this.rooms.get(i), this.rooms.get(i+1), this.rooms));
        }
    }

    /**
     * Génère les sols.
     */
    public void genFloors(){
        for (Room r : this.rooms){
            for (int i = (int) r.start.x ; i < r.end.x ; i++) {
                for (int j = (int) r.start.y; j < r.end.y; j++) {
                    this.floors.add(new Floor(i, j));
                }
            }
        }
        for (Bridge b : this.bridges){
            for (Point p : b.points){
                this.floors.add(new Floor(p.x, p.y));
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

    public void spawnMonsters(int currentLevel){
        Map.monsters.clear();
        ArrayList<Integer> randomRarity = new ArrayList<>();

        float tmp_current_level = (float) currentLevel /3;
        if (tmp_current_level<1){
            tmp_current_level=1;
        }

        for (int i = 1; i <= tmp_current_level; i++) {
            for (int j = 0; j <= tmp_current_level-i; j++) {
                randomRarity.add(i);
            }
        }

        for (Room r : this.rooms.subList(1, this.rooms.size())){
            for (int i = (int) r.start.x; i < r.end.x; i++) {
                if (Utils.randint(0, 7)==0){
                    int rarity = randomRarity.get(Utils.randint(0, randomRarity.size() - 1));
                    ArrayList<Monster> mList = Monster.possibleMonsters.get(rarity);
                    while (mList.isEmpty()){
                        rarity = randomRarity.get(Utils.randint(0, randomRarity.size() - 1));
                        mList = Monster.possibleMonsters.get(rarity);
                    }
                    Monster m = mList.get(Utils.randint(0, mList.size()-1));
                    m.place(new Point(i*Floor.TEXTURE_WIDTH, Utils.randint((int) r.start.y, (int) r.end.y)*Floor.TEXTURE_HEIGHT));
                    Map.monsters.add(m);
                    Monster.createPossibleMonsters(currentLevel);
                }
            }
        }
    }

    public void moveMonsters(Player player){
        for (Monster m : Map.monsters){
            m.move(player);
        }
    }

    public void sortRooms(){
        ArrayList<Room> sortedRooms = new ArrayList<>();
        sortedRooms.add(this.rooms.get(0));
        this.rooms.remove(0);

        while (this.rooms.size()>1){
            Room toAdd = sortedRooms.get(sortedRooms.size()-1).getNearestRoom(this.rooms);
            sortedRooms.add(toAdd);
            this.rooms.remove(toAdd);
        }

        sortedRooms.add(this.rooms.get(0));
        this.rooms.clear();

        this.rooms = sortedRooms;
    }

    public void genInteractive(int currentLevel){
        this.chests.clear();
        for (Room r : rooms.subList(1, rooms.size())){
            this.chests.add(new Chest(r.getCenterOutOfMapPos(), currentLevel));
        }

        for (Room r : rooms.subList(1, rooms.size())){
            this.stairs = new Stairs(r.getCenterOutOfMapPos());
        }


    }

    public void updateInteractive(Player player){
        for (Chest chest : this.chests){
            chest.refreshInteract(player);
        }

        stairs.refreshInteract(player);
    }

    public void dispose(){
        for (Floor f : this.floors){
            f.texture.dispose();
        }
    }
}
