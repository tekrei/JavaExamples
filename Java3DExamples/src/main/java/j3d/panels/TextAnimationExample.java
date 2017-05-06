package j3d.panels;

import javax.media.j3d.*;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;

public class TextAnimationExample extends Java3DPanel {

    public TextAnimationExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        transformGroup.setTransform(getTransform(new Vector3f(-0.8f, -0.3f, -0.3f), 0.3, -1, -1));
        TransformGroup transformGroup1 = new TransformGroup();
        transformGroup1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        RotationInterpolator rotator = new RotationInterpolator(new Alpha(-1, 4000), transformGroup1, new Transform3D(), 0.0f, (float) Math.PI * 2.0f);
        rotator.setSchedulingBounds(BOUNDS);
        transformGroup1.addChild(rotator);
        transformGroup1.addChild(new Shape3D(new Text3D(new Font3D(new Font(Font.MONOSPACED, Font.PLAIN, 2), new FontExtrusion()), "COMPUTER", new Point3f(0.0f, 0.0f, 0.0f))));
        transformGroup.addChild(transformGroup1);
    }
}