import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// TODO: Can add more test ass Transform.

public class StereoTransformTest {

    protected  StereoTransform transform = null;
    BufferedImage image = null;

    @Before
    public void setUp() throws IOException {
        File file = new File("src/test/testcase/minion.jpg");
        image = (BufferedImage) ImageIO.read(file);

        transform = new StereoTransform(image);
    }

    @Test
    public void testBack(){
        String expect = "Offset: " + (image.getWidth() - 1);
        transform.back();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());

        expect = "Offset: " + (image.getWidth() - 2);
        transform.back();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());
    }

    @Test
    public void testForward(){
        String expect = "Offset: " + 1;
        transform.forward();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());

        expect = "Offset: " + 2;
        transform.forward();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());
    }

    @After
    public final void tearDown() {
        transform = null;
    }
}