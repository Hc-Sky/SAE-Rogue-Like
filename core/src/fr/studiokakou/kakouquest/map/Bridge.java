package fr.studiokakou.kakouquest.map;

import java.util.ArrayList;
import java.util.List;

public class Bridge {
    public ArrayList<Point> points;

    /**
     * Constructor of the Bridge class.
     *
     * @param room1 Première salle.
     * @param room2 Deuxième salle.
     * @param rooms Liste des salles.
     */
    public Bridge(Room room1, Room room2, ArrayList<Room> rooms) {
        this.points = new ArrayList<>();
        generateBridge(room1, room2, rooms);
    }

    /**
     * Generate a bridge between two rooms.
     *
     * @param room1 Première salle.
     * @param room2 Deuxième salle.
     * @param rooms Liste des salles.
     */
    public void generateBridge(Room room1, Room room2, ArrayList<Room> rooms) {
        Point center1 = room1.getCenter().add(-0.5f, -0.5f);
        Point center2 = room2.getCenter().add(-0.5f, -0.5f);

        float currentX=center1.x;
        float currentY=center1.y;
        while (currentX < center2.x){
            currentX+=1;
            Point p = new Point(currentX, currentY);
            if (! isPointInRooms(p, rooms)){
                this.points.add(p);
            }
        }
        while (currentX > center2.x){
            currentX-=1;
            Point p = new Point(currentX, currentY);
            if (! isPointInRooms(p, rooms)){
                this.points.add(p);
            }
        }
        while (currentY < center2.y){
            currentY+=1;
            Point p = new Point(currentX, currentY);
            if (! isPointInRooms(p, rooms)){
                this.points.add(p);
            }
        }
        while (currentY > center2.y){
            currentY-=1;
            Point p = new Point(currentX, currentY);
            if (! isPointInRooms(p, rooms)){
                this.points.add(p);
            }
        }
    }

    /**
     * Check if a point is in a room.
     *
     * @param p     Point to check.
     * @param rooms Liste des salles.
     */
    public boolean isPointInRooms(Point p, ArrayList<Room> rooms){
        for (Room r : rooms){
            if (Room.isPointInRoom(p, r)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a point is in a room or touching it.
     *
     * @param p     Point to check.
     * @param rooms Liste des salles.
     */
    public boolean isPointInRoomsTouching(Point p, ArrayList<Room> rooms){
        for (Room r : rooms){
            if (Room.isPointInRoomTouching(p, r)){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a point is in a bridge.
     *
     * @param point   Point to check.
     * @param bridges Liste des ponts.
     */
    public boolean isPointsInBridges(Point point, ArrayList<Bridge> bridges){
        for (Bridge b : bridges){
            if (!b.equals(this)){
                for (Point p : b.points){
                    if (p.equals(point)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if a point is a turn.
     *
     * @param pointIndex Index du point.
     */
    public boolean isTurn(int pointIndex){
        if (pointIndex==0 || pointIndex==this.points.size()-1){
            return false;
        }
        if (this.points.get(pointIndex).x == this.points.get(pointIndex+1).x && this.points.get(pointIndex).y == this.points.get(pointIndex-1).y){
            return true;
        }
        return false;
    }

    /**
     * Generate the walls of the bridge.
     *
     * @param rooms   Liste des salles.
     * @param bridges Liste des ponts.
     */
    public ArrayList<Wall> genBridgeWall(ArrayList<Room> rooms, ArrayList<Bridge> bridges){
        ArrayList<Wall> result = new ArrayList<>();

        for (int i = 0; i < this.points.size()-1; i++) {
//            if (i!=0 && i!=this.points.size()-1 && this.points.get(i-1).y != this.points.get(i).y && this.points.get(i+1).x != this.points.get(i).x){
//
//            }
            if (! isPointInRoomsTouching(this.points.get(i), rooms) && !isTurn(i) && !isPointsInBridges(this.points.get(i), bridges)){
                if (i==this.points.size()-1){
                    if (this.points.get(i).y == this.points.get(i-1).y){
                        result.add(new Wall(this.points.get(i).add(0, 1), "assets/map/wall_mid.png"));
                        result.add(new Wall(this.points.get(i).add(0, 2), "assets/map/wall_top_mid.png"));
                        result.add(new Wall(this.points.get(i).add(0, -1), "assets/map/wall_mid.png"));
                        result.add(new Wall(this.points.get(i), "assets/map/wall_top_mid.png"));
                    }else if (this.points.get(i).x == this.points.get(i-1).x){
                        result.add(new Wall(this.points.get(i).add(1, 0), "assets/map/wall_outer_mid_right.png"));
                        result.add(new Wall(this.points.get(i).add(-1, 0), "assets/map/wall_outer_mid_left.png"));
                    }
                } if (i!=this.points.size()-1){
                    if (this.points.get(i).y == this.points.get(i+1).y){
                        result.add(new Wall(this.points.get(i).add(0, 1), "assets/map/wall_mid.png"));
                        result.add(new Wall(this.points.get(i).add(0, 2), "assets/map/wall_top_mid.png"));
                        result.add(new Wall(this.points.get(i).add(0, -1), "assets/map/wall_mid.png"));
                        result.add(new Wall(this.points.get(i), "assets/map/wall_top_mid.png"));
                    }
                    else if (this.points.get(i).x == this.points.get(i+1).x ){
                        result.add(new Wall(this.points.get(i).add(1, 0), "assets/map/wall_outer_mid_right.png"));
                        result.add(new Wall(this.points.get(i).add(-1, 0), "assets/map/wall_outer_mid_left.png"));
                    }
                }
            }


        }

        return result;
    }
}