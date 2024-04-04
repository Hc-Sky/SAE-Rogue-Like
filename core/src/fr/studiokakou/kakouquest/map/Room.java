package fr.studiokakou.kakouquest.map;

import fr.studiokakou.kakouquest.utils.Utils;

import java.util.ArrayList;

/**
 * Represents a room in the map.
 */
public class Room {

	/**
	 * The starting point of the room.
	 */
	Point start;
	/**
	 * The ending point of the room.
	 */
	Point end;

	/**
	 * Constructs a new Room object.
	 *
	 * @param startX    The x-coordinate of the starting point.
	 * @param startY    The y-coordinate of the starting point.
	 * @param endX      The x-coordinate of the ending point.
	 * @param endY      The y-coordinate of the ending point.
	 * @param hasStairs Indicates whether the room has stairs.
	 */
	public Room(int startX, int startY, int endX, int endY, boolean hasStairs){
		if ((endX-startX)%2 ==0){
			endX-=1;
		}
		if ((endY-startY)%2 ==0){
			endY-=1;
		}
		this.start = new Point(startX, startY);
		this.end = new Point(endX, endY);
	}

	/**
	 * Checks if the room collides with any other room.
	 *
	 * @param rooms The list of rooms to check collision with.
	 * @return True if collision occurs, otherwise false.
	 */
	public boolean isColliding(ArrayList<Room> rooms){
		for (Room r : rooms){
			if (this.collideRoom(r)){
				return true;
			}
		}
		return false;
	}

	public boolean collideRoom(Room r){
		Point bottomLeft = this.start;
		Point topLeft = new Point(this.start.x, this.end.y);
		Point topRight = this.end;
		Point bottomRight = new Point(this.end.x, this.start.y);

		if (Room.isPointInRoom(bottomLeft, r)){
			return true;
		}
		if (Room.isPointInRoom(topLeft, r)){
			return true;
		}
		if (Room.isPointInRoom(topRight, r)){
			return true;
		}
		if (Room.isPointInRoom(bottomRight, r)){
			return true;
		}

		if (isLineInRoom(topLeft, bottomLeft, r)){
			return true;
		}

		if (isLineInRoom(topRight, bottomRight, r)){
			return true;
		}

		if (isLineInRoom(topLeft, topRight, r)){
			return true;
		}

		if (isLineInRoom(bottomLeft, bottomRight, r)){
			return true;
		}

		return false;


	}

	public static boolean isLineInRoom(Point p1, Point p2, Room r){
		if (p1.x>=r.start.x && p1.x <= r.end.x){
			if (p1.y<=r.start.y && p2.y>=r.end.y){
				return true;
			}
		}

		if (p1.y>=r.start.y && p1.y <= r.end.y){
			if (p1.x<=r.start.x && p2.x>=r.end.x){
				return true;
			}
		}

		return false;
	}

	public static boolean isPointInRoom(Point p, Room r){
		if (p.x>r.start.x && p.x<r.end.x){
			if (p.y>r.start.y && p.y<r.end.y){
				return true;
			}
		}
		return false;
	}

	public static boolean isPointInRoomTouching(Point p, Room r){
		if (p.x>=r.start.x && p.x<r.end.x){
			if (p.y>=r.start.y && p.y<=r.end.y){
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the center point of the room.
	 *
	 * @return The center point.
	 */
	public Point getCenter(){
		return new Point(this.start.x+(this.end.x-this.start.x)/2, this.start.y+(this.end.y-this.start.y)/2);
	}

	/**
	 * Returns the center point of the room, adjusted to be out of the map.
	 *
	 * @return The center point out of the map.
	 */
	public Point getCenterOutOfMap(){
		return new Point(this.start.x+(this.end.x-this.start.x)/2, this.start.y+(this.end.y-this.start.y)/2).mult(Floor.TEXTURE_WIDTH);
	}

	public Point getCenterOutOfMapPos(){
		return new Point( this.start.x+(this.end.x-this.start.x)/2,  this.start.y+(this.end.y-this.start.y)/2).mult(Floor.TEXTURE_WIDTH).add(-Floor.TEXTURE_WIDTH/2, -Floor.TEXTURE_HEIGHT/2);
	}

	public Room getNearestRoom(ArrayList<Room> rooms){
		Room nearestRoom = rooms.get(0);
		float nearestDistance = Utils.getDistance(this.getCenter(), nearestRoom.getCenter());

		for (Room r : rooms.subList(1, rooms.size())){
			if (Utils.getDistance(this.getCenter(), r.getCenter()) < nearestDistance){
				nearestDistance = Utils.getDistance(this.getCenter(), r.getCenter());
				nearestRoom = r;
			}
		}

		return nearestRoom;
	}
}
