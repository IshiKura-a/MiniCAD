package cad.controller;

import java.util.*;

import javax.swing.JOptionPane;

import cad.model.*;

import java.awt.event.*;

public class FiniteStateMachine {
    private HashMap<TxnIndex, Transaction> txnMap;
    private Model model;
    private State curState;

    public FiniteStateMachine(Model m) {
        txnMap = new HashMap<>();
        this.model = m;
        curState = State.INIT;

        // Draw a line
        TxnIndex tmp = new TxnIndex(State.INIT, Event.DRAW_LINE);
        txnMap.put(tmp, new Transaction(tmp, State.LINE_INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                return State.LINE_INIT;
            }
        });

        tmp = new TxnIndex(State.LINE_INIT, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.LINE_MOVE_END) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                Line newLine = new Line(new Coordinate(me.getX(), me.getY()));
                newLine.setColor(Controller.getDefaultColor());
                model.addShape(newLine);
                Controller.raisePaintEvent();
                return State.LINE_MOVE_END;
            }
        });

        tmp = new TxnIndex(State.LINE_MOVE_END, Event.MOUSE_MOVE);
        txnMap.put(tmp, new Transaction(tmp, State.LINE_MOVE_END) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.getTopShape().drawMoveEnd(new Coordinate(me.getX(), me.getY()));
                Controller.raisePaintEvent();
                return State.LINE_MOVE_END;
            }
        });

        tmp = new TxnIndex(State.LINE_MOVE_END, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.getTopShape().drawMoveEnd(new Coordinate(me.getX(), me.getY()));
                Controller.raisePaintEvent();
                return State.INIT;
            }
        });

        // Draw a rectangle
        tmp = new TxnIndex(State.INIT, Event.DRAW_RECT);
        txnMap.put(tmp, new Transaction(tmp, State.RECT_INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                return State.RECT_INIT;
            }
        });

        tmp = new TxnIndex(State.RECT_INIT, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.RECT_MOVE_END) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                Rectangle newRectangle = new Rectangle(new Coordinate(me.getX(), me.getY()));
                newRectangle.setColor(Controller.getDefaultColor());
                model.addShape(newRectangle);
                Controller.raisePaintEvent();
                return State.RECT_MOVE_END;
            }
        });

        tmp = new TxnIndex(State.RECT_MOVE_END, Event.MOUSE_MOVE);
        txnMap.put(tmp, new Transaction(tmp, State.RECT_MOVE_END) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.getTopShape().drawMoveEnd(new Coordinate(me.getX(), me.getY()));
                Controller.raisePaintEvent();
                return State.RECT_MOVE_END;
            }
        });

        tmp = new TxnIndex(State.RECT_MOVE_END, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                ((Rectangle) model.getTopShape()).drawMoveEnd(new Coordinate(me.getX(), me.getY()), true);
                Controller.raisePaintEvent();
                return State.INIT;
            }
        });

        // Draw a text
        tmp = new TxnIndex(State.INIT, Event.DRAW_TEXT);
        txnMap.put(tmp, new Transaction(tmp, State.TEXT_INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                CADString newString = new CADString(new Coordinate(0, 0));
                newString.setColor(Controller.getDefaultColor());
                String content = JOptionPane.showInputDialog(null, "Please Input", "Set text content",
                        JOptionPane.PLAIN_MESSAGE);

                if (content == null) {
                    return State.INIT;
                } else {
                    newString.setContent(content);

                    model.addShape(newString);
                    return State.TEXT_INIT;
                }

            }
        });

        tmp = new TxnIndex(State.TEXT_INIT, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.getTopShape().drawBegin(new Coordinate(me.getX(), me.getY()));
                Controller.raisePaintEvent();
                return State.INIT;
            }
        });

        // Draw a circle
        tmp = new TxnIndex(State.INIT, Event.DRAW_CIRCLE);
        txnMap.put(tmp, new Transaction(tmp, State.CIRCLE_INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                return State.CIRCLE_INIT;
            }
        });

        tmp = new TxnIndex(State.CIRCLE_INIT, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.CIRCLE_MOVE_END) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                Circle newCircle = new Circle(new Coordinate(me.getX(), me.getY()));
                newCircle.setColor(Controller.getDefaultColor());
                model.addShape(newCircle);
                Controller.raisePaintEvent();
                return State.CIRCLE_MOVE_END;
            }
        });

        tmp = new TxnIndex(State.CIRCLE_MOVE_END, Event.MOUSE_MOVE);
        txnMap.put(tmp, new Transaction(tmp, State.CIRCLE_MOVE_END) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.getTopShape().drawMoveEnd(new Coordinate(me.getX(), me.getY()));
                Controller.raisePaintEvent();
                return State.CIRCLE_MOVE_END;
            }
        });

        tmp = new TxnIndex(State.CIRCLE_MOVE_END, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.INIT) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.getTopShape().drawMoveEnd(new Coordinate(me.getX(), me.getY()));
                Controller.raisePaintEvent();
                return State.INIT;
            }
        });

        // Select item
        tmp = new TxnIndex(State.INIT, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.setSelectedItem(null);
                model.setDragBeginCoordinate(null);
                for (Shape s : model.getShapes()) {
                    if (s.isChosen(new Coordinate(me.getX(), me.getY()))) {
                        model.setSelectedItem(s);
                        Controller.raisePaintEvent();
                        return State.SHAPE_SELECTED;
                    }
                }
                return State.INIT;
            }
        });

        tmp = new TxnIndex(State.INIT, Event.MOUSE_DRAGGED);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                model.setSelectedItem(null);
                model.setDragBeginCoordinate(null);
                for (Shape s : model.getShapes()) {
                    if (s.isChosen(new Coordinate(me.getX(), me.getY()))) {
                        model.setSelectedItem(s);
                        Controller.raisePaintEvent();
                        return State.SHAPE_SELECTED;
                    }
                }
                return State.INIT;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_ADD);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                System.out.println("Thicken");
                model.getSelectedItem().thicken();
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_SUB);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().thin();
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_UP);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().move(new Coordinate(0, -1));
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_DOWN);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().move(new Coordinate(0, 1));
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_LEFT);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().move(new Coordinate(-1, 0));
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_RIGHT);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().move(new Coordinate(1, 0));
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.MOUSE_LEFT_CLICK);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                Coordinate click = new Coordinate(me.getX(), me.getY());
                model.setSelectedItem(null);
                model.setDragBeginCoordinate(null);
                for (Shape s : model.getShapes()) {
                    if (s.isChosen(click)) {
                        model.setSelectedItem(s);
                        Controller.raisePaintEvent();
                        return State.SHAPE_SELECTED;
                    }
                }
                Controller.raisePaintEvent();
                return State.INIT;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_DEL);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.deleteSelectedItem();
                Controller.raisePaintEvent();
                return State.INIT;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.MOUSE_DRAGGED);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                MouseEvent me = (MouseEvent) ie;
                Coordinate cur = new Coordinate(me.getX(), me.getY());
                if (model.getDragBeginCoordinate() == null) {
                    model.setDragBeginCoordinate(cur);
                } else {
                    model.getSelectedItem().move(Coordinate.getDifference(cur, model.getDragBeginCoordinate()));
                    model.setDragBeginCoordinate(cur);
                }
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.CHANGE_DEFAULT_COLOR);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().setColor(Controller.getDefaultColor());
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.SET_COLOR);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().setColor(Controller.getDefaultColor());
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_E);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().enlarge();
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });

        tmp = new TxnIndex(State.SHAPE_SELECTED, Event.KEY_S);
        txnMap.put(tmp, new Transaction(tmp, State.SHAPE_SELECTED) {
            @Override
            public State execute(Event e, InputEvent ie) {
                model.getSelectedItem().shrink();
                Controller.raisePaintEvent();
                return State.SHAPE_SELECTED;
            }
        });
    }

    public State forward(Event e, InputEvent ie) {
        TxnIndex index = new TxnIndex(curState, e);
        Transaction txn = txnMap.get(index);
        if (txn != null) {
            curState = txn.execute(e, ie);
        }
        System.out.println(curState);
        return curState;
    }
}

class TxnIndex {
    State state;
    Event event;

    public TxnIndex(State s, Event e) {
        state = s;
        event = e;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        TxnIndex index = (TxnIndex) o;
        return (index.getState() == this.state) && (index.getEvent() == this.event);
    }

    @Override
    public int hashCode() {
        return State.getStateCode(state) * Event.getEventCodeLimit() + Event.getEventCode(event);
    }

    public State getState() {
        return state;
    }

    public Event getEvent() {
        return event;
    }
}
