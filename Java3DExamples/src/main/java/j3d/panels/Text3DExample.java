package j3d.panels;

import javax.media.j3d.*;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;

public class Text3DExample extends Java3DPanel {

    public Text3DExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) throws Exception {

        Font3D f3d = new Font3D(new Font(Font.SANS_SERIF, Font.PLAIN, 2), new FontExtrusion());
        transformGroup.addChild(new Shape3D(new Text3D(f3d, "COMPUTER", new Point3f(0.0f, 0.0f, 0.0f))));
        transformGroup.setTransform(getTransform(new Vector3f(-0.8f, -0.3f, -0.3f), 0.3, -1, Math.PI / 6.0d));
    }
}