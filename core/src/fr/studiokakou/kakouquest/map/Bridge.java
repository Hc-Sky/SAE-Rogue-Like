package fr.studiokakou.kakouquest.map;

import java.util.ArrayList;
import java.util.List;

public class Bridge {
    public ArrayList<Point> points;

    public Bridge(Room room1, Room room2) {
        this.points = new ArrayList<>();
        generateBridge(room1, room2);
    }

    public void generateBridge(Room room1, Room room2) {
        Point center1 = room1.getCenter().add(-0.5f, -0.5f);
        Point center2 = room2.getCenter().add(-0.5f, -0.5f);

        this.points.add(center2);
        float currentX=center1.x;
        float currentY=center1.y;
        while (currentX < center2.x){
            currentX+=1;
            Point p = new Point(currentX, currentY);
            if (!Room.isPointInRoom(p, room1) && !Room.isPointInRoom(p, room2)){
                this.points.add(p);
            }
        }
        while (currentX > center2.x){
            currentX-=1;
            Point p = new Point(currentX, currentY);
            if (!Room.isPointInRoom(p, room1) && !Room.isPointInRoom(p, room2)){
                this.points.add(p);
            }
        }
        while (currentY < center2.y){
            currentY+=1;
            Point p = new Point(currentX, currentY);
            if (!Room.isPointInRoom(p, room1) && !Room.isPointInRoom(p, room2)){
                this.points.add(p);
            }
        }
        while (currentY > center2.y){
            currentY-=1;
            Point p = new Point(currentX, currentY);
            if (!Room.isPointInRoom(p, room1) && !Room.isPointInRoom(p, room2)){
                this.points.add(p);
            }
        }
    }

    public ArrayList<Wall> genBridgeWall(){
        ArrayList<Wall> result = new ArrayList<>();

        for (int i = 0; i < this.points.size()-1; i++) {
//            if (i!=0 && i!=this.points.size()-1 && this.points.get(i-1).y != this.points.get(i).y && this.points.get(i+1).x != this.points.get(i).x){
//
//            }
            if (this.points.get(i).y == this.points.get(i+1).y){
                result.add(new Wall(this.points.get(i).add(0, 1), "assets/map/wall_mid.png"));
                result.add(new Wall(this.points.get(i).add(0, 2), "assets/map/wall_top_mid.png"));
                result.add(new Wall(this.points.get(i).add(0, -1), "assets/map/wall_mid.png"));
                result.add(new Wall(this.points.get(i), "assets/map/wall_top_mid.png"));
            }
            if (this.points.get(i).x == this.points.get(i+1).x){
                result.add(new Wall(this.points.get(i).add(1, 0), "assets/map/wall_outer_mid_right.png"));
                result.add(new Wall(this.points.get(i).add(-1, 0), "assets/map/wall_outer_mid_left.png"));
            }
        }

        return result;
    }
}
