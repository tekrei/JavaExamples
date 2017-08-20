package j3d.panels;

import com.sun.j3d.utils.geometry.Box;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.TransparencyAttributes;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;
import java.awt.*;

public class LightningExample extends Java3DPanel {

    public LightningExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) {
        transformGroup.addChild(getColorBackground(new Color(.905f, .905f, 0.95f)));
        Appearance appearance = new Appearance();
        Material mat = new Material();
        mat.setAmbientColor(0.5f, 0.5f, 0.5f);
        mat.setDiffuseColor(1.0f, 1.0f, 1.0f);
        mat.setEmissiveColor(0.0f, 0.0f, 0.0f);
        mat.setSpecularColor(1.0f, 1.0f, 1.0f);
        mat.setShininess(80.0f);
        appearance.setMaterial(mat);

        TransparencyAttributes ta = new TransparencyAttributes();
        ta.setTransparency(0.5f);
        ta.setTransparencyMode(TransparencyAttributes.BLENDED);
        appearance.setTransparencyAttributes(ta);

        transformGroup.addChild(new Box(0.6f, 0.5f, 0.4f, appearance));

        transformGroup.addChild(getPointLight(new Color(1.0f, 1.0f, 1.0f), new Point3f(2.0f, 2.0f, 2.0f)));
        transformGroup.addChild(getAmbientLight(new Color(0.1f, 0.1f, 0.1f)));
        transformGroup.setTransform(getTransform(new Vector3f(0.3f, 0.3f, 0.3f), 0.75, -1, Math.PI / 4.0d));
    }
}
