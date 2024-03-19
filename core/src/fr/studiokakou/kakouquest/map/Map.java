package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Test;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;
import java.util.ArrayList;

public class Map {
    public ArrayList<Floor> floors = new ArrayList<Floor>();
    public static ArrayList<Test> tests = new ArrayList<>();

    public int map_height;
    public int map_width;

    //map gen var
    ArrayList<Room> rooms =  new ArrayList<>();

    //room settings
    public static int ROOM_MIN_HEIGHT=5;
    public static int ROOM_MIN_WIDTH=5;
    public static int ROOM_MAX_HEIGHT=19;
    public static int ROOM_MAX_WIDTH=19;

    public Map(int width, int height){
        this.map_height = height;
        this.map_width = width;

        Map.tests.add(new Test(120, 120, "test1"));
        Map.tests.add(new Test(140, 140, "test2"));

        this.initMap();
    }

    public void initMap(){
        generateRooms();
        this.genFloors();
    }


    public static void clearAttackedPlayers(Player player){
        for (Test t : tests){
            t.player_hitted.remove(player.name);
        }
    }

    public void updateHitsAnimation(SpriteBatch batch){
        for (Test t : tests){
            t.updateHitAnimation(batch);
        }
    }

    public void drawMap(SpriteBatch batch){
        for (Floor f : this.floors){
            batch.draw(f.texture, f.pos.x, f.pos.y);
        }

        for (Test t : Map.tests) {
            t.sprite.draw(batch);
        }
    }


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

    public void genFloors(){
        for (Room r : this.rooms){
            for (int i = (int) r.start.x ; i < r.end.x ; i++) {
                for (int j = (int) r.start.y; j < r.end.y; j++) {
                    this.floors.add(new Floor(i*Floor.TEXTURE_WIDTH, j*Floor.TEXTURE_HEIGHT));
                }
            }
        }
    }

    public Point getPlayerSpawn(){
        return this.rooms.get(0).getCenterOutOfMap();
    }
}
