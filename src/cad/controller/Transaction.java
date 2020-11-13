package cad.controller;

import java.awt.event.*;

public abstract class Transaction {
    private TxnIndex index;
    private State nextState;

    protected Transaction(Event event, State curState, State nextState) {
        this.index = new TxnIndex(curState, event);
        this.nextState = nextState;
    }

    protected Transaction(TxnIndex index, State nextState) {
        this.index = index;
        this.nextState = nextState;
    }

    protected Transaction() {
    }

    public abstract State execute(Event e, InputEvent ie);

    public State getCurState() {
        return index.state;
    }

    public State getNextState() {
        return nextState;
    }

    public Event getEvent() {
        return index.event;
    }

}