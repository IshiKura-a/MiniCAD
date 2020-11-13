package cad.model;

import java.awt.Color;

public class Rectangle extends Shape {
    private static final long serialVersionUID = 2733861933700256645L;
    private Coordinate start;
    private Coordinate end;

    public Rectangle(Coordinate r) {
        type = ShapeType.RECT;
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
        int maxLBound = (int) (getMinX() * 0.99);
        int maxRBound = (int) (getMaxX() * 1.01);
        int maxDBound = (int) (getMinY() * 0.99);
        int maxUBound = (int) (getMaxY() * 1.01);

        return click.getX() <= maxRBound && click.getX() >= maxLBound && click.getY() <= maxUBound
                && click.getY() >= maxDBound;
    }

    @Override
    public void drawBegin(Coordinate r) {
        start = new Coordinate(r);
        end = new Coordinate(r);
    }

    @Override
    public void drawMoveEnd(Coordinate r) {
        drawMoveEnd(r, false);
    }

    @Override
    public void enlarge() {
        int incWidth = getWidth() / Math.min(getWidth(), getHeight());
        int incHeight = getHeight() / Math.min(getWidth(), getHeight());

        start.move(new Coordinate(-incWidth, -incHeight));
        end.move(new Coordinate(incWidth, incHeight));
    }

    @Override
    public void shrink() {
        int incWidth = getWidth() / Math.min(getWidth(), getHeight());
        int incHeight = getHeight() / Math.min(getWidth(), getHeight());

        end.move(new Coordinate(-incWidth, -incHeight));
        start.move(new Coordinate(incWidth, incHeight));
    }

    public void drawMoveEnd(Coordinate r, boolean isDecided) {
        if (!isDecided) {
            end = new Coordinate(r);
        } else {
            Coordinate tmp = new Coordinate(start);
            start = new Coordinate(Math.min(tmp.getX(), r.getX()), Math.min(tmp.getY(), r.getY()));
            end = new Coordinate(Math.max(tmp.getX(), r.getX()), Math.max(tmp.getY(), r.getY()));
        }
    }

    public int getMinX() {
        return start.getX();
    }

    public int getMaxX() {
        return end.getX();
    }

    public int getMinY() {
        return start.getY();
    }

    public int getMaxY() {
        return end.getY();
    }

    public int getHeight() {
        return end.getY() - start.getY();
    }

    public int getWidth() {
        return end.getX() - start.getX();
    }
}
