package j3d.panels;

import com.sun.j3d.utils.geometry.Box;

import javax.media.j3d.TransformGroup;

public class TexturedBox extends Java3DPanel {
    public TexturedBox() {
        super();
    }

    @Override
    void buildScene(TransformGroup transformGroup) throws Exception {
        transformGroup.addChild(new Box(0.7f, 0.5f, 0.5f, Box.GENERATE_TEXTURE_COORDS | Box.GENERATE_NORMALS, getTextureAppearance("/sky.jpg")));
    }
}
