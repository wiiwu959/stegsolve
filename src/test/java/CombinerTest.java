import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JLabelMatcher;
import org.assertj.swing.fixture.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CombinerTest {

    protected FrameFixture frame;

    @Before
    public final void setUp() throws IOException {
        File file1 = new File("src/test/testcase/combineImage1.jpg");
        BufferedImage image1 = (BufferedImage) ImageIO.read(file1);
        File file2 = new File("src/test/testcase/combineImage2.jpg");
        BufferedImage image2 = (BufferedImage) ImageIO.read(file2);
        frame = new FrameFixture(new Combiner(image1, image2));
        frame.show();
    }

    @Test
    public void testFrame(){
        frame.requireVisible();
        frame.requireEnabled();
    }

    @Test
    public void testBackButton(){
        JLabelFixture combinerText = frame.label(JLabelMatcher.withText("XOR"));
        JButtonFixture backBtn = frame.button(JButtonMatcher.withText("<"));
        backBtn.requireVisible();
        backBtn.requireEnabled();
        backBtn.click();
        Assert.assertEquals("Vertical Interlace", combinerText.text());
        backBtn.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));
        Assert.assertEquals("Horizontal Interlace", combinerText.text());
    }

    @Test
    public void testForwardButton(){
        JLabelFixture combinerText = frame.label(JLabelMatcher.withText("XOR"));
        JButtonFixture forwardBtn = frame.button(JButtonMatcher.withText(">"));
        forwardBtn.requireVisible();
        forwardBtn.requireEnabled();
        forwardBtn.click();
        Assert.assertEquals("OR", combinerText.text());
        forwardBtn.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));
        Assert.assertEquals("AND", combinerText.text());
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

        saveBtn.click();
        fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText("solved");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

        saveBtn.click();
        fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText(new String(new char[300]).replace('\0', '1'));
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @After
    public final void tearDown() throws InterruptedException {
        frame.cleanUp();
        this.frame = null;
    }
}