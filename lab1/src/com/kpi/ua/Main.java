package com.kpi.ua;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {
    private static final double SCENE_WIDTH = 1200;
    private static final double SCENE_HEIGHT = 900;
    private static final double SCENE_WIDTH_CENTER = SCENE_WIDTH / 2;
    private static final double SCENE_HEIGHT_CENTER = SCENE_HEIGHT / 2;
    private static final double BUTTERFLY_BODY_CENTER_X = SCENE_WIDTH_CENTER;
    private static final double BUTTERFLY_BODY_CENTER_Y = SCENE_HEIGHT_CENTER;
    private static final double BUTTERFLY_BODY_RADIUS_Y = 100.0;
    private static final double BUTTERFLY_BODY_RADIUS_X = 50.0;
    private static final Color BUTTERFLY_BODY_COLOR = Color.rgb(173,255,47);
    private static final double BUTTERFLY_WING_CENTER_X = BUTTERFLY_BODY_CENTER_X;
    private static final double BUTTERFLY_WING_CENTER_Y = BUTTERFLY_BODY_CENTER_Y;
    private static final double BUTTERFLY_WING_LENGTH = 150;
    private static final Color BUTTERFLY_WING_COLOR = Color.rgb(0,255,255);
    private static final double ANTENNAE_OFFSET_Y = 20;
    private static final double ANTENNAE_OFFSET_X = 10;
    private static final double BUTTERFLY_ANTENNAE_START_Y = BUTTERFLY_WING_CENTER_Y - BUTTERFLY_BODY_RADIUS_Y + ANTENNAE_OFFSET_Y;
    private static final double BUTTERFLY_ANTENNAE_LENGTH = 100;
    private static final Color BUTTERFLY_ANTENNAE_COLOR = Color.rgb(175,175,175);
    public static final String PROGRAM_TITLE = "Lab 1";
    private static final double COEFFICIENT_TO_MAKE_TRIANGLE_EQUILATERAL = Math.sqrt(0.75);
    private static final double ANTENNAE_DEGREE = 15;

    @Override
    public void start(Stage primaryStage) {
        Ellipse ellipse = getButterflyBody();
        Point[] wingCoordinates = {
                new Point(BUTTERFLY_WING_CENTER_X,
                        BUTTERFLY_WING_CENTER_Y),
                new Point(BUTTERFLY_WING_CENTER_X - (BUTTERFLY_WING_LENGTH / 2),
                        BUTTERFLY_WING_CENTER_Y - BUTTERFLY_WING_LENGTH * COEFFICIENT_TO_MAKE_TRIANGLE_EQUILATERAL),
                new Point(BUTTERFLY_WING_CENTER_X - BUTTERFLY_WING_LENGTH,
                        BUTTERFLY_WING_CENTER_Y)
        };
        Polygon leftTopWing = getWing(wingCoordinates, 10);
        Polygon leftBottomWing = getWing(wingCoordinates, 110);
        Polygon rightTopWing = getWing(wingCoordinates, 200);
        Polygon rightBottomWing = getWing(wingCoordinates, 280);
        Line leftAntennae = getButterflyAntennae(
                new Point(BUTTERFLY_BODY_CENTER_X - ANTENNAE_OFFSET_X, BUTTERFLY_ANTENNAE_START_Y),
                new Point(BUTTERFLY_BODY_CENTER_X - ANTENNAE_OFFSET_X,
                        BUTTERFLY_ANTENNAE_START_Y - BUTTERFLY_ANTENNAE_LENGTH),
                 - ANTENNAE_DEGREE
        );
        Line rightAntennae = getButterflyAntennae(
                new Point(BUTTERFLY_BODY_CENTER_X + ANTENNAE_OFFSET_X, BUTTERFLY_ANTENNAE_START_Y),
                new Point(BUTTERFLY_BODY_CENTER_X + ANTENNAE_OFFSET_X,
                        BUTTERFLY_ANTENNAE_START_Y - BUTTERFLY_ANTENNAE_LENGTH),
                ANTENNAE_DEGREE
        );

        Group root = new Group();
        root.getChildren().addAll(ellipse, leftTopWing,
                leftBottomWing, rightTopWing,
                rightBottomWing, leftAntennae,
                rightAntennae);

        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);

        primaryStage.setTitle(PROGRAM_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    private Line getButterflyAntennae(Point start, Point end, double  angle) {
        Line line = new Line();
        line.setStartX(start.getX());
        line.setStartY(start.getY());
        line.setEndX(end.getX());
        line.setEndY(end.getY());
        line.setStroke(BUTTERFLY_ANTENNAE_COLOR);
        rotateFigureAroundPoint(line, start, angle);
        return line;
    }

    private Polygon getWing(Point[] points, double rotateAngle) {
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(
                Arrays.stream(points)
                .flatMap(point -> Stream.of(point.getX(), point.getY()))
                .collect(Collectors.toList())
        );
        rotateFigureAroundPoint(polygon, points[0], rotateAngle);

        polygon.setFill(BUTTERFLY_WING_COLOR);
        return polygon;
    }

    private void rotateFigureAroundPoint(Node figure, Point point, double rotateAngle) {
        Rotate rotation = new Rotate();
        rotation.setPivotX(point.getX());
        rotation.setPivotY(point.getY());
        rotation.setAngle(rotateAngle);
        figure.getTransforms().add(rotation);
    }

    private Ellipse getButterflyBody() {
        Ellipse ellipse = new Ellipse();
        ellipse.setCenterX(BUTTERFLY_BODY_CENTER_X);
        ellipse.setCenterY(BUTTERFLY_BODY_CENTER_Y);
        ellipse.setRadiusX(BUTTERFLY_BODY_RADIUS_X);
        ellipse.setRadiusY(BUTTERFLY_BODY_RADIUS_Y);
        ellipse.setFill(BUTTERFLY_BODY_COLOR);
        return ellipse;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
