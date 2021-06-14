import org.assertj.swing.core.GenericTypeMatcher;
import org.assertj.swing.core.KeyPressInfo;
import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.fixture.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// line coverage 100%

public class ExtractTest {
    protected FrameFixture frame;

    BufferedImage image = null;
    protected  Extract extract = null;

    @Before
    public void setUp() throws IOException {
        File file = new File("src/test/testcase/minion.jpg");
        image = (BufferedImage) ImageIO.read(file);

        frame = new FrameFixture(new Extract(image));
        frame.show();
        extract = new Extract(image);
    }

    public void clickAll(){
        JPanelFixture alphaPanel = frame.panel("alphaBitPanel");
        alphaPanel.requireVisible();

        JCheckBoxFixture aAll = alphaPanel.checkBox(new GenericTypeMatcher<JCheckBox>(JCheckBox.class) {
            @Override
            protected boolean isMatching(JCheckBox checkBox) {
                return "all".equals(checkBox.getText());
            }
        });
        aAll.click();

        JPanelFixture redPanel = frame.panel("redBitPanel");
        redPanel.requireVisible();

        JCheckBoxFixture rAll = redPanel.checkBox(new GenericTypeMatcher<JCheckBox>(JCheckBox.class) {
            @Override
            protected boolean isMatching(JCheckBox checkBox) {
                return "all".equals(checkBox.getText());
            }
        });
        rAll.click();

        JPanelFixture bluePanel = frame.panel("blueBitPanel");
        bluePanel.requireVisible();

        JCheckBoxFixture bAll = bluePanel.checkBox(new GenericTypeMatcher<JCheckBox>(JCheckBox.class) {
            @Override
            protected boolean isMatching(JCheckBox checkBox) {
                return "all".equals(checkBox.getText());
            }
        });
        bAll.click();

        JPanelFixture greenPanel = frame.panel("greenBitPanel");
        greenPanel.requireVisible();

        JCheckBoxFixture gAll = greenPanel.checkBox(new GenericTypeMatcher<JCheckBox>(JCheckBox.class) {
            @Override
            protected boolean isMatching(JCheckBox checkBox) {
                return "all".equals(checkBox.getText());
            }
        });
        gAll.click();
    }

    public void clickBitPlane(){
        JPanelFixture bitPlaneOrderPanel = frame.panel("BitPlaneOrder");
        bitPlaneOrderPanel.requireVisible();
        JRadioButtonFixture bitBtn = bitPlaneOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "RGB".equals(radio.getText());
            }
        });
        bitBtn.click();

        JButtonFixture btn = frame.button(JButtonMatcher.withText("Preview"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();

        bitBtn = bitPlaneOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "GRB".equals(radio.getText());
            }
        });
        bitBtn.click();
        btn.click();

        bitBtn = bitPlaneOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "RBG".equals(radio.getText());
            }
        });
        bitBtn.click();
        btn.click();

        bitBtn = bitPlaneOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "BRG".equals(radio.getText());
            }
        });
        bitBtn.click();
        btn.click();

        bitBtn = bitPlaneOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "GBR".equals(radio.getText());
            }
        });
        bitBtn.click();
        btn.click();

        bitBtn = bitPlaneOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "BGR".equals(radio.getText());
            }
        });
        bitBtn.click();
        btn.click();
    }

    @Test
    public void testAll_Row_MSB() {
        clickAll();

        JPanelFixture extractByPanel = frame.panel("extractByPanel");
        JRadioButtonFixture byBtn = extractByPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "Row".equals(radio.getText());
            }
        });
        byBtn.click();


        JPanelFixture bitOrderPanel = frame.panel("bitOrderPanel");
        JRadioButtonFixture orderBtn = bitOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "MSB First".equals(radio.getText());
            }
        });
        orderBtn.click();
        clickBitPlane();
    }

    @Test
    public void testAll_Row_LSB() {
        clickAll();

        JPanelFixture extractByPanel = frame.panel("extractByPanel");
        JRadioButtonFixture byBtn = extractByPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "Row".equals(radio.getText());
            }
        });
        byBtn.click();


        JPanelFixture bitOrderPanel = frame.panel("bitOrderPanel");
        JRadioButtonFixture orderBtn = bitOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "LSB First".equals(radio.getText());
            }
        });
        orderBtn.click();
        clickBitPlane();
    }

    @Test
    public void testAll_Column_MSB() {
        clickAll();

        JPanelFixture extractByPanel = frame.panel("extractByPanel");
        JRadioButtonFixture byBtn = extractByPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "Column".equals(radio.getText());
            }
        });
        byBtn.click();


        JPanelFixture bitOrderPanel = frame.panel("bitOrderPanel");
        JRadioButtonFixture orderBtn = bitOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "MSB First".equals(radio.getText());
            }
        });
        orderBtn.click();

        clickBitPlane();
    }

    @Test
    public void testAll_Column_LSB() {
        clickAll();

        JPanelFixture extractByPanel = frame.panel("extractByPanel");
        JRadioButtonFixture byBtn = extractByPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "Column".equals(radio.getText());
            }
        });
        byBtn.click();


        JPanelFixture bitOrderPanel = frame.panel("bitOrderPanel");
        JRadioButtonFixture orderBtn = bitOrderPanel.radioButton(new GenericTypeMatcher<JRadioButton>(JRadioButton.class) {
            @Override
            protected boolean isMatching(JRadioButton radio) {
                return "LSB First".equals(radio.getText());
            }
        });
        orderBtn.click();

        clickBitPlane();
    }

    @Test
    public void testCancelButton(){
        frame.requireVisible();

        JButtonFixture btn = frame.button(JButtonMatcher.withText("Cancel"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();

        frame.requireNotVisible();
    }

    @Test
    public void testSaveTextButton() throws IOException {
        clickAll();

        File testTarget = new File("src/test/testcase/results/extract");
        if(testTarget.exists()) { testTarget.delete(); }

        JButtonFixture saveBtn = frame.button(JButtonMatcher.withText("Save Text"));
        saveBtn.requireVisible();
        saveBtn.requireEnabled();
        saveBtn.click();
        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText("extract");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

        testTarget = new File("src/test/testcase/results/extract");
        Assert.assertTrue(testTarget.exists());
        frame.requireVisible();

        saveBtn.click();
        fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText(new String(new char[300]).replace('\0', '1'));
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

    }

    @Test
    public void testSaveBinButton() {
        clickAll();

        File testTarget = new File("src/test/testcase/results/extract");
        if(testTarget.exists()) { testTarget.delete(); }

        JButtonFixture saveBtn = frame.button(JButtonMatcher.withText("Save Bin"));
        saveBtn.requireVisible();
        saveBtn.requireEnabled();
        saveBtn.click();
        JFileChooserFixture fileChooserFixture = frame.fileChooser();
        fileChooserFixture.setCurrentDirectory(new File("src/test/testcase/results/"));
        fileChooserFixture.fileNameTextBox().setText("extract");
        fileChooserFixture.pressAndReleaseKey(KeyPressInfo.keyCode(KeyEvent.VK_ENTER));

        testTarget = new File("src/test/testcase/results/extract");
        Assert.assertTrue(testTarget.exists());
        frame.requireVisible();

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
        extract = null;
    }

}