package fr.studiokakou.kakouquest.map;

/**
 * le type Room.
 * Cette classe est utilisée pour créer un objet Room.
 *
 * @version 1.0
 * @author hugocohen--cofflard
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
     *
     * @param r the r
     * @return the boolean
     */
    public boolean collideRoom(Room r){
		if (isWithinBounds(this.start, r.start, r.end) || isWithinBounds(this.end, r.start, r.end)){
			return true;
		}
		if (isWithinBounds(new Point(this.start.x, this.end.y), r.start, r.end)){
			return true;
		}
		return isWithinBounds(new Point(this.end.x, this.start.y), r.start, r.end);
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

	private boolean isWithinBounds(Point point, Point start, Point end) {
		return point.x >= start.x && point.x <= end.x && point.y >= start.y && point.y <= end.y;
	}
}