package j3d.panels;

import com.sun.j3d.utils.geometry.ColorCube;

import javax.media.j3d.TransformGroup;

public class InteractiveCubeExample extends Java3DPanel {

    public InteractiveCubeExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        transformGroup.addChild(new ColorCube(0.5));
    }
}
