package j3d.panels;

import com.sun.j3d.utils.geometry.Sphere;

import javax.media.j3d.TransformGroup;
import java.awt.*;

public class SphereExample extends Java3DPanel {

    public SphereExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        transformGroup.addChild(new Sphere(0.7f, getColorAppearance(Color.CYAN)));
    }
}
