package com.company.GUI;

import com.company.Util.PolygonHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MainGUI extends JFrame {
    private ScreenConverter sc;
    private JPanel mainPanel;
    private final ArrayList<RealPoint> points;
    private final ArrayList<MyRectangle> toPoly = new ArrayList<>();
    private MyPolygon poly = null;
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
        this.sc = new ScreenConverter(-16, 16, 32, 32, 1100, 550);
        setDefaults();
        points = new ArrayList<>();
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                zoomRatio = 1;
                int x = e.getX() + 5;
                int y = e.getY() + 35;

                RealPoint newShow;

                switch (points.size()) {
                    case 0:
                        newShow = sc.s2r(new ScreenPoint(x, y));
                        points.add(newShow);
                        break;
                    case 1:
                        newShow = sc.s2r(new ScreenPoint(x, y));
                        points.add(newShow);
                        toPoly.add(new MyRectangle(points));
                        poly = ph.makePolygon(toPoly);
                        points.clear();
                        break;
                }
                repaint();
            }
        });
    }

    @Override
    public void paint(Graphics g) {

        sc.setSw(getWidth());
        sc.setSh(getHeight());

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, this.getWidth(), this.getHeight());
        drawBackground(g2, mainPanel);
        g2.setColor(Color.BLACK);

        try {
            if (points.size() > 0) {
                for (RealPoint point : points) {
                    ScreenPoint sp = sc.r2s(point);
                    g2.drawOval(sp.getC(), sp.getR(), 3, 3);
                    String coords = "x: " + sp.getC() + ";y: " + sp.getR() + ";";
                    g2.drawString(coords, sp.getC(), sp.getR());
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        if (poly != null) {
            counter = toPoly.size();
            for (MyRectangle local : toPoly) {
                local.setX(local.getX() * zoomRatio);
                local.setY(local.getY() * zoomRatio);
                local.setWidth(local.getWidth() * zoomRatio);
                local.setHeight(local.getHeight() * zoomRatio);
            }
            poly = ph.makePolygon(toPoly);

            g2.setColor(Color.BLACK);
            g2.setColor(Color.GREEN);
            poly.draw(g2, sc);
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
