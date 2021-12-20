package com.company.GUI;

import java.awt.*;
import java.util.*;
import java.util.List;

public class MyPolygon implements Drawing {
    List<RealPoint> pointList = new ArrayList<>();

    public void addPoint(RealPoint point) {
        pointList.add(point);
    }

    @Override
    public void draw(Graphics2D graphics2D, ScreenConverter screenConverter) {
        int[] x1 = new int[pointList.size()];
        int[] y1 = new int[pointList.size()];

        for (int i = 0; i < pointList.size() ; i++) {
            ScreenPoint c =screenConverter.r2s(new RealPoint(pointList.get(i).getX(), pointList.get(i).getY()));
            x1[i] = c.getC();
            y1[i] = c.getR();
        }
        //System.out.println(Arrays.toString(x1));
        //System.out.println(Arrays.toString(y1));
        graphics2D.fillPolygon(x1, y1, pointList.size());
    }
}
