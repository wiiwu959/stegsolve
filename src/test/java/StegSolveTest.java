import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JFileChooserFixture;
import org.assertj.swing.fixture.JMenuItemFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.*;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.*;

public class StegSolveTest {

    protected FrameFixture frame = null;
    protected StegSolve stegSolve = null;

    @Before
    public final void setUp() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<StegSolve> stegSolveConstructor = StegSolve.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(stegSolveConstructor.getModifiers()));
        stegSolveConstructor.setAccessible(true);
        stegSolve = stegSolveConstructor.newInstance();
        frame = new FrameFixture(stegSolve);
        frame.show();
    }

    @Test
    public void testMain(){
        stegSolve.main(null);
    }

    @Test
    public void testOpen(){
        JMenuItemFixture openItem = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Open".equals(item.getText());
            }
        });
        openItem.click();

        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/"));
        fileChooserFixture.fileNameTextBox().setText("minion.jpg");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @Test
    public void testOpen_Fail(){
        JMenuItemFixture openItem = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Open".equals(item.getText());
            }
        });
        openItem.click();

        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/"));
        fileChooserFixture.fileNameTextBox().setText("123.jpg");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @Test
    public void testFileFormat(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "File Format".equals(item.getText());
            }
        });
        item.click();
    }

    @Test
    public void testDataExtract(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Data Extract".equals(item.getText());
            }
        });
        item.click();
    }

    @Test
    public void testStereogramSolver(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Stereogram Solver".equals(item.getText());
            }
        });
        item.click();
    }

    @Test
    public void testFrameBrowser(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Frame Browser".equals(item.getText());
            }
        });
        item.click();
    }

    @Test
    public void testImageCombiner(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/combineImage1.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Image Combiner".equals(item.getText());
            }
        });
        item.click();

        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/"));
        fileChooserFixture.fileNameTextBox().setText("combineImage2.jpg");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @Test
    public void testImageCombinerFail(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/combineImage1.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Image Combiner".equals(item.getText());
            }
        });
        item.click();

        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText("combineImage2.jpg");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @Test
    public void testSave(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Save As".equals(item.getText());
            }
        });
        item.click();

        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

        File testTarget = new File("src/test/testcase/results/save.jpg");
        if(testTarget.exists()) { testTarget.delete(); }

        item.click();
        fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText("save.jpg");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

        testTarget = new File("src/test/testcase/results/solved");
        Assert.assertTrue(testTarget.exists());

        item.click();
        fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText(new String(new char[300]).replace('\0', '1'));
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @Test
    public void testExit(){
        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");

        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "Exit".equals(item.getText());
            }
        });
        item.click();
        frame.requireNotVisible();
    }



    @Test
    public void testAbout() throws IOException {
        JMenuItemFixture item = frame.menuItem(new GenericTypeMatcher<JMenuItem>(JMenuItem.class) {
            @Override
            protected boolean isMatching(JMenuItem item) {
                return "About".equals(item.getText());
            }
        });
        item.click();
    }

    @Test
    public void testForward() {
        JButtonFixture btn = frame.button(JButtonMatcher.withText(">"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();

        stegSolve.loadImage("src/test/testcase/minion.jpg");
        btn.click();
    }

    @Test
    public void testBack() {
        JButtonFixture btn = frame.button(JButtonMatcher.withText("<"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();

        stegSolve.loadImage("src/test/testcase/minion.jpg");
        btn.click();
    }

    @Test
    public void testKeyLeft() {
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));

        stegSolve.loadImage("src/test/testcase/minion.jpg");
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));
    }

    @Test
    public void testKeyRight() {
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));

        stegSolve.loadImage("src/test/testcase/minion.jpg");
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));
    }

    @Test
    public void testKeyEnter(){
        JTextComponentFixture textField = frame.textBox();
        textField.setText("200");
        textField.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @Test
    public void testKeyTyped() throws AWTException {
        stegSolve.loadImage("src/test/testcase/minion.jpg");

        Robot robot = new Robot();

        frame.click();
        robot.mouseWheel(50);

        frame.pressKey(KeyEvent.VK_SHIFT);
        robot.mouseWheel(50);
        frame.releaseKey(KeyEvent.VK_SHIFT);

        frame.pressKey(KeyEvent.VK_SHIFT);
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_A));
        frame.releaseKey(KeyEvent.VK_SHIFT);
    }

    @After
    public final void tearDown(){
        frame.cleanUp();
        frame = null;
        stegSolve = null;
    }
}
