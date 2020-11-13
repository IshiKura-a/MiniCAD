package cad.controller;

public enum State {
    INIT(0), LINE_INIT(1), RECT_INIT(2), TEXT_INIT(3), CIRCLE_INIT(4), LINE_MOVE_END(5), RECT_MOVE_END(6),
    CIRCLE_MOVE_END(7), SHAPE_SELECTED(8);

    private int stateCode;

    private State(int stateCode) {
        this.stateCode = stateCode;
    }

    public static int getStateCode(State s) {
        return s.stateCode;
    }

    public static int getStateCodeLimit() {
        return values().length;
    }

    public static State getState(int stateCode) {
        for (State state : values()) {
            if (state.stateCode == stateCode)
                return state;
        }
        return null;
    }
}
