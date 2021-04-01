package com.kpi.ua;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import java.awt.*;

public class Ball {
    private Ball() {
    }

    public static Sphere getSphere(float radius, String pictureFilePath, Color3f emissiveColor) {
        int primitiveFlags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Sphere(radius, primitiveFlags, getXMassBallsAppearance(pictureFilePath,
                emissiveColor));
    }

    public static Sphere getEllipsoid(float radius, String picture, Color3f emissiveColor) {
        int primitiveFlags = Primitive.GENERATE_NORMALS;
        return new Sphere(radius, primitiveFlags, getXMassBallsAppearance(picture,
                emissiveColor));
    }


    private static Appearance getXMassBallsAppearance(String pictureFilePath, Color3f emissive) {
        Appearance ap = new Appearance();
        Color3f ambient = new Color3f(0.2f, 0.15f, .15f);
        Color3f diffuse = new Color3f(1.2f, 1.15f, .15f);
        Color3f specular = new Color3f(0.0f, 0.0f, 0.0f);
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        if (!pictureFilePath.equals("")) {
            TextureLoader loader = new TextureLoader(pictureFilePath, "LUMINANCE", new
                    Container());

            Texture texture = loader.getTexture();
            texture.setBoundaryModeS(Texture.WRAP);
            texture.setBoundaryModeT(Texture.WRAP);
            texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
            TextureAttributes texAttr = new TextureAttributes();
            texAttr.setTextureMode(TextureAttributes.MODULATE);
            ap.setTexture(texture);
            ap.setTextureAttributes(texAttr);
        }
        return ap;
    }
}