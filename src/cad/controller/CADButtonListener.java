package cad.controller;

import java.awt.event.*;

public class CADButtonListener implements MouseListener {
    public static final int LINE_BUTTON = 0;
    public static final int RECT_BUTTON = 1;
    public static final int CIRCLE_BUTTON = 2;
    public static final int TEXT_BUTTON = 3;
    public static final int PALETTE_BUTTON = 4;

    private int button;

    public CADButtonListener(int button) {
        this.button = button;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (button == PALETTE_BUTTON && e.getClickCount() >= 2) {
            Controller.raisePaletteEvent();
        } else
            Controller.raiseSelectEvent(button);
    }

    @Override
    public void mouseExited(MouseEvent me) {
        // do nothing
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        /* Todo */
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
