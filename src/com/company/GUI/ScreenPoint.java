package com.company.GUI;

public class ScreenPoint {
    private final int c;
    private final int r;

    public ScreenPoint(int c, int r) {
        this.c = c;
        this.r = r;
    }

    public int getC() {
        return c;
    }

    public int getR() {
        return r;
    }

    /*@Override
    public String toString() {
        return "ScreenPoint{" +
                "c=" + c +
                ", r=" + r +
                '}';
    }*/
}
