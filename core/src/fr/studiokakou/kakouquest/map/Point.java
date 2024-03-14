package fr.studiokakou.kakouquest.map;

public class Point {
    public float x;
    public float y;

    public Point(float x, float y){
        this.x=x;
        this.y=y;
    }

    public Point add(float x, float y){
        return new Point(this.x+x, this.y+y);
    }

    public Point add(Point p){
        return new Point(this.x+p.x, this.y+p.y);
    }

    public Point reverse(){
        return new Point(-this.x, -this.y);
    }
}
