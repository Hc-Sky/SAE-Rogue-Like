package fr.studiokakou.kakouquest.map;

import fr.studiokakou.kakouquest.utils.Utils;

import java.util.ArrayList;

/**
 * le type Room.
 * Cette classe est utilisée pour créer un objet Room.
 *
 * @version 1.0
 */
public class Room {

	/**
	 * le point de départ.
	 */
	Point start;
	/**
	 * le point de fin.
	 */
	Point end;

	/**
	 * Constructeur de Room.
	 * Sert à créer un objet Room.
	 *
	 * @param startX    the start x
	 * @param startY    the start y
	 * @param endX      the end x
	 * @param endY      the end y
	 * @param hasStairs the has stairs
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
	 * colision avec une salle.
	 * Sert à vérifier si une salle est en collision avec une autre salle.
	 * @return the boolean
	 */
	public boolean isColliding(ArrayList<Room> rooms){
		for (Room r : rooms){
			if (this.collideRoom(r)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the current Room collides with another Room.
	 * Collision is determined by checking if any of the corners of the current Room are inside the other Room,
	 * or if any of the sides of the current Room are inside the other Room.
	 *
	 * @param r The other Room to check for collision.
	 * @return true if the current Room collides with the other Room, false otherwise.
	 */
	public boolean collideRoom(Room r){
		// Define the corners of the current Room
		Point bottomLeft = this.start;
		Point topLeft = new Point(this.start.x, this.end.y);
		Point topRight = this.end;
		Point bottomRight = new Point(this.end.x, this.start.y);

		// Check if any of the corners of the current Room are inside the other Room
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

		// Check if any of the sides of the current Room are inside the other Room
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

		// If none of the corners or sides of the current Room are inside the other Room, there is no collision
		return false;
	}

	/**
	 * Checks if a line, defined by two points, is inside a Room.
	 * The line is considered to be inside the Room if it is within the x boundaries of the Room and crosses the y boundaries,
	 * or if it is within the y boundaries of the Room and crosses the x boundaries.
	 *
	 * @param p1 The first point of the line.
	 * @param p2 The second point of the line.
	 * @param r The Room to check.
	 * @return true if the line is inside the Room, false otherwise.
	 */
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

	/**
	 * Checks if a point is inside a Room.
	 * The point is considered to be inside the Room if its x and y coordinates are within the boundaries of the Room.
	 *
	 * @param p The point to check.
	 * @param r The Room to check.
	 * @return true if the point is inside the Room, false otherwise.
	 */
	public static boolean isPointInRoom(Point p, Room r){
		if (p.x>r.start.x && p.x<r.end.x){
			if (p.y>r.start.y && p.y<r.end.y){
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if a point is touching a Room.
	 * The point is considered to be touching the Room if its x and y coordinates are within or on the boundaries of the Room.
	 *
	 * @param p The point to check.
	 * @param r The Room to check.
	 * @return true if the point is touching the Room, false otherwise.
	 */
	public static boolean isPointInRoomTouching(Point p, Room r){
		if (p.x>=r.start.x && p.x<r.end.x){
			if (p.y>=r.start.y && p.y<=r.end.y){
				return true;
			}
		}
		return false;
	}

	/**
	 * Permet de connaître le centre d'une salle.
	 *
	 * @return the point
	 */
	public Point getCenter(){
		return new Point(this.start.x+(this.end.x-this.start.x)/2, this.start.y+(this.end.y-this.start.y)/2);
	}

	/**
	 * Permet de connaître le centre d'une salle hors de la map.
	 *
	 * @return the point
	 */
	public Point getCenterOutOfMap(){
		return new Point(this.start.x+(this.end.x-this.start.x)/2, this.start.y+(this.end.y-this.start.y)/2).mult(Floor.TEXTURE_WIDTH);
	}

	public Point getCenterOutOfMapPos(){
		return new Point( this.start.x+(this.end.x-this.start.x)/2,  this.start.y+(this.end.y-this.start.y)/2).mult(Floor.TEXTURE_WIDTH).add(-Floor.TEXTURE_WIDTH/2, -Floor.TEXTURE_HEIGHT/2);
	}

	/**
	 * Finds the nearest Room to the current Room from a list of Rooms.
	 * The distance between Rooms is calculated using the Utils.getDistance method, which takes the centers of two Rooms as parameters.
	 * The method iterates over the list of Rooms, updating the nearest Room and the shortest distance whenever a closer Room is found.
	 *
	 * @param rooms The list of Rooms to search.
	 * @return The Room from the list that is nearest to the current Room.
	 */
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
