package cad.controller;

import java.awt.event.*;

public class CADMouseListener implements MouseListener {
    // int mouseMoveCnt = 0;

    @Override
    public void mouseExited(MouseEvent me) {
        // do nothing
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        /* Todo */
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        if (me.getButton() == MouseEvent.BUTTON1) {
            System.out.println("Left Click");
            Controller.forward(Event.MOUSE_LEFT_CLICK, me);
        } else if (me.getButton() == MouseEvent.BUTTON3) {
            System.out.println("Right Click");
            Controller.forward(Event.MOUSE_RIGHT_CLICK, me);
        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        /* Todo */
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        // do nothing
    }

}