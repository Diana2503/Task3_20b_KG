package com.company.Util;

import com.company.GUI.MyPolygon;
import com.company.GUI.MyRectangle;
import com.company.GUI.RealPoint;

import java.util.*;
import java.util.stream.Collectors;

public class PolygonHelper {

    public PolygonHelper() {
    }

    public MyPolygon makePolygon(ArrayList<MyRectangle> myRectangles) {
        ArrayList<RealPoint> points = calcPoints(myRectangles);
        //System.out.println(myRectangles);
        //System.out.println(points);
        MyPolygon polygon = new MyPolygon();
        Collections.reverse(points);
        for (RealPoint point : points) {
            polygon.addPoint(point);
        }
        return polygon;
    }

    private ArrayList<RealPoint> calcPoints(ArrayList<MyRectangle> myRectangles) {
        ArrayList<RealPoint> ret = new ArrayList<>();

        ArrayList<Double> yCoords = new ArrayList<>(getAllYCoords(myRectangles));
        yCoords.sort(Comparator.naturalOrder());

        double previousLeftCoord = 0.0;
        double previousRightCoord = 0.0;

        for (double yCoord : yCoords) {
            double minimumXLeftCoord = minXLeftCoord(yCoord, myRectangles);
            double maximumXRightCoord = maxXRightCoord(yCoord, myRectangles);

            if (yCoord == yCoords.get(0)) {
                ret.add(new RealPoint((int) minimumXLeftCoord, (int) yCoord));
            } else {
                if (minimumXLeftCoord != previousLeftCoord) {
                    ret.add(0, new RealPoint((int) previousLeftCoord, (int) yCoord));
                }
                ret.add(0, new RealPoint((int) minimumXLeftCoord, (int) yCoord));
                if (maximumXRightCoord != previousRightCoord) {
                    ret.add(new RealPoint((int) previousRightCoord, (int) yCoord));
                }
            }
            ret.add(new RealPoint((int) maximumXRightCoord, (int) yCoord));
            previousLeftCoord = minimumXLeftCoord;
            previousRightCoord = maximumXRightCoord;
        }
        return ret;
    }

    private Set<Double> getAllYCoords(ArrayList<MyRectangle> rectangles) {
        ArrayList<Double> allBottomYCoords = rectangles.stream()
                .map(rectangle -> (rectangle.getY() + rectangle.getHeight()))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Double> allTopYCoords = rectangles.stream()
                .map(MyRectangle::getY)
                .collect(Collectors.toCollection(ArrayList::new));

        Set<Double> allCoords = new HashSet<>();
        allCoords.addAll(allTopYCoords);
        allCoords.addAll(allBottomYCoords);
        return allCoords;
    }

    private Double minXLeftCoord(Double y, ArrayList<MyRectangle> rectangles) {
        return rectanglesAtY(y, rectangles).stream()
                .map(MyRectangle::getX)
                .min(Comparator.naturalOrder()).get();
    }

    private Double maxXRightCoord(Double y, ArrayList<MyRectangle> rectangles) {
        return rectanglesAtY(y, rectangles).stream()
                .map(rect -> (rect.getX() + rect.getWidth()))
                .max(Comparator.naturalOrder()).get();
    }

    private ArrayList<MyRectangle> rectanglesAtY(Double y, ArrayList<MyRectangle> rectangles) {
        ArrayList<MyRectangle> rectsAtYExcBottomLines = rectsAtYExcBottomLines(y, rectangles);

        if (rectsAtYExcBottomLines.size() > 0) {
            return rectsAtYExcBottomLines;
        } else {
            return rectsAtYIncBottomLines(y, rectangles);
        }
    }

    private ArrayList<MyRectangle> rectsAtYExcBottomLines(Double y, ArrayList<MyRectangle> rectangles) {
        return rectangles.stream()
                .filter(rect -> (rect.getY()) <= y && (rect.getY() + rect.getHeight()) > y)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<MyRectangle> rectsAtYIncBottomLines(Double y, ArrayList<MyRectangle> rectangles) {
        return rectangles.stream()
                .filter(rect -> (rect.getY()) <= y && (rect.getY() + rect.getHeight()) == y)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
