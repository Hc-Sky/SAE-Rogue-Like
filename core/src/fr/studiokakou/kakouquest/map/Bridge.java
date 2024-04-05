package fr.studiokakou.kakouquest.map;

import java.util.ArrayList;

/**
 * Represents a bridge connecting two rooms.
 */
public class Bridge {
    /** The points forming the bridge. */
    public ArrayList<Point> points;

    /**
     * Constructs a bridge between two rooms.
     * @param room1 The first room.
     * @param room2 The second room.
     * @param rooms The list of rooms.
     */
    public Bridge(Room room1, Room room2, ArrayList<Room> rooms) {
        this.points = new ArrayList<>();
        generateBridge(room1, room2, rooms);
    }

    /**
     * Generates the bridge between two rooms.
     * @param room1 The first room.
     * @param room2 The second room.
     * @param rooms The list of rooms.
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
     * Checks if a point is inside any of the rooms.
     * @param p The point to check.
     * @param rooms The list of rooms.
     * @return True if the point is inside a room, false otherwise.
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
     * Checks if a point is inside any of the rooms, touching their boundaries.
     * @param p The point to check.
     * @param rooms The list of rooms.
     * @return True if the point is touching any room, false otherwise.
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
     * Checks if a point is in any of the bridges.
     * @param point The point to check.
     * @param bridges The list of bridges.
     * @return True if the point is in any bridge, false otherwise.
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
     * Checks if a point represents a turn in the bridge.
     * @param pointIndex The index of the point.
     * @return True if the point represents a turn, false otherwise.
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
     * Generates wall objects for the bridge.
     * @param rooms The list of rooms.
     * @param bridges The list of bridges.
     * @return The list of wall objects.
     */
    public ArrayList<Wall> genBridgeWall(ArrayList<Room> rooms, ArrayList<Bridge> bridges){
        ArrayList<Wall> result = new ArrayList<>();

        for (int i = 0; i < this.points.size()-1; i++) {
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