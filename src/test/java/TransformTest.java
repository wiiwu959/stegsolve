import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.awt.image.*;
import java.util.Random;
import java.awt.color.ColorSpace;

// line coverage : 98% some default case cannot touched.
// TODO: Assert picture (not sure how to do)

public class TransformTest {
    protected  Transform transform = null;
    protected BufferedImage image = null;

    protected static BufferedImage convert2Index(BufferedImage src){
        BufferedImage img = new BufferedImage(src.getWidth(), src.getHeight(),
                BufferedImage.TYPE_BYTE_INDEXED);
        Graphics2D g2d = img.createGraphics();
        g2d.drawImage(src, 0, 0, null);
        g2d.dispose();
        return img;
    }

    protected static BufferedImage convert2Package(BufferedImage src){

        ColorModel colorModel = new DirectColorModel(ColorSpace.getInstance
                (ColorSpace.CS_LINEAR_RGB), 32,
                0x00FF0000, 0x0000FF00,
                0x000000FF, 0xFF000000, true,
                DataBuffer.TYPE_INT);
        WritableRaster wr = colorModel.createCompatibleWritableRaster(src.getWidth(), src.getHeight());
        BufferedImage img = new BufferedImage(colorModel, wr, true, null);

        return img;
    }


    @Before
    public void setUp() throws IOException {
        File file = new File("src/test/testcase/minion.jpg");
        image = (BufferedImage) ImageIO.read(file);

        transform = new Transform(image);
    }

    @Test
    public void testRandomRandommap(){
        BufferedImage img;
        Transform tran;
        String expect;

        // Test ComponentColorModel
        img = image;
        tran = transform;
        expect = "Random colour map 1"; // transNum = 38
        for (int i = 0; i <= 37; i++){
            tran.forward();
        }
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());

