import com.jhlabs.image.*;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.Vector;

public class ImageProcessing {

    private static Vector<BufferedImageOp> filters;
    private static Vector<BufferedImageOp> transformations;
    private static Vector<BufferedImageOp> morphs;

    public static Vector<BufferedImageOp> getFilters() {
        if (filters == null) {
            filters = new Vector<>();
            filters.add(new BlurFilter());
            filters.add(new EdgeFilter());
            filters.add(new InvertFilter());
            filters.add(new PosterizeFilter());
            filters.add(new SharpenFilter());
            filters.add(new GaussianFilter());
            filters.add(new ChromeFilter());
            filters.add(new CrystallizeFilter());
            filters.add(new MedianFilter());
            filters.add(new MaximumFilter());
            filters.add(new EqualizeFilter());
            filters.add(new OilFilter());
            filters.add(new LevelsFilter());
            filters.add(new ThresholdFilter(64));
            filters.add(new ThresholdFilter(128));
            filters.add(new ThresholdFilter(192));
        }
        return filters;
    }

    public static Vector<BufferedImageOp> getTransformations() {
        if (transformations == null) {
            transformations = new Vector<>();
            transformations.add(new RotateFilter(
                    (float) Math.PI / 6));
            transformations.add(new RotateFilter(
                    (float) Math.PI / 3));
            transformations.add(new RotateFilter(
                    (float) Math.PI / 2));
            transformations.add(
                    new RotateFilter((float) Math.PI));
            transformations.add(new ScaleFilter());
            transformations.add(new ScaleFilter(16, 16));
            transformations.add(new ScaleFilter(8, 16));
        }
        return transformations;
    }

    public static Vector<BufferedImageOp> getMorphs() {
        if (morphs == null) {
            morphs = new Vector<>();
            morphs.add(createShearFilter((float) Math.PI / 6, (float) Math.PI / 3));
            morphs.add(createShearFilter((float) Math.PI / 2, (float) Math.PI / 4));
        }
        return morphs;
    }

    private static BufferedImageOp createShearFilter(float xAngle, float yAngle) {
        ShearFilter sf = new ShearFilter();
        sf.setXAngle(xAngle);
        sf.setXAngle(yAngle);
        return sf;
    }

    public static BufferedImage process(BufferedImage mBufferedImage, BufferedImageOp operation) {
        return operation.filter(mBufferedImage, null);
    }
}
