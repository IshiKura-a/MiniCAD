package cad.model;

import java.awt.*;

public class Line extends Shape {
    private static final long serialVersionUID = 4907000607656928358L;
    Coordinate start;
    Coordinate end;

    public Line(Coordinate r) {
        type = ShapeType.LINE;
        color = Color.BLACK;
        thickness = 1;
        start = new Coordinate(r);
        end = new Coordinate(r);
    }

    @Override
    public void move(Coordinate dif) {
        start.move(dif);
        end.move(dif);
    }

    @Override
    public boolean isChosen(Coordinate click) {
        Coordinate posDir = new Coordinate(0, (int) Math.ceil(thickness / 2));
        Coordinate negDir = new Coordinate(0, -(int) Math.ceil(thickness / 2));
        Coordinate a = Coordinate.getSum(start, posDir);
        Coordinate b = Coordinate.getSum(start, negDir);
        Coordinate c = Coordinate.getSum(end, negDir);
        Coordinate d = Coordinate.getSum(end, negDir);

        return Coordinate.sumTriangleArea(a, b, c, d, click) <= (thickness + 5) * Coordinate.getDistance(start, end);
    }

    @Override
    public void drawBegin(Coordinate r) {
        start = new Coordinate(r);
        end = new Coordinate(r);
    }

    @Override
    public void drawMoveEnd(Coordinate r) {
        end = new Coordinate(r);
    }

    @Override
    public void enlarge() {
        Coordinate dir = Coordinate.getDifference(start, end);
        int size = Math.min(Math.abs(dir.getX()), Math.abs(dir.getY()));
        Coordinate dif = new Coordinate(dir.getX() / size, dir.getY() / size);
        end.move(Coordinate.getDifference(new Coordinate(0, 0), dif));
        start.move(dif);
    }

    @Override
    public void shrink() {
        Coordinate dir = Coordinate.getDifference(start, end);
        int size = Math.min(Math.abs(dir.getX()), Math.abs(dir.getY()));
        Coordinate dif = new Coordinate(dir.getX() / size, dir.getY() / size);
        start.move(Coordinate.getDifference(new Coordinate(0, 0), dif));
        end.move(dif);
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        return end;
    }
}
