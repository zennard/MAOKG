package com.kpi.ua;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

public class StarAnimation extends JPanel implements ActionListener {
    private static final int STROKE_WIDTH = 3;
    private final Point startPoint;
    private final int borderWidth;
    private final int borderHeight;

    //star
    private double[][] points;
    private Timer timer;
    private double scale = 1;
    private double delta = 0.01;
    private double angle = 0;
    private int sceneWidth;
    private int sceneHeight;
    private double widthCenter;
    private double heightCenter;

    public StarAnimation(Point startPoint, int borderWidth, int borderHeight) {
        this.startPoint = new Point(startPoint.getX(), startPoint.getY());
        this.borderWidth = borderWidth;
        this.borderHeight = borderHeight;

        this.setLayout(new GridLayout(1, 1));
        this.sceneWidth = borderWidth - STROKE_WIDTH;
        this.sceneHeight = borderHeight - STROKE_WIDTH;
        widthCenter = startPoint.getX() + sceneWidth / 2.0;
        heightCenter = startPoint.getY() + sceneHeight / 2.0;
        timer = new Timer(10, this);
        timer.start();
        points = new double[][]{
                {widthCenter, heightCenter + 25}, {widthCenter + 75, heightCenter + 15},
                {widthCenter + 100, heightCenter - 50}, {widthCenter + 125, heightCenter + 15},
                {widthCenter + 200, heightCenter + 25}, {widthCenter + 150, heightCenter + 65},
                {widthCenter + 160, heightCenter + 130}, {widthCenter + 100, heightCenter + 90},
                {widthCenter + 40, heightCenter + 130}, {widthCenter + 50, heightCenter + 65},
                {widthCenter, heightCenter + 25}
        };
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        setUpGraphicsCanvas(g2d);
        Rectangle rectangle = new Rectangle(startPoint.getX().intValue(),
                startPoint.getY().intValue(), borderWidth, borderHeight);
        g2d.setStroke(new BasicStroke(STROKE_WIDTH, BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_BEVEL));
        g2d.draw(rectangle);

        GradientPaint gp = new GradientPaint(5, 25,
                new Color(255, 255, 0), 20, 2, new Color(0, 0, 255), true);
        g2d.setPaint(gp);
        g2d.clearRect(startPoint.getX().intValue() + STROKE_WIDTH, startPoint.getY().intValue() + STROKE_WIDTH,
                sceneWidth + 1 - STROKE_WIDTH, sceneHeight + 1 - STROKE_WIDTH);

        GeneralPath star = getStar();
        g2d.rotate(angle, 580, 190);
        g2d.fill(star);
        g2d.draw(new Rectangle((int) widthCenter, (int) heightCenter, (int) (100 * scale), (int) (100 * scale)));
    }

    private GeneralPath getStar() {
        GeneralPath star = new GeneralPath();
        star.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            star.lineTo(points[k][0], points[k][1]);
        star.closePath();
        return star;
    }

    private void setUpGraphicsCanvas(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (scale < 0.01 || scale > 0.99) {
            delta = -delta;
        }

        angle += 0.03;
        scale += delta;
        repaint();
    }
}
