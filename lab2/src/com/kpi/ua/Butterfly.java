package com.kpi.ua;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Arrays;

public class Butterfly extends JPanel {
    private static final double BUTTERFLY_BODY_RADIUS_Y = 100.0;
    private static final double BUTTERFLY_BODY_RADIUS_X = 50.0;
    private static final Color BUTTERFLY_BODY_COLOR = new Color(173, 255, 47);
    private static final double BUTTERFLY_WING_LENGTH = 150;
    private static final Color BUTTERFLY_WING_COLOR = new Color(0, 255, 255);
    private static final double ANTENNAE_OFFSET_Y = 20;
    private static final double ANTENNAE_OFFSET_X = 10;
    private static final double BUTTERFLY_ANTENNAE_LENGTH = 100;
    private static final Color BUTTERFLY_ANTENNAE_COLOR = new Color(175, 175, 175);
    private static final double COEFFICIENT_TO_MAKE_TRIANGLE_EQUILATERAL = Math.sqrt(0.75);
    private static final double ANTENNAE_DEGREE = 15;

    private final double sceneWidth;
    private final double sceneHeight;
    private final double butterflyBodyCenterX;
    private final double butterflyBodyCenterY;
    private final double butterflyWingCenterX;
    private final double butterflyWingCenterY;
    private final double butterflyAntennaeStartY;

    public Butterfly(double sceneWidth, double sceneHeight) {
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;
        double sceneWidthCenter = sceneWidth / 2;
        double sceneHeightCenter = sceneHeight / 2;
        butterflyBodyCenterX = sceneWidthCenter;
        butterflyBodyCenterY = sceneHeightCenter - 250;
        butterflyWingCenterX = butterflyBodyCenterX;
        butterflyWingCenterY = butterflyBodyCenterY;
        butterflyAntennaeStartY = butterflyWingCenterY - BUTTERFLY_BODY_RADIUS_Y + ANTENNAE_OFFSET_Y;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        setUpGraphicsCanvas(g2d);

        Ellipse2D ellipse = getButterflyBody();
        Point[] wingCoordinates = {
                new Point(butterflyWingCenterX,
                        butterflyWingCenterY),
                new Point(butterflyWingCenterX - (BUTTERFLY_WING_LENGTH / 2),
                        butterflyWingCenterY - BUTTERFLY_WING_LENGTH * COEFFICIENT_TO_MAKE_TRIANGLE_EQUILATERAL),
                new Point(butterflyWingCenterX - BUTTERFLY_WING_LENGTH,
                        butterflyWingCenterY)
        };
        Shape leftTopWing = getWing(wingCoordinates, 10);
        Shape leftBottomWing = getWing(wingCoordinates, 110);
        Shape rightTopWing = getWing(wingCoordinates, 200);
        Shape rightBottomWing = getWing(wingCoordinates, 280);
        Shape leftAntennae = getButterflyAntennae(
                new Point(butterflyBodyCenterX - ANTENNAE_OFFSET_X, butterflyAntennaeStartY),
                new Point(butterflyBodyCenterX - ANTENNAE_OFFSET_X,
                        butterflyAntennaeStartY - BUTTERFLY_ANTENNAE_LENGTH),
                -ANTENNAE_DEGREE
        );
        Shape rightAntennae = getButterflyAntennae(
                new Point(butterflyBodyCenterX + ANTENNAE_OFFSET_X, butterflyAntennaeStartY),
                new Point(butterflyBodyCenterX + ANTENNAE_OFFSET_X,
                        butterflyAntennaeStartY - BUTTERFLY_ANTENNAE_LENGTH),
                ANTENNAE_DEGREE
        );

        //body
        g2d.setColor(BUTTERFLY_BODY_COLOR);
        g2d.fill(ellipse);

        //wings
        g2d.setColor(BUTTERFLY_WING_COLOR);
        g2d.fill(leftTopWing);
        g2d.fill(leftBottomWing);
        g2d.fill(rightTopWing);
        g2d.fill(rightBottomWing);

        //antennas
        g2d.setColor(BUTTERFLY_ANTENNAE_COLOR);
        g2d.draw(leftAntennae);
        g2d.draw(rightAntennae);
    }

    private void setUpGraphicsCanvas(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
    }

    private Shape getButterflyAntennae(Point start, Point end, double angle) {
        Line2D line = new Line2D.Double(start.getX(), start.getY(), end.getX(), end.getY());
        return getRotatedShape(start, angle, line);
    }

    private Shape getRotatedShape(Point start, double angle, Shape shape) {
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(angle),
                start.getX(), start.getY());
        return at.createTransformedShape(shape);
    }

    private Shape getWing(Point[] points, double rotateAngle) {
        Polygon polygon = new Polygon();
        Arrays.stream(points).forEach(p -> polygon.addPoint(p.getX().intValue(), p.getY().intValue()));
        return getRotatedShape(points[0], rotateAngle, polygon);
    }

    private Ellipse2D getButterflyBody() {
        return new Ellipse2D.Double(butterflyBodyCenterX - BUTTERFLY_BODY_RADIUS_X, butterflyBodyCenterY - BUTTERFLY_BODY_RADIUS_Y,
                BUTTERFLY_BODY_RADIUS_X * 2, BUTTERFLY_BODY_RADIUS_Y * 2);
    }
}
