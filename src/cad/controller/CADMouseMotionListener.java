package cad.controller;

import java.awt.event.*;

public class CADMouseMotionListener implements MouseMotionListener {
    @Override
    public void mouseMoved(MouseEvent me) {
        System.out.println("Mouse Move");
        Controller.forward(Event.MOUSE_MOVE, me);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        System.out.println("Mouse Dragged");
        Controller.forward(Event.MOUSE_DRAGGED, me);
    }
}
