package com.company.GUI;

import java.awt.*;
import java.util.List;

public class MyRectangle implements Drawing {
    private RealPoint firstPoint;
    private double width;
    private double height;

    public MyRectangle(RealPoint firstPoint, double width, double height) {
        this.firstPoint = firstPoint;
        this.width = width;
        this.height = height;
    }

    public MyRectangle(List<RealPoint> points) {
        double width = Math.abs(points.get(0).getX() - points.get(1).getX());
        double height = Math.abs(points.get(0).getY() - points.get(1).getY());

        if (points.get(1).getX() < points.get(0).getX() && points.get(1).getY() < points.get(0).getY()) {
            this.firstPoint = new RealPoint(points.get(1).getX(),points.get(1).getY());
            this.width = width;
            this.height = height;
        } else if (points.get(1).getX() > points.get(0).getX() && points.get(1).getY() < points.get(0).getY()) {
            this.firstPoint = new RealPoint(points.get(0).getX(),points.get(1).getY());
            this.width = width;
            this.height = height;
        } else if (points.get(1).getX() < points.get(0).getX() && points.get(1).getY() > points.get(0).getY()) {
            this.firstPoint = new RealPoint(points.get(1).getX(),points.get(0).getY());
            this.width = width;
            this.height = height;
        } else {
            this.firstPoint = new RealPoint(points.get(0).getX(),points.get(0).getY());
            this.width = width;
            this.height = height;
        }
    }

    public double getX() {
        return firstPoint.getX();
    }


    public double getY() {
        return firstPoint.getY();
    }

    public void setX(double x) {
        setFirstPoint(new RealPoint(x,getY()));
    }

    public void setY(double y) {
        setFirstPoint(new RealPoint(getX(),y));
    }

    public void setFirstPoint(RealPoint firstPoint) {
        this.firstPoint = firstPoint;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public void draw(Graphics2D graphics2D, ScreenConverter screenConverter) {

    }
}
