package j3d.panels;

import com.sun.j3d.utils.geometry.Sphere;

import javax.media.j3d.TransformGroup;

public class TextureExample extends Java3DPanel {

    public TextureExample() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) throws Exception {
        transformGroup.addChild(new Sphere(0.75f, Sphere.GENERATE_TEXTURE_COORDS | Sphere.GENERATE_NORMALS, getTextureAppearance("/earth.jpg")));
    }
}
