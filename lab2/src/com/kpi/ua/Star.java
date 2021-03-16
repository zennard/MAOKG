package com.kpi.ua;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.GeneralPath;

public class Star extends JPanel implements ActionListener {
    private double[][] points;
    private Timer timer;
    private double scale = 1;
    private double delta = 0.01;
    private double rotationDelta = 1;
    private double tx = 0;
    private double ty = 6;
    double orbitX = 0;
    double orbitY = 0;
    double orbitRadius = 100;
    double orbitSpeed = Math.PI / 16;
    private int sceneWidth;
    private int sceneHeight;

    public Star(int sceneWidth, int sceneHeight) {
        this.setLayout(new GridLayout(1, 1));
        this.sceneWidth = sceneWidth;
        this.sceneHeight = 0;
        double widthCenter = 0;
        double heightCenter = 0;
        timer = new Timer(10, this);
        timer.start();
        points = new double[][]{
                {widthCenter - 100, heightCenter - 15}, {widthCenter - 25, heightCenter - 25},
                {widthCenter, heightCenter - 90}, {widthCenter + 25, heightCenter - 25},
                {widthCenter + 100, heightCenter - 15}, {widthCenter + 50, heightCenter + 25},
                {widthCenter + 60, heightCenter + 100}, {widthCenter, heightCenter + 50},
                {widthCenter - 60, heightCenter + 100}, {widthCenter - 50, heightCenter + 25},
                {widthCenter - 100, heightCenter - 15}
        };
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        GradientPaint gp = new GradientPaint(5, 25,
                new Color(255, 255, 0), 20, 2, new Color(0, 0, 255), true);
        g2d.setPaint(gp);
        g2d.clearRect(0, 0, sceneWidth + 1, sceneHeight + 1);
        setUpGraphicsCanvas(g2d);
        // Зсовуємо центр координат в центр вікна
        g2d.translate(sceneWidth / 2, sceneHeight / 2);

        // Перетворення для анімації руху.
        g2d.translate(tx, ty);

        // Створення зірки
        GeneralPath star = new GeneralPath();
        star.moveTo(points[0][0], points[0][1]);
        for (int k = 1; k < points.length; k++)
            star.lineTo(points[k][0], points[k][1]);
        star.closePath();

        // Перетворення для анімації масштабу
        g2d.scale(scale, 0.99);

        // Далі йдуть всі ті методи, що необхідні для власне малювання малюнку
        g2d.fill(star);
    }

    private void setUpGraphicsCanvas(Graphics2D g2d) {
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHints(rh);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Приклад анімації");
        frame.add(new Star(1200, 900));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 900);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
//        maxWidth = size.width - insets.left - insets.right - 1;
//        maxHeight = size.height - insets.top - insets.bottom - 1;
    }

    public void actionPerformed(ActionEvent e) {
        if (scale < 0.01 || scale > 0.99) {
            delta = -delta;
        }

        double radian = orbitSpeed * rotationDelta;
        tx = orbitX + orbitRadius * Math.cos(radian);
        ty = orbitY + orbitRadius * Math.sin(radian);
        rotationDelta += 1;
        scale += delta;

        repaint();
    }
}
