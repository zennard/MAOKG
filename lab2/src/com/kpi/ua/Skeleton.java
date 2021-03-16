package com.kpi.ua;

import javax.swing.*;
import java.awt.*;

public class Skeleton extends JPanel {
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawString("Привіт, Java 2D!", 50, 50);
    }

}
