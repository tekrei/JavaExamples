package j3d.panels;

import com.sun.j3d.utils.geometry.Cone;

import javax.media.j3d.TransformGroup;

public class ConeExample extends Java3DPanel {
    public ConeExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        transformGroup.addChild(new Cone(0.5f, 0.5f, getDefaultAppearance()));
    }
}
