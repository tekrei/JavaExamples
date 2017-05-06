package j3d.panels;

import com.sun.j3d.utils.geometry.Cylinder;

import javax.media.j3d.TransformGroup;
import java.awt.*;

public class CylinderExample extends Java3DPanel {

    public CylinderExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        transformGroup.addChild(new Cylinder(0.7f, 0.4f, getColorAppearance(Color.ORANGE)));
    }
}
