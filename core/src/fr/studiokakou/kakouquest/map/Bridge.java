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
        Point center1 = room1.getCenter();
        Point center2 = room2.getCenter();

        this.points.add(center2);
        float currentX=center1.x;
        float currentY=center1.y;
        while (currentX < center2.x){
            currentX+=1;
            this.points.add(new Point(currentX, currentY));
        }
        while (currentX > center2.x){
            currentX-=1;
            this.points.add(new Point(currentX, currentY));
        }
        while (currentY < center2.y){
            currentY+=1;
            this.points.add(new Point(currentX, currentY));
        }
        while (currentY > center2.y){
            currentY-=1;
            this.points.add(new Point(currentX, currentY));
        }
    }
}
