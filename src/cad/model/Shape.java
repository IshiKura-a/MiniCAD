package cad.model;

import java.awt.*;
import java.io.Serializable;

public abstract class Shape implements Serializable {
    private static final long serialVersionUID = 5804828205127257841L;
    protected ShapeType type;
    protected Color color;
    protected float thickness;

    public abstract boolean isChosen(Coordinate click);

    public abstract void move(Coordinate dif);

    public abstract void drawBegin(Coordinate r);

    public abstract void drawMoveEnd(Coordinate r);

    public abstract void enlarge();

    public abstract void shrink();

    public void thicken() {
        thickness += 0.1;
    }

    public void thin() {
        if (thickness >= 0.2) {
            thickness -= 0.1;
        }
    }

    public ShapeType getType() {
        return type;
    }

    public float getThickness() {
        return thickness;
    }

    public Color getColor(boolean isSelected) {
        if (isSelected) {
            if (color == Color.RED)
                return Color.BLUE;
            else
                return Color.RED;
        }
        return color;
    }

    public void setThickness(float newThickness) {
        thickness = newThickness;
    }

    public void setColor(Color newColor) {
        color = newColor;
    }

}
