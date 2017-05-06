package j3d.panels;

import com.sun.j3d.utils.geometry.Box;

import javax.media.j3d.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class ColoredBoxExample extends Java3DPanel {

    public ColoredBoxExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        Background background = new Background(new Color3f(.905f, .905f, 0.95f));
        background.setApplicationBounds(BOUNDS);
        transformGroup.addChild(background);

        Appearance appearance = new Appearance();
        ColoringAttributes ca = new ColoringAttributes();
        ca.setColor(1.0f, 0.0f, 1.0f);
        ca.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
        appearance.setColoringAttributes(ca);
        transformGroup.addChild(new Box(0.6f, 0.5f, 0.4f, appearance));

        Transform3D transform3D = new Transform3D();
        transform3D.setTranslation(new Vector3f(0.3f, 0.3f, 0.3f));
        transform3D.setScale(0.75);
        Transform3D t = new Transform3D();
        t.rotY(Math.PI / 4.0d);
        transform3D.mul(t);
        transformGroup.setTransform(transform3D);
    }
}
