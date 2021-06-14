import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.*;

public class StegSolveTest {

    protected FrameFixture frame = null;

    @Before
    public final void setUp() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<StegSolve> stegSolveConstructor = StegSolve.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(stegSolveConstructor.getModifiers()));
        stegSolveConstructor.setAccessible(true);
        frame = new FrameFixture(stegSolveConstructor.newInstance());
        frame.show();
    }

    @Test
    public void testForward() {
        JButtonFixture btn = frame.button(JButtonMatcher.withText(">"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();

        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");
        btn.click();
    }

    @Test
    public void testBack() {
        JButtonFixture btn = frame.button(JButtonMatcher.withText("<"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();

        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");
        btn.click();
    }

    @Test
    public void testKeyLeft() {
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));

        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_LEFT));
    }

    @Test
    public void testKeyRight() {
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));

        ((StegSolve) frame.target()).loadImage("src/test/testcase/minion.jpg");
        frame.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_RIGHT));
    }

    @Test
    public void testKeyEnter(){
        JTextComponentFixture textField = frame.textBox();
        textField.setText("200");
        textField.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));
    }

    @After
    public final void tearDown(){
        frame.cleanUp();
        frame = null;
    }
}
