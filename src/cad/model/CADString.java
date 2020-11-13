package cad.model;

import java.awt.*;
import java.awt.font.FontRenderContext;

public class CADString extends Shape {
    private static final long serialVersionUID = 5271389679950864860L;
    Coordinate start;
    Font font;
    String fontName;
    int fontSize;
    String content;

    public CADString(Coordinate r) {
        type = ShapeType.TEXT;
        color = Color.BLACK;
        thickness = 1;
        start = new Coordinate(r);
        fontName = "Courier New";
        fontSize = 24;
        font = new Font(fontName, Font.PLAIN, fontSize);
    }

    @Override
    public void move(Coordinate dif) {
        start.move(dif);
    }

    @Override
    public boolean isChosen(Coordinate click) {
        int width = (int) Math
                .ceil(font.getStringBounds(content, new FontRenderContext(null, false, false)).getWidth());
        int height = (int) Math
                .ceil(font.getStringBounds(content, new FontRenderContext(null, false, false)).getHeight());

        return click.getX() <= start.getX() + width && click.getX() >= start.getX() && click.getY() <= start.getY()
                && click.getY() >= start.getY() - height;
    }

    @Override
    public void drawBegin(Coordinate r) {
        start = new Coordinate(r);
    }

    @Override
    public void drawMoveEnd(Coordinate r) {
        System.out.println("Using Abandoned Method: CADString.drawMoveEnd(Coordinate)");
    }

    @Override
    public void enlarge() {
        fontSize++;
        font = new Font(fontName, Font.PLAIN, fontSize);
    }

    @Override
    public void shrink() {
        if (fontSize >= 2) {
            fontSize--;
            font = new Font(fontName, Font.PLAIN, fontSize);
        }
    }

    public void setContent(String s) {
        content = s;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
        font = new Font(fontName, Font.PLAIN, fontSize);
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
        font = new Font(fontName, Font.PLAIN, fontSize);
    }

    public String getContent() {
        return content;
    }

    public Font getFont() {
        return font;
    }

    public int getX() {
        return start.getX();
    }

    public int getY() {
        return start.getY();
    }

    public int getFontSize() {
        return fontSize;
    }
}
