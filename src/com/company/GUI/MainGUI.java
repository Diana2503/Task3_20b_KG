package com.company.GUI;

import com.company.Util.PolygonHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

public class MainGUI extends JFrame {
    private JPanel mainPanel;
    private final ArrayList<Point> points;
    private final ArrayList<Rectangle> toPoly = new ArrayList<>();
    private Polygon poly = null;
    private final PolygonHelper ph = new PolygonHelper();
    private double zoomRatio = 1;

    private int counter = 0;

    public void zoom(boolean argue) {
        if (argue) {
            if (zoomRatio < 0) zoomRatio = 1;
            zoomRatio += 0.01;
            repaint();
        } else {
            if (zoomRatio > 0.1) {
                if (zoomRatio > 0) zoomRatio = 1;
                zoomRatio -= 0.01;
                repaint();
            }
        }
    }

    private void setDefaults() {
        setContentPane(mainPanel);
        setBounds(0, 0, 100, 100);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public MainGUI() {
        setDefaults();
        points = new ArrayList<>();
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                zoomRatio = 1;
                int x = e.getX() + 5;
                int y = e.getY() + 25;

                Point newShow;

                switch (points.size()) {
                    case 0:
                        //newShow = sc.real2Screen(new ScreenPoint(x, y));
                        newShow = new Point(x, y);
                        points.add(newShow);
                        break;
                    case 1:
                        newShow = new Point(x, y);
                        points.add(newShow);

                        Rectangle generated;

                        int width = Math.abs(points.get(0).x - points.get(1).x);
                        int height = Math.abs(points.get(0).y - points.get(1).y);

                        if (x < points.get(0).x && y < points.get(0).y) {
                            generated = new Rectangle(x, y, width, height);
                        } else if (x > points.get(0).x && y < points.get(0).y) {
                            generated = new Rectangle(points.get(0).x, y, width, height);
                        } else if (x < points.get(0).x && y > points.get(0).y) {
                            generated = new Rectangle(x, points.get(0).y, width, height);
                        } else {
                            generated = new Rectangle(points.get(0).x, points.get(0).y, width, height);
                        }

                        toPoly.add(generated);
                        poly = ph.makePolygon(toPoly);
                        points.clear();
                }
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawBackground(g2, mainPanel);
        g2.setColor(Color.BLACK);
        try {
            if (points.size() > 0) {
                for (Point point : points) {
                    //ScreenPoint sp = sc.real2Screen(point);
                    g2.drawOval(point.x, point.y, 3, 3);
                    String coords = "x: " + point.x + ";y: " + point.y + ";";
                    g2.drawString(coords, point.x + 5, point.y + 5);
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (poly != null) {
            counter = toPoly.size();
            for (Rectangle local : toPoly) {
                local.x = (int) (local.x * zoomRatio);
                local.y = (int) (local.y * zoomRatio);
                local.width = (int) (local.width * zoomRatio);
                local.height = (int) (local.height * zoomRatio);
            }
            poly = ph.makePolygon(toPoly);

            g2.setColor(Color.BLACK);
            g2.drawPolygon(poly);
            g2.setColor(Color.GREEN);
            g2.fillPolygon(poly);
        }
    }

    public void clear() {
        poly = null;
        toPoly.clear();
        counter = 0;
        repaint();
    }

    public int getCounter() {
        return counter;
    }

    private void drawBackground(Graphics2D g, JPanel panel) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(new Color(0xD7D7D7));
        int supGH = panel.getHeight();
        int supGW = panel.getWidth();
        for (int i = 0; i < supGH / 20; i++) {
            g.drawLine(0, i * 20, supGW, i * 20);
        }
        for (int i = 0; i < supGW / 20; i++) {
            g.drawLine(i * 20, 0, i * 20, supGH);
        }
    }
}
