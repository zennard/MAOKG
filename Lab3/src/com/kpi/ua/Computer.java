package com.kpi.ua;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Computer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);

        Polygon p0 = new Polygon(340,230, 435, 230, 387.5, 350);
        p0.setFill(Color.BLACK);
        root.getChildren().add(p0);

        Polygon p01 = new Polygon(341,231, 434, 231, 387.5, 349);
        p01.setFill(Color.WHITE);
        root.getChildren().add(p01);

        Rectangle r0 = new Rectangle(340, 292, 95,20);
        r0.setFill(Color.BLACK);
        root.getChildren().add(r0);

        Rectangle r01 = new Rectangle(341, 293, 93,18);
        r01.setFill(Color.WHITE);
        root.getChildren().add(r01);

        Polygon p1 = new Polygon(300,180, 445, 160, 460, 270, 315, 290);
        p1.setFill(Color.BLACK);
        root.getChildren().add(p1);

        Polygon p2 = new Polygon(301,181, 444, 161, 459, 269, 316, 289);
        p2.setFill(Color.WHITE);
        root.getChildren().add(p2);

        Polygon p3 = new Polygon(315,195, 430, 175, 445, 255, 330, 275);
        p3.setFill(Color.CYAN);
        root.getChildren().add(p3);

        Rectangle r1 = new Rectangle(300, 300, 175,52);
        r1.setFill(Color.BLACK);
        root.getChildren().add(r1);

        Rectangle r2 = new Rectangle(301, 301, 173,50);
        r2.setFill(Color.CYAN);
        root.getChildren().add(r2);

        Rectangle r11 = new Rectangle(315, 308, 25,4);
        r11.setFill(Color.WHITE);
        root.getChildren().add(r11);

        Rectangle r12 = new Rectangle(400, 312, 25,4);
        r12.setFill(Color.WHITE);
        root.getChildren().add(r12);
        Rectangle r13 = new Rectangle(430, 312, 25,4);
        r13.setFill(Color.WHITE);
        root.getChildren().add(r13);

        final ImageView imv = new ImageView();
        final Image image2 = new Image(new FileInputStream("face.png"));
        imv.setImage(image2);
        imv.setX(220);
        imv.setY(130);
        double size = 220;
        imv.setFitHeight(size);
        imv.setFitWidth(size*1.5);
        root.getChildren().add(imv);

        // Animation
        int cycleCount = 2;
        int time = 3000;

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(360f);
        rotateTransition.setCycleCount(cycleCount);
        rotateTransition.setAutoReverse(true);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(150);
        translateTransition.setToX(500);
        translateTransition.setCycleCount(cycleCount + 1);
        translateTransition.setAutoReverse(true);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(time), root);
        translateTransition2.setFromX(50);
        translateTransition2.setToX(500);
        translateTransition2.setCycleCount(cycleCount + 1);
        translateTransition2.setAutoReverse(true);

        ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(time), root);
        scaleTransition2.setToX(0.1);
        scaleTransition2.setToY(0.1);
        scaleTransition2.setCycleCount(cycleCount);
        scaleTransition2.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                rotateTransition,
                scaleTransition,
                scaleTransition2,
                translateTransition
        );
        parallelTransition.setCycleCount(Timeline.INDEFINITE);
        parallelTransition.play();


        primaryStage.setResizable(false);
        primaryStage.setTitle("Lab 3");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
