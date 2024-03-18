package fr.studiokakou.kakouquest.map;

public class Room {
	public Point upLeft;
	public Point upRight;
	public Point downLeft;
	public Point downRight;

	//constructor for room with given points (for map generation)
	public Room(Point upLeft, Point upRight, Point downLeft, Point downRight) {
		this.upLeft = upLeft;
		this.upRight = upRight;
		this.downLeft = downLeft;
		this.downRight = downRight;
	}

	public Room(int mapWidth, int mapHeight) {
		this.upLeft = new Point((int)(Math.random() * (mapWidth - 10) + 1), (int)(Math.random() * (mapHeight - 10) + 1));
		this.upRight = new Point(this.upLeft.x + (int)(Math.random() * 10 + 1), this.upLeft.y);
		this.downLeft = new Point(this.upLeft.x, this.upLeft.y + (int)(Math.random() * 10 + 1));
		this.downRight = new Point(this.upRight.x, this.downLeft.y);
	}


}