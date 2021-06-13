import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CombineTransformTest {
    protected CombineTransform transform_1st_larger = null;
    protected CombineTransform transform_2nd_larger = null;

    @Before
    public void setUp() throws IOException {
        File file1 = new File("src/test/testcase/combineImage1.jpg");
        BufferedImage image1 = (BufferedImage) ImageIO.read(file1);
        File file2 = new File("src/test/testcase/combineImage2.jpg");
        BufferedImage image2 = (BufferedImage) ImageIO.read(file2);
        transform_1st_larger = new CombineTransform(image1, image2);
        transform_2nd_larger = new CombineTransform(image2, image1);
    }

    @Test
    public void testBack(){
        for (int i=0; i<13; i++) {
            BufferedImage originImage = transform_1st_larger.getImage();
            transform_1st_larger.back();
            Assert.assertNotSame(originImage, transform_1st_larger.getImage());
        }
        for (int i=0; i<13; i++) {
            BufferedImage originImage = transform_2nd_larger.getImage();
            transform_2nd_larger.back();
            Assert.assertNotSame(originImage, transform_2nd_larger.getImage());
        }
    }

    @Test
    public void testForward() {
        for (int i=0; i<13; i++) {
            BufferedImage originImage = transform_1st_larger.getImage();
            transform_1st_larger.forward();
            Assert.assertNotSame(originImage, transform_1st_larger.getImage());
        }
        for (int i=0; i<13; i++) {
            BufferedImage originImage = transform_2nd_larger.getImage();
            transform_2nd_larger.forward();
            Assert.assertNotSame(originImage, transform_2nd_larger.getImage());
        }
    }

    @Test
    public void testGetText() throws NoSuchFieldException, IllegalAccessException {
        Field transNum = CombineTransform.class.getDeclaredField("transNum");
        transNum.setAccessible(true);
        for (int i=0; i<14; i++){
            transNum.set(transform_1st_larger, i);
            String actual = transform_1st_larger.getText();
            switch(i){
                case 0:
                    Assert.assertEquals("XOR", actual);
                    continue;
                case 1:
                    Assert.assertEquals("OR", actual);
                    continue;
                case 2:
                    Assert.assertEquals("AND", actual);
                    continue;
                case 3:
                    Assert.assertEquals("ADD", actual);
                    continue;
                case 4:
                    Assert.assertEquals("ADD (R,G,B separate)", actual);
                    continue;
                case 5:
                    Assert.assertEquals("SUB", actual);
                    continue;
                case 6:
                    Assert.assertEquals("SUB (R,G,B separate)", actual);
                    continue;
                case 7:
                    Assert.assertEquals("MUL", actual);
                    continue;
                case 8:
                    Assert.assertEquals("MUL (R,G,B separate)", actual);
                    continue;
                case 9:
                    Assert.assertEquals("Lightest (R, G, B separate)", actual);
                    continue;
                case 10:
                    Assert.assertEquals("Darkest (R, G, B separate)", actual);
                    continue;
                case 11:
                    Assert.assertEquals("Horizontal Interlace", actual);
                    continue;
                case 12:
                    Assert.assertEquals("Vertical Interlace", actual);
                    continue;
                case 13:
                    Assert.assertEquals("???", actual);
                    continue;
            }
        }
    }

    @Test
    public void testComb_DefaultReturn() throws NoSuchFieldException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Field transNum = CombineTransform.class.getDeclaredField("transNum");
        transNum.setAccessible(true);
        transNum.set(transform_1st_larger, 13);
        Method comb = CombineTransform.class.getDeclaredMethod("comb", int.class, int.class);
        comb.setAccessible(true);
        Integer actual = (Integer) comb.invoke(transform_1st_larger, 1, 2);

        Assert.assertEquals((Integer) 0, actual);
    }

    @After
    public final void tearDown() {
        transform_1st_larger = null;
        transform_2nd_larger = null;
    }

}