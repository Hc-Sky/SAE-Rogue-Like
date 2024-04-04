package fr.studiokakou.kakouquest.map;

/**
 * Represents a point in a two-dimensional space.
 */
public class Point {
    /**
     * The x-coordinate of the point.
     */
    public float x;
    /**
     * The y-coordinate of the point.
     */
    public float y;

    /**
     * Constructs a new Point with the given coordinates.
     *
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Point(float x, float y){
        this.x=x;
        this.y=y;
    }

    /**
     * Adds the given x and y values to the current point coordinates.
     *
     * @param x The x value to add.
     * @param y The y value to add.
     * @return The resulting Point.
     */
    public Point add(float x, float y){
        return new Point(this.x+x, this.y+y);
    }

    /**
     * Adds another Point to the current point.
     *
     * @param p The Point to add.
     * @return The resulting Point.
     */
    public Point add(Point p){
        return new Point(this.x+p.x, this.y+p.y);
    }

    /**
     * Returns a new Point with reversed coordinates (negative of current coordinates).
     *
     * @return The resulting Point.
     */
    public Point reverse(){
        return new Point(-this.x, -this.y);
    }

    public String toString(){
        return "("+this.x+", "+this.y+")";
    }

    /**
     * Calculates the orientation between two points (start and end).
     *
     * @param start The starting point.
     * @param end   The ending point.
     * @return The orientation as a Point.
     */
    public static Point getOrientation(Point start, Point end){
        float x = end.x - start.x;
        float y = end.y - start.y;
        float totalDistance = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        float ratioX = x / totalDistance;
        float ratioY = y / totalDistance;

        if (ratioX > 0){
            x = 1;
        } else if (ratioX < 0){
            x = -1;
        } else {
            x = 0;
        }

        if (ratioY > 0){
            y = 1;
        } else if (ratioY < 0){
            y = -1;
        } else {
            y = 0;
        }

        return new Point(x, y);
    }

    /**
     * Checks if a point exceeds another point based on orientation.
     *
     * @param start       The starting point.
     * @param end         The ending point.
     * @param orientation The orientation.
     * @return True if the point exceeds, otherwise false.
     */
    public static boolean isPointExceeded(Point start, Point end, Point orientation){
        if (orientation.x > 0){
            if (start.x > end.x){
                return true;
            }
        } else if (orientation.x < 0){
            if (start.x < end.x){
                return true;
            }
        }

        if (orientation.y > 0){
            return start.y > end.y;
        } else if (orientation.y < 0){
            return start.y < end.y;
        }

        return false;
    }

    /**
     * Calculates the position of a point with given angle and distance from another point.
     *
     * @param pos           The starting point.
     * @param distance      The distance from the starting point.
     * @param angleInDegree The angle in degrees.
     * @return The resulting Point.
     */
    public static Point getPosWithAngle(Point pos, float distance, float angleInDegree){
        float x = (float) (pos.x + distance * Math.cos(Math.toRadians(angleInDegree)));
        float y = (float) (pos.y + distance * Math.sin(Math.toRadians(angleInDegree)));
        return new Point(x, y);
    }

    /**
     * Multiplies the current point coordinates by a scalar value.
     *
     * @param n The scalar value.
     * @return The resulting Point.
     */
    public Point mult(float n){
        return new Point(this.x*n, this.y*n);
    }

    /**
     * Checks if the current point is equal to another point.
     *
     * @param p The other Point to compare.
     * @return True if the points are equal, otherwise false.
     */
    public boolean equals(Point p){
        return (this.x==p.x && this.y==p.y);
    }

    /**
     * Checks if the current point lies within the rectangle defined by two other points.
     *
     * @param p1 The first corner of the rectangle.
     * @param p2 The second corner of the rectangle.
     * @return True if the point lies within the rectangle, otherwise false.
     */
    public boolean isPointIn(Point p1, Point p2){
        if (this.x>=p1.x && this.x<=p2.x){
            if (this.y>=p1.y && this.y<=p2.y){
                return true;
            }
        }
        return false;
    }
}
