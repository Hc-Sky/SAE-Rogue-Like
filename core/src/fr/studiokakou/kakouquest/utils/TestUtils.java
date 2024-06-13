package fr.studiokakou.kakouquest.utils;

import fr.studiokakou.kakouquest.map.Point;

public class TestUtils {
    public static void main(String[] args) {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(4, 8);

        float speed = 2.0f;

        assert Utils.getPointDirection(p1, p2, speed).equals(new Point((float) 1.8944272, (float) 1)) : "Not the correct resul point";

    }
}