        expect = "Random colour map 2"; // transNum = 39
        tran.forward();
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());

        expect = "Random colour map 3"; // transNum = 40
        tran.forward();
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());


        // Test IndexColorModel
        img = convert2Index(image);
        tran = new Transform(img);
        expect = "Random colour map 1"; // transNum = 38
        for (int i = 0; i <= 37; i++){
            tran.forward();
        }
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());

        expect = "Random colour map 2"; // transNum = 39
        tran.forward();
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());

        expect = "Random colour map 3"; // transNum = 40
        tran.forward();
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());

        // Test PackedColorModel
        img = convert2Package(image);
        tran = new Transform(img);
        expect = "Random colour map 1"; // transNum = 38
        for (int i = 0; i <= 37; i++){
            tran.forward();
        }
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());

        expect = "Random colour map 2"; // transNum = 39
        tran.forward();
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());

        expect = "Random colour map 3"; // transNum = 40
        tran.forward();
        Assert.assertEquals(expect, tran.getText());
        Assert.assertNotSame(img, tran.getImage());
    }

    @Test
    public void testTransfrombit(){
        String expect;
        int testTransform = 31;
        transform.forward();
        for (int i = 2; i <= 9; i++){ // transNum = 2 - 9
            transform.forward();
            expect = "Alpha plane " + (9 - i);
            Assert.assertEquals(expect, transform.getText());
            Assert.assertNotSame(image, transform.getImage());
            for (int j = 0; j < 100;j++) {
                Random rand = new Random();
                int w = rand.nextInt(image.getWidth());
                int h = rand.nextInt(image.getHeight());
                int expectPixel = image.getRGB(w, h);

                if(((expectPixel >>> testTransform) & 1) > 0) { expectPixel = 0xffffff; }
                else { expectPixel = 0;}

                Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
            }
            testTransform--;
        }

        for (int i = 10; i <= 17; i++){ // transNum = 10 - 17
            transform.forward();
            expect = "Red plane " + (17 - i);
            Assert.assertEquals(expect, transform.getText());
            Assert.assertNotSame(image, transform.getImage());
            for (int j = 0; j < 100;j++) {
                Random rand = new Random();
                int w = rand.nextInt(image.getWidth());
                int h = rand.nextInt(image.getHeight());
                int expectPixel = image.getRGB(w, h);

                if(((expectPixel >>> testTransform) & 1) > 0) { expectPixel = 0xffffff; }
                else { expectPixel = 0;}

                Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
            }
            testTransform--;
        }

        for (int i = 18; i <= 25; i++){ // transNum = 18 - 25
            transform.forward();
            expect = "Green plane " + (25 - i);
            Assert.assertEquals(expect, transform.getText());
            Assert.assertNotSame(image, transform.getImage());
            for (int j = 0; j < 100;j++) {
                Random rand = new Random();
                int w = rand.nextInt(image.getWidth());
                int h = rand.nextInt(image.getHeight());
                int expectPixel = image.getRGB(w, h);

                if(((expectPixel >>> testTransform) & 1) > 0) { expectPixel = 0xffffff; }
                else { expectPixel = 0;}

                Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
            }
            testTransform--;
        }

        for (int i = 26; i <= 33; i++){ // transNum = 26 - 33
            transform.forward();
            expect = "Blue plane " + (33 - i);
            Assert.assertEquals(expect, transform.getText());
            Assert.assertNotSame(image, transform.getImage());
            for (int j = 0; j < 100;j++) {
                Random rand = new Random();
                int w = rand.nextInt(image.getWidth());
                int h = rand.nextInt(image.getHeight());
                int expectPixel = image.getRGB(w, h);

                if(((expectPixel >>> testTransform) & 1) > 0) { expectPixel = 0xffffff; }
                else { expectPixel = 0;}

                Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
            }
            testTransform--;
        }

    }

    @Test
    public void testTransmask(){
        String expect = "Full alpha"; // transNum = 34
        for (int i = 0; i <= 33; i++){
            transform.forward();
         }
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());
        for (int i = 0; i < 100;i++) {
            Random rand = new Random();
            int w = rand.nextInt(image.getWidth());
            int h = rand.nextInt(image.getHeight());
            int expectPixel = image.getRGB(w, h) & 0xff000000;
            if(expectPixel > 0xffffff || expectPixel < 0) { expectPixel = expectPixel >>> 8; }
            Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
        }

        expect = "Full red"; // transNum = 35
        transform.forward();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());
        for (int i = 0; i < 100;i++) {
            Random rand = new Random();
            int w = rand.nextInt(image.getWidth());
            int h = rand.nextInt(image.getHeight());
            int expectPixel = image.getRGB(w, h) & 0x00ff0000;
            Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
        }

        expect = "Full green"; // transNum = 36
        transform.forward();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());
        for (int i = 0; i < 100;i++) {
            Random rand = new Random();
            int w = rand.nextInt(image.getWidth());
            int h = rand.nextInt(image.getHeight());
            int expectPixel = image.getRGB(w, h) & 0x0000ff00;
            Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
        }

        expect = "Full blue"; // transNum = 37
        transform.forward();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());
        for (int i = 0; i < 100;i++) {
            Random rand = new Random();
            int w = rand.nextInt(image.getWidth());
            int h = rand.nextInt(image.getHeight());
            int expectPixel = image.getRGB(w, h) & 0x000000ff;
            Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h) & 0xffffff);
        }
    }

    @Test
    public void testInversion(){
        String expect = "Colour Inversion (Xor)";
        transform.forward(); // transNum = 1
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());

        for (int i = 0; i < 100;i++) {
            Random rand = new Random();
            int w = rand.nextInt(image.getWidth());
            int h = rand.nextInt(image.getHeight());
            int expectPixel = image.getRGB(w, h) ^ 0xffffff;
            Assert.assertEquals(expectPixel, transform.getImage().getRGB(w, h));
        }
    }

    @Test
    public void testGraybits(){
        String expect = "Gray bits";
        transform.back(); // transNum = 41
        Assert.assertEquals(expect, transform.getText());
        Assert.assertNotSame(image, transform.getImage());

        Random rand = new Random();

        for (int i = 0; i < 100;i++) {
            int w = rand.nextInt(image.getWidth());
            int h = rand.nextInt(image.getHeight());

            int expectPixel = image.getRGB(w, h);
            if ((expectPixel & 0xff) == ((expectPixel & 0xff00) >> 8)
                    && (expectPixel & 0xff) == ((expectPixel & 0xff0000) >> 16)) {
                expectPixel = 0xffffff;
            } else {
                expectPixel = 0;
            }
            int actual = transform.getImage().getRGB(w, h) & 0xffffff;
            Assert.assertEquals(expectPixel, actual);
        }
    }

    @Test
    public void testOrigine(){
        String expect = "Normal Image";
        transform.forward();
        transform.back();
        Assert.assertEquals(expect, transform.getText());
        Assert.assertSame(image, transform.getImage());
    }

    @After
    public final void tearDown() {
        transform = null;
    }

}