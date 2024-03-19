package fr.studiokakou.kakouquest.map;

/**
 * le type Point.
 * Cette classe est utilisée pour créer un objet Point.
 *
 * @version 1.0
 */
public class Point {
    /**
     * le point x.
     */
    public float x;
    /**
     * le point y.
     */
    public float y;

    /**
     * Constructeur de Point.
     * Sert à créer un objet Point.
     *
     * @param x the x
     * @param y the y
     */
    public Point(float x, float y){
        this.x=x;
        this.y=y;
    }

    /**
     * AJoute un point à un autre.
     *
     * @param x the x
     * @param y the y
     * @return the point
     */
    public Point add(float x, float y){
        return new Point(this.x+x, this.y+y);
    }

    /**
     * Ajoute un point à un autre point en p
     *
     * @param p the p
     * @return the point
     */
    public Point add(Point p){
        return new Point(this.x+p.x, this.y+p.y);
    }

    /**
     * Soustrait un point à un autre.
     *
     * @return the point
     */
    public Point reverse(){
        return new Point(-this.x, -this.y);
    }

    public String toString(){
        return "("+this.x+", "+this.y+")";
    }

    /**
     * Permet de savoir l'orientation entre deux points (start et end)
     *
     * @param start the start
     * @param end   the end
     * @return the point
     */
    public static Point getOrientation(Point start, Point end){
        //retourne un Point dont les valeurs de x et y ne peuvent etre que -1, 0 ou 1
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
     * Permet de savoir si un point est égal à un autre. Pour savoir si un point est dépassé
     *
     * @param start       the start
     * @param end         the end
     * @param orientation the orientation
     * @return the boolean
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
     * Permet de connaître la postion d'un point avec un angle et une distance
     *
     * @param pos           the pos
     * @param distance      the distance
     * @param angleInDegree the angle in degree
     * @return the point
     */
    public static Point getPosWithAngle(Point pos, float distance, float angleInDegree){
        //fonction qui retourne un Point qui est a distance de pos avec un angle de angleInDegree de l'axe des abscisses dans le sens des aiguilles d'une montre
        float x = (float) (pos.x + distance * Math.cos(Math.toRadians(angleInDegree)));
        float y = (float) (pos.y + distance * Math.sin(Math.toRadians(angleInDegree)));
        return new Point(x, y);
    }

    /**
     * Multiplie un point par un nombre
     *
     * @param n the n
     * @return the point
     */
    public Point mult(float n){
        return new Point(this.x*n, this.y*n);
    }
}
