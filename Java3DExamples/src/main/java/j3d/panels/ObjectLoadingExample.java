package j3d.panels;

import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;
import java.awt.*;

public class ObjectLoadingExample extends Java3DPanel {

    public ObjectLoadingExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        transformGroup.addChild(getColorBackground(Color.CYAN));
        transformGroup.addChild(loadObject("/galleon.obj"));
        transformGroup.addChild(getAmbientLight(new Color(1.0f, 1.0f, 0.9f)));
        transformGroup.addChild(getDirectionalLight(new Color(1.0f, 1.0f, 0.9f), new Vector3f(1.0f, 1.0f, 1.0f)));
        transformGroup.addChild(getDirectionalLight(new Color(1.0f, 1.0f, 1.0f), new Vector3f(-1.0f, -1.0f, -1.0f)));
    }
}
