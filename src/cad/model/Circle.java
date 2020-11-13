package cad.model;

import java.awt.*;

public class Circle extends Shape {
    private static final long serialVersionUID = -6744824435026446789L;
    Coordinate start;
    int radius;

    public Circle(Coordinate r) {
        type = ShapeType.CIRCLE;
        color = Color.BLACK;
        thickness = 1;
        start = new Coordinate(r);
        radius = 0;
    }

    @Override
    public void move(Coordinate dif) {
        start.move(dif);
    }

    @Override
    public boolean isChosen(Coordinate click) {
        return Coordinate.getDistance(click, start) <= radius * 1.001;
    }

    @Override
    public void drawBegin(Coordinate r) {
        start = new Coordinate(r);
        radius = 0;
    }

    @Override
    public void drawMoveEnd(Coordinate r) {
        radius = (int) Math.ceil(Coordinate.getDistance(r, start));
    }

    @Override
    public void enlarge() {
        radius++;
    }

    @Override
    public void shrink() {
        if (radius >= 2)
            radius--;
    }

    public int getX() {
        return start.getX();
    }

    public int getY() {
        return start.getY();
    }

    public int getRadius() {
        return radius;
    }
}
