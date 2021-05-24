import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.Map;

public class Ladybug {
	private TransformUtility body;

	private TransformUtility leftLeg1;
	private TransformUtility leftLeg2;
	private TransformUtility leftLeg3;

	private TransformUtility rightLeg1;
	private TransformUtility rightLeg2;
	private TransformUtility rightLeg3;
	private TransformUtility ant1;
	private TransformUtility ant2;
	private TransformUtility mainMove1;
	private TransformUtility mainMove2;
	private TransformUtility mainModel1;
	private TransformUtility mainModel2;

	public Ladybug(Canvas canvas) throws FileNotFoundException {
		TransformUtility[] transfoms = loadObject("ladybug", "leg6", "leg5", "leg4", "leg3", "leg2", "leg1", "antenna2", "antenna");

		body = transfoms[0];
		leftLeg1 = transfoms[1];
		leftLeg2 = transfoms[2];
		leftLeg3 = transfoms[3];
		rightLeg1 = transfoms[4];
		rightLeg2 = transfoms[5];
		rightLeg3 = transfoms[6];
		ant1 = transfoms[7];
		ant2 = transfoms[8];
		Material material = new Material();
		material.setAmbientColor (new Color3f(1, 1, 1));
		material.setDiffuseColor (new Color3f(1f, 1f, 1f));
		material.setSpecularColor(new Color3f(0.1f, 0.1f, 0.1f));
		material.setShininess(1f);
		material.setLightingEnable(true);

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.COMBINE);

		TextureLoader textureLoader = new TextureLoader("ground.jpg", "RGB", canvas);
		Appearance ap = new Appearance();
		ap.setTexCoordGeneration(new TexCoordGeneration(
				TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_2));
		ap.setMaterial(material);
		ap.setTextureAttributes(texAttr);
		ap.setTexture(textureLoader.getTexture());

		TransformUtility ground = new TransformUtility(new Box(1000, 1000, 0.1f, ap));
		ground.translate(0, 0, -0.1);

		mainMove1 = new TransformUtility(body.asNode(), leftLeg1.asNode(), leftLeg2.asNode(), leftLeg3.asNode(),
				rightLeg1.asNode(), rightLeg2.asNode(), rightLeg3.asNode(), ant1.asNode(), ant2.asNode());
		mainMove2 = new TransformUtility(mainMove1.asNode());
		mainModel1 = new TransformUtility(mainMove2.asNode(), ground.asNode());
		mainModel2 = new TransformUtility(mainModel1.asNode());
		mainMove1.rotate(Math.PI/2.1,0,0);
		rotateModel(-Math.PI/1.2, Math.PI, 0);
	}

	private static TransformUtility[] loadObject(String... groupNames) throws FileNotFoundException {
		Scene scene = new ObjectFile(ObjectFile.RESIZE/2).load("ladybug.obj");
		BranchGroup root = scene.getSceneGroup();

		Map<String, Shape3D> nameMap = scene.getNamedObjects();

		root.removeAllChildren();

		TransformUtility[] ret = new TransformUtility[groupNames.length];

		for (int i = 0; i < groupNames.length; ++i) {
			ret[i] = new TransformUtility(nameMap.get(groupNames[i]));
		}

		return ret;
	}

	double legRotateDX = 0.02, bodyRotateDy = 0.005;

	public void update(boolean isUp) {
		leftLeg1.rotate(legRotateDX, legRotateDX, 0);
		rightLeg2.rotate(legRotateDX, legRotateDX, 0);
		leftLeg3.rotate(legRotateDX, legRotateDX, 0);
		rightLeg1.rotate(-legRotateDX, -legRotateDX, 0);
		leftLeg2.rotate(-legRotateDX, -legRotateDX, 0);
		rightLeg3.rotate(-legRotateDX, -legRotateDX, 0);
		if(Math.abs(leftLeg1.rotX) > 0.1) {
			legRotateDX *= -1;
		}

		body.rotate(0, bodyRotateDy, 0);

		if(Math.abs(body.rotY) > 0.05) {
			bodyRotateDy *= -1;
		}

		double speed = 0.01;
		if(isUp){
			mainMove2.translate(speed * Math.sin(mainMove1.rotZ), -speed * Math.cos(mainMove1.rotZ), 0);
		} else {
			mainMove2.translate(-speed * Math.sin(mainMove1.rotZ), speed * Math.cos(mainMove1.rotZ), 0);
		}

	}


	public void rotateModel(double rotX, double rotY, double rotZ) {
		mainModel1.rotate(rotX, 0, 0);
		mainModel2.rotate(0, rotY, 0);
	}

	public Node asNode() {
		return mainModel2.asNode();
	}
}
