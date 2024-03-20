package fr.studiokakou.kakouquest.map;

import java.util.ArrayList;
import java.util.List;

public class Bridge {
	private List<Point> points;

	public Bridge(Room room1, Room room2) {
		this.points = new ArrayList<>();
		generateBridge(room1, room2);
	}

	public void generateBridge(Room room1, Room room2) {
		Point center1 = room1.getCenter();
		Point center2 = room2.getCenter();

		// Générer le pont horizontalement
		for (int x = (int) Math.min(center1.x, center2.x); x <= Math.max(center1.x, center2.x); x++) {
			this.points.add(new Point(x, center1.y));
			this.points.add(new Point(x, center1.y + 1)); // pont de deux d'épaisseur
		}

		// Générer le pont verticalement
		for (int y = (int) Math.min(center1.y, center2.y); y <= Math.max(center1.y, center2.y); y++) {
			this.points.add(new Point(center2.x, y));
			this.points.add(new Point(center2.x + 1, y)); // pont de deux d'épaisseur
		}
	}

	//

	public List<Point> getPoints() {
		return points;
	}

	public void add(Bridge bridge) {
		this.points.addAll(bridge.getPoints());
	}
}