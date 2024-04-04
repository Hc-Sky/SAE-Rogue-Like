package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Monster;
import fr.studiokakou.kakouquest.interactive.Chest;
import fr.studiokakou.kakouquest.interactive.OnGroundMeleeWeapon;
import fr.studiokakou.kakouquest.interactive.Stairs;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.screens.InGameScreen;
import fr.studiokakou.kakouquest.utils.Utils;

import java.util.*;

/**
 * le type Map.
 * Cette classe est utilisée pour créer un objet Map.
 *
 * @version 1.0
 */
public class Map {
    /** The list of floor tiles. */
    public ArrayList<Floor> floors = new ArrayList<>();
    /** The list of monsters. */
    public static ArrayList<Monster> monsters = new ArrayList<>();
    /** The list of chests. */
    ArrayList<Chest> chests = new ArrayList<>();
    /** The list of melee weapons on the ground. */
    public static ArrayList<OnGroundMeleeWeapon> onGroundMeleeWeapons = new ArrayList<>();
    /** The stairs. */
    public Stairs stairs;
    /** The height of the map. */
    public int map_height;
    /** The width of the map. */
    public int map_width;

    /** The list of rooms. */
    ArrayList<Room> rooms = new ArrayList<>();
    /** The list of bridges. */
    ArrayList<Bridge> bridges = new ArrayList<>();
    /** The list of walls. */
    ArrayList<Wall> walls = new ArrayList<>();

    /** The minimum height of a room. */
    public static int ROOM_MIN_HEIGHT=7;
    /** The minimum width of a room. */
    public static int ROOM_MIN_WIDTH=7;
    /** The maximum height of a room. */
    public static int ROOM_MAX_HEIGHT=21;
    /** The maximum width of a room. */
    public static int ROOM_MAX_WIDTH=21;

    /** The distances between points and objects. */
    public Hashtable<Float, Object> distances = new Hashtable<>();

    /**
     * Constructs a new map.
     * @param width The width of the map.
     * @param height The height of the map.
     */
    public Map(int width, int height){
        this.map_height = height;
        this.map_width = width;

        this.initMap();
    }

    /**
     * Initializes the map.
     */
    public void initMap(){
        Map.onGroundMeleeWeapons.clear();

        generateRooms();

        this.sortRooms();

        generateBridges();

        this.genFloors();

        this.genWalls();

        this.getRealSize();
    }

    /**
     * Updates hit animations.
     * @param batch The sprite batch.
     */
    public void updateHitsAnimation(SpriteBatch batch){
        for (Monster m : Map.monsters){
            m.updateHitAnimation(batch);
        }
    }

    /**
     * Retrieves the real size of the map.
     */
    public void getRealSize(){
        for (Floor f : this.floors){
            f.pos = f.pos.mult(Floor.TEXTURE_WIDTH);
        }
    }

    /**
     * Generates walls for the map.
     */
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
     * Draws the map.
     * @param batch The sprite batch.
     */
    public void drawMap(SpriteBatch batch){
        for (Floor f : this.floors){
            batch.draw(f.texture, f.pos.x, f.pos.y);
        }

        for (Wall w : this.walls){
            w.draw(batch);
        }
    }

    /**
     * Draws monsters on the map.
     * @param batch The sprite batch.
     */
    public void drawMonsters(SpriteBatch batch){
        for (Monster m : Map.monsters){
            m.draw(batch);
        }
    }

    /**
     * Draws interactive objects on the map.
     * @param batch The sprite batch.
     */
    public void drawInteractive(SpriteBatch batch){
        for (Chest chest : this.chests){
            chest.draw(batch);
        }

        this.stairs.draw(batch);

        for (OnGroundMeleeWeapon weapon : Map.onGroundMeleeWeapons){
            weapon.draw(batch);
        }
    }

    /**
     * Checks if any monster is dead and removes it from the list.
     */
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
     * Generates rooms for the map.
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

    /**
     * Generates bridges between rooms.
     */
    public void generateBridges(){
        if (this.rooms.size()==1){
            return;
        }
        for (int i = 0; i < this.rooms.size() - 1; i++) {
            this.bridges.add(new Bridge(this.rooms.get(i), this.rooms.get(i+1), this.rooms));
        }
    }

    /**
     * Generates floor tiles for the map.
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
     * Retrieves the spawn point for the player.
     * @return The spawn point.
     */
    public Point getPlayerSpawn(){
        return this.rooms.get(0).getCenterOutOfMap();
    }

