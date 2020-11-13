package cad.controller;

import cad.model.*;
import cad.view.*;

import java.awt.event.*;
import java.io.*;

import javax.swing.JOptionPane;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Color;

public class Controller {
    private static Viewer view;
    private static Model model;
    private static FiniteStateMachine fsm;

    private Controller() {
    }

    public static void init() {
        model = new Model();
        fsm = new FiniteStateMachine(model);
        view = new Viewer();
    }

    public static void paintItems(Graphics2D g2D) {
        for (Shape s : model.getShapes()) {
            paint(s, g2D, s == model.getSelectedItem());
        }
    }

    public static void raiseSelectEvent(int btn) {
        if (btn == CADButtonListener.LINE_BUTTON) {
            fsm.forward(Event.DRAW_LINE, null);
        } else if (btn == CADButtonListener.RECT_BUTTON) {
            fsm.forward(Event.DRAW_RECT, null);
        } else if (btn == CADButtonListener.CIRCLE_BUTTON) {
            fsm.forward(Event.DRAW_CIRCLE, null);
        } else if (btn == CADButtonListener.TEXT_BUTTON) {
            fsm.forward(Event.DRAW_TEXT, null);
        } else if (btn == CADButtonListener.PALETTE_BUTTON) {
            fsm.forward(Event.SET_COLOR, null);
        }
    }

    public static void raisePaletteEvent() {
        String result = JOptionPane.showInputDialog(null, "Set Default Color", "Hex RGB:", JOptionPane.PLAIN_MESSAGE);
        if (result.matches("[0-9A-Fa-f]{6,6}")) {
            int r = Integer.parseInt(result.substring(0, 2), 16);
            int g = Integer.parseInt(result.substring(2, 4), 16);
            int b = Integer.parseInt(result.substring(4, 6), 16);
            model.setDefaultColor(new Color(r, g, b));
            view.updateDefaultColor(model.getDefaultColor());
            forward(Event.CHANGE_DEFAULT_COLOR, null);
        } else if (result.matches("(0[xX])?[0-9A-Fa-f]{6,6}")) {
            int r = Integer.parseInt(result.substring(2, 4), 16);
            int g = Integer.parseInt(result.substring(4, 6), 16);
            int b = Integer.parseInt(result.substring(6, 8), 16);
            model.setDefaultColor(new Color(r, g, b));
            view.updateDefaultColor(model.getDefaultColor());
            forward(Event.CHANGE_DEFAULT_COLOR, null);
        }

    }

    public static void paint(Shape s, Graphics2D g2D, boolean isSelected) {
        g2D.setColor(s.getColor(isSelected));
        g2D.setStroke(new BasicStroke(s.getThickness() + (isSelected ? 2 : 0)));

        if (s.getType() == ShapeType.LINE) {
            Line line = (Line) s;

            g2D.drawLine(line.getStart().getX(), line.getStart().getY(), line.getEnd().getX(), line.getEnd().getY());
        } else if (s.getType() == ShapeType.RECT) {
            Rectangle rectangle = (Rectangle) s;
            int x = rectangle.getMinX();
            int y = rectangle.getMinY();
            int width = rectangle.getWidth();
            int height = rectangle.getHeight();

            if (width < 0) {
                x += width;
                width = -width;
            }
            if (height < 0) {
                y += height;
                height = -height;
            }

            g2D.drawRect(x, y, width, height);
        } else if (s.getType() == ShapeType.CIRCLE) {
            Circle circle = (Circle) s;
            int radius = circle.getRadius();
            int x = circle.getX() - radius;
            int y = circle.getY() - radius;

            // !!! Sh*t-like method whose parameters are ALL oval's bounding rectangle.
            g2D.drawOval(x, y, radius * 2, radius * 2);
        } else if (s.getType() == ShapeType.TEXT) {
            CADString string = (CADString) s;

            g2D.setFont(string.getFont());
            g2D.drawString(string.getContent(), string.getX(), string.getY());
        }

    }

    public static State forward(Event e, InputEvent ie) {
        return fsm.forward(e, ie);
    }

    public static void raisePaintEvent() {
        view.repaintCanvus();
    }

    public static void raiseSaveFileEvent(File file) {
        System.out.println("Write to " + file.getName());

        try {
            if (!file.exists())
                file.createNewFile();
        } catch (IOException ioe) {
            System.out.println("Fail to create a new file");
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(model);
        } catch (IOException ioe) {
            System.out.println("Meet output exception");
        }
    }

    public static void raiseOpenFileEvent(File file) {
        System.out.println("Read " + file.getName());

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            model = (Model) in.readObject();
            fsm = new FiniteStateMachine(model);
            raisePaintEvent();
            view.updateDefaultColor(model.getDefaultColor());
        } catch (IOException ioe) {
            System.out.println("Meet input exception");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Class not found");
        }
    }

    public static Color getDefaultColor() {
        return model.getDefaultColor();
    }
}
