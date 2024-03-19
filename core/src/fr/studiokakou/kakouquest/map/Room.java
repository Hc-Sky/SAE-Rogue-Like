package fr.studiokakou.kakouquest.map;

public class Room {

	Point start;
	Point end;

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

	public boolean collideRoom(Room r){
		if (isWithinBounds(this.start, r.start, r.end) || isWithinBounds(this.end, r.start, r.end)){
			return true;
		}
		if (isWithinBounds(new Point(this.start.x, this.end.y), r.start, r.end)){
			return true;
		}
		return isWithinBounds(new Point(this.end.x, this.start.y), r.start, r.end);
	}

	public Point getCenter(){
		return new Point(this.start.x+(this.end.x-this.start.x)/2, this.start.y+(this.end.y-this.start.y)/2);
	}

	public Point getCenterOutOfMap(){
		return new Point(this.start.x+(this.end.x-this.start.x)/2, this.start.y+(this.end.y-this.start.y)/2).mult(Floor.TEXTURE_WIDTH);
	}

	private boolean isWithinBounds(Point point, Point start, Point end) {
		return point.x >= start.x && point.x <= end.x && point.y >= start.y && point.y <= end.y;
	}
}