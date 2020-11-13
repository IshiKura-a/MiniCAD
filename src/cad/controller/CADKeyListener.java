package cad.controller;

import java.awt.event.*;

public class CADKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent ke) {
        // do nothing
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        System.out.print(ke.getKeyChar());
        System.out.print(" : ");
        System.out.println(ke.getKeyCode());
        if (ke.getKeyCode() == KeyEvent.VK_ADD) {
            Controller.forward(Event.KEY_ADD, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_SUBTRACT) {
            Controller.forward(Event.KEY_SUB, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_DELETE || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            Controller.forward(Event.KEY_DEL, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_UP) {
            Controller.forward(Event.KEY_UP, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_DOWN) {
            Controller.forward(Event.KEY_DOWN, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_LEFT) {
            Controller.forward(Event.KEY_LEFT, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_RIGHT) {
            Controller.forward(Event.KEY_RIGHT, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_E) {
            Controller.forward(Event.KEY_E, ke);
        } else if (ke.getKeyCode() == KeyEvent.VK_S) {
            Controller.forward(Event.KEY_S, ke);
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        // do nothing
    }
}
