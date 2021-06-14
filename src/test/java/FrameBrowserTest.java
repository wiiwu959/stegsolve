import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JScrollPaneFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// method coverage 77% (15/17)
// also because saveButtonActionPerformed is hard to handle
// and some try catch will never be trigger

public class FrameBrowserTest {
    protected FrameFixture frame;

    BufferedImage image = null;
    protected java.util.List<BufferedImage> transform = null;
    int num = 0;

    @Before
    public void setUp() throws IOException {

        File file = new File("src/test/testcase/nyan.gif");
        transform = new ArrayList<BufferedImage>();

        ImageInputStream input = ImageIO.createImageInputStream(file);
        ImageReader reader = ImageIO.getImageReaders(input).next();
        reader.setInput(input);
        BufferedImage bnext;
        while(true) {
            try {
                bnext = reader.read(num);
            } catch (IndexOutOfBoundsException e) {
                bnext = null;
            }
            if (bnext == null) {
                break;
            }
            transform.add(bnext);
            num++;
        }

        image = (BufferedImage) transform.get(0);
        frame = new FrameFixture(new FrameBrowser(image, file));
        frame.show();
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

        BufferedImage expect = transform.get(0);
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

        JScrollPaneFixture pane = frame.scrollPane();
        pane.requireVisible();
        pane.requireEnabled();

        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.get(1);

        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    @Test
    public void testBack() {
        JButtonFixture btn = frame.button(JButtonMatcher.withText("<"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();

        JScrollPaneFixture pane = frame.scrollPane();
        pane.requireVisible();
        pane.requireEnabled();

        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.get(11);

        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    @Test
    public void testKeyLeft() {
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));

        JScrollPaneFixture pane = frame.scrollPane();
        pane.requireVisible();
        pane.requireEnabled();

        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.get(10);

        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    @Test
    public void testKeyRight() {
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));

        JScrollPaneFixture pane = frame.scrollPane();
        pane.requireVisible();
        pane.requireEnabled();

        DPanel panel = (DPanel) pane.target().getViewport().getView();
        BufferedImage actual = new BufferedImage(panel.getWidth(), panel.getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D graph = actual.createGraphics();
        panel.paint(graph);

        BufferedImage expect = transform.get(2);

        Assert.assertEquals(expect.getWidth(), actual.getWidth());
        Assert.assertEquals(expect.getHeight(), actual.getHeight());

        for (int y = 0; y < expect.getHeight(); y++) {
            for (int x = 0; x < expect.getWidth(); x++) {
                Assert.assertEquals(expect.getRGB(x, y), actual.getRGB(x, y));
            }
        }
    }

    @Test
    public void testSaveButton() {
        JButtonFixture saveBtn = frame.button(JButtonMatcher.withText("Save"));
        saveBtn.requireVisible();
        saveBtn.requireEnabled();
        saveBtn.click();
        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

        File testTarget = new File("src/test/testcase/results/solved");
        if(testTarget.exists()) { testTarget.delete(); }

        saveBtn.click();
        fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText("solved");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

        testTarget = new File("src/test/testcase/results/solved");
        Assert.assertTrue(testTarget.exists());

        saveBtn.click();
        fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText(new String(new char[300]).replace('\0', '1'));
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @After
    public final void tearDown() {
        frame.cleanUp();
        frame = null;
        transform = null;
    }
}