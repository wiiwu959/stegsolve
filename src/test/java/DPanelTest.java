import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class DPanelTest {
    private DPanel dPanel = null;

    @Before
    public final void setUp(){
        dPanel = new DPanel();
    }

    @Test
    public void testSetImage() throws IOException {
        File file = new File("src/test/testcase/minion.jpg");
        BufferedImage image = (BufferedImage) ImageIO.read(file);
        dPanel.setImage(image);
        Assert.assertEquals(image.getWidth(), dPanel.getPreferredSize().width);
        Assert.assertEquals(image.getHeight(), dPanel.getPreferredSize().height);
    }

    @After
    public final void tearDown(){
        dPanel = null;
    }
}