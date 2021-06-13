import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JScrollPaneFixture;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// class coverage 88% (15/17)
// saveButtonActionPerformed hard to test

public class StereoTest  extends AssertJSwingTestCaseTemplate {
    protected FrameFixture frame;

    BufferedImage image = null;
    protected  StereoTransform transform = null;

    @Before
    public void setUp() throws IOException {
        File file = new File("src/test/testcase/minion.jpg");
        image = (BufferedImage) ImageIO.read(file);

        frame = new FrameFixture(new Stereo(image));
        frame.show();
        transform = new StereoTransform(image);
    }

    @Test
    public void testFrame() {
        frame.requireTitle("");
        frame.requireVisible();
        frame.requireEnabled();

        JScrollPaneFixture pane = frame.scrollPane();
        pane.requireVisible();
        pane.requireEnabled();

        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.getImage();
        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    @Test
    public void testForward() {
        JButtonFixture btn = frame.button(JButtonMatcher.withText(">"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();
        transform.forward();

        JScrollPaneFixture pane = frame.scrollPane();
        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.getImage();
        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }

    }

    @Test
    public void testBackward(){
        JButtonFixture btn = frame.button(JButtonMatcher.withText("<"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();
        transform.back();

        JScrollPaneFixture pane = frame.scrollPane();
        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.getImage();
        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }

    }

    @Test
    public void testKeyLeft(){
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));
        transform.back();
        transform.back();

        JScrollPaneFixture pane = frame.scrollPane();
        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.getImage();
        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    @Test
    public void testKeyRight(){
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));
        transform.forward();
        transform.forward();

        JScrollPaneFixture pane = frame.scrollPane();
        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.getImage();
        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    @After
    public final void tearDown() {
        frame.cleanUp();
        frame = null;
        transform = null;
    }

}