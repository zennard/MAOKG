package com.kpi.ua;

import javax.swing.*;

public class ShapeFactory {
    public static JPanel createButterflyShape(double sceneWidth, double sceneHeight) {
        return new Butterfly(sceneWidth, sceneHeight);
    }

    public static JPanel createBorderWithStar(Point startPoint, int width, int height) {
        return new StarAnimation(startPoint, width, height);
    }
}
