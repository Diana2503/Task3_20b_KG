package com.company.Util;

import java.awt.*;
import java.util.*;
import java.util.stream.Collectors;

public class PolygonHelper {

    public PolygonHelper() {
    }

    public Polygon makePolygon(ArrayList<Rectangle> rectangles) {
        ArrayList<Point> points = calcPoints(rectangles);
        Polygon polygon = new Polygon();
        Collections.reverse(points);
        for (Point point : points) {
            polygon.addPoint(point.x, point.y);
        }
        return polygon;
    }

    private ArrayList<Point> calcPoints(ArrayList<Rectangle> rectangles) {
        ArrayList<Point> ret = new ArrayList<>();

        ArrayList<Integer> yCoords = new ArrayList<>(getAllYCoords(rectangles));
        yCoords.sort(Comparator.naturalOrder());

        int previousLeftCoord = 0;
        int previousRightCoord = 0;

        for (Integer yCoord : yCoords) {
            int minimumXLeftCoord = minXLeftCoord(yCoord, rectangles);
            int maximumXRightCoord = maxXRightCoord(yCoord, rectangles);

            if (yCoord.equals(yCoords.get(0))) {
                ret.add(new Point(minimumXLeftCoord, yCoord));
            } else {
                if (minimumXLeftCoord != previousLeftCoord) {
                    ret.add(0, new Point(previousLeftCoord, yCoord));
                }
                ret.add(0, new Point(minimumXLeftCoord, yCoord));
                if (maximumXRightCoord != previousRightCoord) {
                    ret.add(new Point(previousRightCoord, yCoord));
                }
            }
            ret.add(new Point(maximumXRightCoord, yCoord));
            previousLeftCoord = minimumXLeftCoord;
            previousRightCoord = maximumXRightCoord;
        }
        return ret;
    }

    private Set<Integer> getAllYCoords(ArrayList<Rectangle> rectangles) {
        ArrayList<Integer> allBottomYCoords = rectangles.stream()
                .map(rectangle -> (rectangle.y + rectangle.height))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> allTopYCoords = rectangles.stream()
                .map(rectangle -> (rectangle.y))
                .collect(Collectors.toCollection(ArrayList::new));

        Set<Integer> allCoords = new HashSet<>();
        allCoords.addAll(allTopYCoords);
        allCoords.addAll(allBottomYCoords);
        return allCoords;
    }

    private int minXLeftCoord(Integer y, ArrayList<Rectangle> rectangles) {
        return rectanglesAtY(y, rectangles).stream()
                .map(rect -> (rect.x))
                .min(Comparator.naturalOrder()).get();
    }

    private int maxXRightCoord(Integer y, ArrayList<Rectangle> rectangles) {
        return rectanglesAtY(y, rectangles).stream()
                .map(rect -> (rect.x + rect.width))
                .max(Comparator.naturalOrder()).get();
    }

    private ArrayList<Rectangle> rectanglesAtY(Integer y, ArrayList<Rectangle> rectangles) {
        ArrayList<Rectangle> rectsAtYExcBottomLines = rectsAtYExcBottomLines(y, rectangles);

        if (rectsAtYExcBottomLines.size() > 0) {
            return rectsAtYExcBottomLines;
        } else {
            return rectsAtYIncBottomLines(y, rectangles);
        }
    }

    private ArrayList<Rectangle> rectsAtYExcBottomLines(Integer y, ArrayList<Rectangle> rectangles) {
        return rectangles.stream()
                .filter(rect -> (rect.y) <= y && (rect.y + rect.height) > y)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Rectangle> rectsAtYIncBottomLines(Integer y, ArrayList<Rectangle> rectangles) {
        return rectangles.stream()
                .filter(rect -> (rect.y) <= y && (rect.y + rect.height) == y)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
