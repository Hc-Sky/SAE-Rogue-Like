package fr.studiokakou.kakouquest.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import fr.studiokakou.kakouquest.entity.Test;
import fr.studiokakou.kakouquest.player.Player;
import fr.studiokakou.kakouquest.utils.Utils;
import java.util.ArrayList;
import java.util.Objects;

public class Map {
    public ArrayList<Floor> floors = new ArrayList<Floor>();
    public static ArrayList<Test> tests = new ArrayList<>();

    public int map_height;
    public int map_width;

    String[][] mapScheme;

    //map gen var
    ArrayList<Room> rooms =  new ArrayList<>();

    //room settings
    public static int ROOM_MIN_HEIGHT=5;
    public static int ROOM_MIN_WIDTH=5;
    public static int ROOM_MAX_HEIGHT=17;
    public static int ROOM_MAX_WIDTH=17;

    public Map(int width, int height){
        this.map_height = height;
        this.map_width = width;

        Map.tests.add(new Test(120, 120, "test1"));
        Map.tests.add(new Test(140, 140, "test2"));

        this.initMap();

        this.printMapScheme();
    }
    public void initMap(){
        generateRooms();
        this.mapScheme = getPlaneMap();
        Map.rotateMapToNormal(this.mapScheme);
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

        for (Room r : rooms){
            Utils.markPoint(r.getCenter().mult(Floor.TEXTURE_WIDTH), batch);
        }
    }


    public void generateRooms(){
        for (int i = 0; i < 40; i++) {
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

    public String[][] getPlaneMap(){
        String[][] showMap = new String[this.map_height][this.map_width];

        for (int i = 0; i < this.map_height; i++) {
            for (int j = 0; j < this.map_width; j++) {
                showMap[i][j]=" ";
            }
        }
        for (Room r : this.rooms){
            for (int i = (int) r.start.y; i < r.end.y; i++) {
                for (int j = (int) r.start.x; j < r.end.x; j++) {
                    showMap[i][j]=".";
                }
            }
        }

        return showMap;
    }

    public static void rotateMapToNormal(String[][] tableau) {
        for (int i = 0; i < 3; i++) {
            rotateRight90(tableau);
        }
    }

    public static void rotateRight90(String[][] tableau) {
        int n = tableau.length;
        String[][] rotated = new String[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = tableau[i][j];
            }
        }

        for (int i = 0; i < n; i++) {
            System.arraycopy(rotated[i], 0, tableau[i], 0, n);
        }
    }

    public void genFloors(){
        for (int i = 0; i < this.map_height; i++) {
            for (int j = 0; j < this.map_width; j++) {
                if (Objects.equals(this.mapScheme[j][i], ".")){
                    this.floors.add(new Floor(j*Floor.TEXTURE_WIDTH, i*Floor.TEXTURE_HEIGHT));

                }
            }
        }
    }

    public void printMapScheme(){

        StringBuilder line;
        for (int i = 0; i < this.map_height; i++) {
            line = new StringBuilder();
            for (int j = 0; j < this.map_width; j++) {
                line.append(this.mapScheme[i][j]+" ");
            }
            System.out.println(line);
        }
    }
}
