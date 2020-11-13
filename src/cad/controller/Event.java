package cad.controller;

public enum Event {
    MOUSE_LEFT_CLICK(0), MOUSE_MOVE(1), KEY_ADD(2), KEY_SUB(3), MOUSE_RIGHT_CLICK(4), MOUSE_RELEASE(5), DRAW_LINE(6),
    DRAW_RECT(7), DRAW_TEXT(8), DRAW_CIRCLE(9), MOUSE_DRAGGED(10), KEY_DEL(11), KEY_UP(12), KEY_DOWN(13), KEY_LEFT(14),
    KEY_RIGHT(15), CHANGE_DEFAULT_COLOR(16), KEY_E(17), KEY_S(18), SET_COLOR(19);

    private int eventCode;

    private Event(int eventCode) {
        this.eventCode = eventCode;
    }

    public static int getEventCode(Event e) {
        return e.eventCode;
    }

    public static int getEventCodeLimit() {
        return values().length;
    }

    public static Event getEvent(int eventCode) {
        for (Event e : values()) {
            if (e.eventCode == eventCode)
                return e;
        }
        return null;
    }
}