    /**
     * Spawns monsters on the map based on the current level.
     * @param currentLevel The current level.
     */
    public void spawnMonsters(int currentLevel){
        Map.monsters.clear();
        ArrayList<Integer> randomRarity = new ArrayList<>();

        float tmp_current_level = (float) currentLevel /3;
        if (tmp_current_level<1){
            tmp_current_level=1;
        }

        for (int i = 1; i <= tmp_current_level; i++) {
            for (int j = 0; j <= tmp_current_level-i; j++) {
                if (Monster.possibleMonsters.get(i)!=null){
                    randomRarity.add(i);
                }
            }
        }

        for (Room r : this.rooms.subList(1, this.rooms.size())){
            for (int i = (int) r.start.x+1; i < r.end.x-1; i++) {
                if (Utils.randint(0, 7)==0){
                    int rarity = randomRarity.get(Utils.randint(0, randomRarity.size() - 1));
                    ArrayList<Monster> mList = Monster.possibleMonsters.get(rarity);
                    while ( mList==null || mList.isEmpty()){
                        rarity = randomRarity.get(Utils.randint(0, randomRarity.size() - 1));
                        mList = Monster.possibleMonsters.get(rarity);
                    }
                    Monster m = mList.get(Utils.randint(0, mList.size()-1));
                    m.place(new Point(i*Floor.TEXTURE_WIDTH, Utils.randint((int) r.start.y+1, (int) r.end.y-1)*Floor.TEXTURE_HEIGHT));
                    Map.monsters.add(m);
                    Monster.createPossibleMonsters(currentLevel);
                }
            }
        }
    }

    /**
     * Moves monsters on the map towards the player.
     * @param player The player.
     */
    public void moveMonsters(Player player){
        for (Monster m : Map.monsters){
            m.move(player, this);
        }
    }

    /**
     * Sorts rooms based on their proximity.
     */
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

    /**
     * Generates interactive objects such as chests and stairs.
     * @param currentLevel The current level.
     * @param gameScreen The game screen.
     */
    public void genInteractive(int currentLevel, InGameScreen gameScreen){
        this.stairs = new Stairs(this.rooms.get(this.rooms.size()-1).getCenterOutOfMapPos(), gameScreen);

        this.chests.clear();
        for (Room r : rooms.subList(1, rooms.size()-1)){
            if (Utils.randint(0, 4) == 0){
                if (!this.stairs.pos.equals(r.getCenterOutOfMapPos())){
                    this.chests.add(new Chest(r.getCenterOutOfMapPos(), currentLevel));
                }
            }
        }

    }

    /**
     * Updates the state of interactive objects based on player's position.
     * @param player The player.
     */
    public void updateInteractive(Player player){
        this.distances.clear();

        TreeMap<Float, Object> sorted = getDistances(player);

        Object closestObject = sorted.get(sorted.firstKey());

        //update functions
        for (Chest chest : this.chests){
            chest.refreshInteract(player, chest == closestObject);
        }
        this.stairs.refreshInteract(player, this.stairs == closestObject);
        for (OnGroundMeleeWeapon weapon : Map.onGroundMeleeWeapons){
            weapon.refreshInteract(player, weapon == closestObject);
        }
    }

    /**
     * Retrieves distances between points and objects.
     * @param player The player.
     * @return A tree map of distances.
     */
    public TreeMap<Float, Object> getDistances(Player player){
        for (Chest chest : this.chests){
            this.distances.put(Utils.getDistance(chest.pos, player.pos), chest);
        }
        this.distances.put(Utils.getDistance(this.stairs.pos, player.pos), this.stairs);
        for (OnGroundMeleeWeapon weapon : Map.onGroundMeleeWeapons){
            this.distances.put(Utils.getDistance(weapon.pos, player.pos), weapon);
        }

        return new TreeMap<>(this.distances);
    }

    /**
     * Updates the removal of interactive objects.
     */
    public void updateRemoveInteractive(){
        ArrayList<OnGroundMeleeWeapon> toRemove = new ArrayList<>();
        ArrayList<OnGroundMeleeWeapon> toAdd = new ArrayList<>();

        for (OnGroundMeleeWeapon weapon : Map.onGroundMeleeWeapons){
            if (weapon.toDelete){
                toRemove.add(weapon);
            }
            if (weapon.toAdd!=null){
                toAdd.add(weapon.toAdd);
            }
        }

        Map.onGroundMeleeWeapons.addAll(toAdd);

        for (OnGroundMeleeWeapon weapon : toRemove){
            Map.onGroundMeleeWeapons.remove(weapon);
        }
    }

    /**
     * Checks if all points are on floor tiles.
     * @param points The points to check.
     * @return True if all points are on floor tiles, false otherwise.
     */
    public boolean arePointsOnFloor(Point[] points){
        boolean[] areIn = new boolean[points.length];
        java.util.Arrays.fill(areIn, false);

        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            for (Floor floor : this.floors) {
                Point p1 = floor.pos;
                Point p2 = floor.pos.add(Floor.TEXTURE_WIDTH, Floor.TEXTURE_HEIGHT);
                if (point.isPointIn(p1, p2)) {
                    areIn[i]=true;
                }
            }
        }

        for (boolean isIn : areIn){
            if (!isIn){
                return false;
            }
        }

        return true;
    }

    /**
     * Disposes of resources.
     */
    public void dispose(){
        for (Floor f : this.floors){
            f.texture.dispose();
        }
    }
}
