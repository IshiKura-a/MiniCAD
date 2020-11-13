package cad.model;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.io.Serializable;

public class Model implements Serializable {
    private static final long serialVersionUID = 4601482783643467824L;
    private ArrayList<Shape> item;
    private Shape selectedItem;
    private Coordinate dragBegiCoordinate;
    private Color defaultColor;

    public Model() {
        item = new ArrayList<>();
        selectedItem = null;
        defaultColor = new Color(0, 0, 0);
    }

    public Shape shapeAt(Coordinate click) {
        for (Shape s : item) {
            if (s.isChosen(click))
                return s;
        }
        return null;
    }

    public void addShape(Shape s) {
        item.add(s);
    }

    public Shape getTopShape() {
        return item.get(item.size() - 1);
    }

    public List<Shape> getShapes() {
        return item;
    }

    public boolean removeTopShape() {
        if (item.isEmpty())
            return false;
        else {
            item.remove(item.size() - 1);
            return true;
        }
    }

    public void setSelectedItem(Shape s) {
        selectedItem = s;
    }

    public Shape getSelectedItem() {
        return selectedItem;
    }

    public void deleteSelectedItem() {
        item.remove(selectedItem);
        selectedItem = null;
    }

    public Coordinate getDragBeginCoordinate() {
        return dragBegiCoordinate;
    }

    public void setDragBeginCoordinate(Coordinate r) {
        if (r == null)
            dragBegiCoordinate = null;
        else
            dragBegiCoordinate = new Coordinate(r);
    }

    public Color getDefaultColor() {
        return defaultColor;
    }

    public void setDefaultColor(Color c) {
        defaultColor = new Color(c.getRGB());
    }
}
