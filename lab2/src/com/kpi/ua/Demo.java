package com.kpi.ua;

import javax.swing.*;
import java.awt.*;

public class Demo {
    public static final String PROGRAM_TITLE = "Lab 2";
    public static final int FRAME_WIDTH = 1200;
    public static final int FRAME_HEIGHT = 900;
    private static final double BORDER_START_X = 350;
    private static final double BORDER_START_Y = 0;
    private static final int BORDER_WIDTH = 500;
    private static final int BORDER_HEIGHT = 420;
    private static int maxCanvasWidth;
    private static int maxCanvasHeight;

    public static void main(String[] args) {
        JFrame frame = new JFrame(PROGRAM_TITLE);
        setUpFrame(frame);
        initializeDimensions(frame);

        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        container.add(ShapeFactory.createButterflyShape(maxCanvasWidth, maxCanvasHeight));
        Point borderStart = new Point(BORDER_START_X, BORDER_START_Y);
        container.add(ShapeFactory.createBorderWithStar(borderStart, BORDER_WIDTH, BORDER_HEIGHT));

        frame.add(container);
        frame.setVisible(true);
    }

    private static void initializeDimensions(JFrame frame) {
        Dimension size = frame.getSize();
        Insets insets = frame.getInsets();
        maxCanvasWidth = size.width - insets.left - insets.right - 1;
        maxCanvasHeight = size.height - insets.top - insets.bottom - 1;
    }

    private static void setUpFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }
}
