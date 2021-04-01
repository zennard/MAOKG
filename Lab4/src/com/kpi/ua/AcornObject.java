package com.kpi.ua;

import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AcornObject implements ActionListener {
    private TransformGroup treeTransformGroup;
    private Transform3D treeTransform3D = new Transform3D();
    private Timer timer;
    private float angle = 0;

    public static void main(String[] args) {
        new AcornObject();
    }

    public AcornObject() {
        timer = new Timer(50, this);
        timer.start();
        BranchGroup scene = createSceneGraph();
        SimpleUniverse u = new SimpleUniverse();
        u.getViewingPlatform().setNominalViewingTransform();
        u.addBranchGraph(scene);
    }

    public BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();
        treeTransformGroup = new TransformGroup();
        treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildObj();
        objRoot.addChild(treeTransformGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color,
                light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }

    private void buildObj() {
        createBall(0.2f, .0f, -0.05f, .0f, "", new Color3f(1.0f, 0.5f, 0.3f));
        TransformGroup cone1 = new TransformGroup();
        Transform3D transformTop = new Transform3D();
        com.sun.j3d.utils.geometry.Cone coneTop = Cone.getCone(-0.25f, 0.1f);
        Vector3f vectorTop = new Vector3f(.0f, -0.2f, .0f);
        transformTop.setTranslation(vectorTop);
        cone1.setTransform(transformTop);
        cone1.addChild(coneTop);

        treeTransformGroup.addChild(cone1);

        TransformGroup cone2 = new TransformGroup();
        Transform3D transformTop2 = new Transform3D();
        com.sun.j3d.utils.geometry.Cone coneTop2 = Cone.getCone(-0.20f, 0.19f);
        Vector3f vectorTop2 = new Vector3f(.0f, -0.2f, .0f);
        transformTop2.setTranslation(vectorTop2);
        cone2.setTransform(transformTop2);
        cone2.addChild(coneTop2);
        treeTransformGroup.addChild(cone2);

        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Cylinder cylinder = new Cylinder(0.01f, 0.09f);
        Vector3f vector = new Vector3f(.0f, .2f, .0f);
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(cylinder);
        treeTransformGroup.addChild(tg);

        createBall(0.18f, .0f, 0.03f, .0f, "./dub.jpg", new Color3f(0.5f, 0.0f, 0.0f));

    }

    private void createBall(float radius, float x, float y, float z, String picture,
                            Color3f emissive) {
        TransformGroup tg = new TransformGroup();
        Transform3D transform = new Transform3D();
        Sphere cone = Ball.getSphere(radius, picture, emissive);
        Vector3f vector = new Vector3f(x, y, z);
        transform.setTranslation(vector);
        tg.setTransform(transform);
        tg.addChild(cone);
        treeTransformGroup.addChild(tg);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        treeTransform3D.rotY(angle);
        treeTransformGroup.setTransform(treeTransform3D);
        angle += 0.05;
    }
}
