package cad.model;

import java.io.Serializable;

public class Coordinate implements Serializable {
    private static final long serialVersionUID = 6018840439680632329L;
    private int x;
    private int y;

    public Coordinate() {
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate r) {
        this(r.x, r.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void set(Coordinate r) {
        this.x = r.x;
        this.y = r.y;
    }

    public static Coordinate getMiddle(Coordinate p, Coordinate q) {
        return new Coordinate((p.getX() + q.getX()) / 2, (p.getY() + q.getY()) / 2);
    }

    public static Coordinate getDifference(Coordinate p, Coordinate q) {
        return new Coordinate(p.getX() - q.getX(), p.getY() - q.getY());
    }

    public static Coordinate getSum(Coordinate p, Coordinate q) {
        return new Coordinate(p.getX() + q.getX(), p.getY() + q.getY());
    }

    public static double getDistance(Coordinate p, Coordinate q) {
        Coordinate dif = getDifference(p, q);
        int difX = dif.getX();
        int difY = dif.getY();
        return Math.sqrt((double) difX * difX + difY * difY);
    }

    public static double getTriangleArea(Coordinate a, Coordinate b, Coordinate c) {
        return 0.5 * Math.abs((b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y));
    }

    public static double sumTriangleArea(Coordinate a, Coordinate b, Coordinate c, Coordinate d, Coordinate x) {
        return getTriangleArea(a, b, x) + getTriangleArea(b, d, x) + getTriangleArea(d, c, x)
                + getTriangleArea(c, a, x);
    }

    public void move(Coordinate dir) {
        set(getSum(this, dir));
    }

    public static int compare(Coordinate p, Coordinate q) {
        if (p.x < q.x)
            return -1;
        else if (p.x > q.x)
            return 1;
        else {
            if (p.y < q.y)
                return -1;
            else if (p.y > q.y)
                return 1;
            else
                return 0;
        }
    }
}
