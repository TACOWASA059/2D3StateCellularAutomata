package com.github.tacowasa059.multistatecellularautomata2d;

public class Cell {
    public static final int ROCK = 0;//RED
    public static final int SCISSORS = 1;//yellow
    public static final int PAPER = 2;//BLUE

    private int state;

    public Cell(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}