package j3d.panels;

import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseTranslate;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.imageio.ImageIO;
import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;

/**
 * Created by T. E. Kalayci on 06-May-2017
 */
abstract class Java3DPanel extends JPanel {

    final static BoundingSphere BOUNDS = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

    Java3DPanel() {
        super();
        setLayout(new BorderLayout());
        Canvas3D canvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        add(canvas3D);
        SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
        simpleU.getViewingPlatform().setNominalViewingTransform();
        try {
            BranchGroup branchGroup = new BranchGroup();
            TransformGroup transformGroup = getInteractiveTransformGroup();
            branchGroup.addChild(transformGroup);
            buildScene(transformGroup);
            branchGroup.compile();
            simpleU.addBranchGraph(branchGroup);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setVisible(true);
    }

    private TransformGroup getInteractiveTransformGroup() {
        TransformGroup transformGroup = new TransformGroup();
        addInteraction(transformGroup);
        return transformGroup;
    }

    Appearance getTextureAppearance(String path) throws Exception {
        Appearance appearance = new Appearance();
        TextureLoader pattern = new TextureLoader(ImageIO.read(getClass().getResource(path)), "RGB", this);
        appearance.setTexture(pattern.getTexture());
        return appearance;
    }

    Appearance getDefaultAppearance() {
        return getColorAppearance(Color.BLUE);
    }

    Appearance getColorAppearance(Color color) {
        Appearance appearance = new Appearance();
        Color3f color3f = new Color3f(color);
        appearance.setMaterial(new Material(color3f, color3f, color3f, color3f, 100f));
        return appearance;
    }

    private void addMouseRotate(TransformGroup transformGroup) {
        MouseRotate mouseRotate = new MouseRotate();
        mouseRotate.setTransformGroup(transformGroup);
        transformGroup.addChild(mouseRotate);
        mouseRotate.setSchedulingBounds(BOUNDS);
    }

    AmbientLight getAmbientLight(Color color) {
        AmbientLight ambientLightNode = new AmbientLight(new Color3f(color));
        ambientLightNode.setInfluencingBounds(BOUNDS);
        return ambientLightNode;
    }

    DirectionalLight getDirectionalLight(Color color, Vector3f direction) {
        DirectionalLight directionalLight = new DirectionalLight(new Color3f(color), direction);
        directionalLight.setInfluencingBounds(BOUNDS);
        return directionalLight;
    }

    PointLight getPointLight(Color color, Point3f position) {
        PointLight pointLight = new PointLight();
        pointLight.setEnable(true);
        pointLight.setColor(new Color3f(color));
        pointLight.setPosition(position);
        pointLight.setInfluencingBounds(BOUNDS);
        return pointLight;
    }

    Transform3D getTransform(Vector3f translation, double scale, double rotX, double rotY) {
        Transform3D transform3D = new Transform3D();
        if (translation != null)
            transform3D.setTranslation(translation);
        if (scale >= 0)
            transform3D.setScale(scale);
        if (rotX > 0 || rotY > 0) {
            Transform3D t = new Transform3D();
            if (rotX > 0)
                t.rotX(rotX);
            if (rotY > 0)
                t.rotY(rotY);
            transform3D.mul(t);
        }
        return transform3D;
    }

    private void addMouseTranslate(TransformGroup transformGroup) {
        MouseTranslate mouseTranslate = new MouseTranslate();
        mouseTranslate.setTransformGroup(transformGroup);
        transformGroup.addChild(mouseTranslate);
        mouseTranslate.setSchedulingBounds(BOUNDS);
    }

    private void addMouseZoom(TransformGroup transformGroup) {
        MouseZoom mouseZoom = new MouseZoom();
        mouseZoom.setTransformGroup(transformGroup);
        transformGroup.addChild(mouseZoom);
        mouseZoom.setSchedulingBounds(BOUNDS);
    }

    private void addInteraction(TransformGroup transformGroup) {
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        addMouseRotate(transformGroup);
        addMouseTranslate(transformGroup);
        addMouseZoom(transformGroup);
    }

    BranchGroup loadObject(String path) {
        try {
            return new ObjectFile(ObjectFile.RESIZE).load(getClass().getResource(path)).getSceneGroup();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Background getColorBackground(Color color) {
        Background bgNode = new Background(new Color3f(color));
        bgNode.setApplicationBounds(BOUNDS);
        return bgNode;
    }

    abstract void buildScene(TransformGroup transformGroup) throws Exception;
}
