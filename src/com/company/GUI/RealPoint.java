package com.company.GUI;

public class RealPoint {

    /*@Override
    public String toString() {
        return "RealPoint{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }*/

    private double x, y;

    public RealPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public RealPoint minus(RealPoint p) {
        return new RealPoint(getX() - p.getX(), getY() - p.getY());
    }
}
