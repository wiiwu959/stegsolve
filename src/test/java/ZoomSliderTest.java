import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import javax.swing.*;
import java.lang.reflect.Field;

// addChangeListener will be tested in StegSolveTest.
// line 44~47 seems won't be called forever??

public class ZoomSliderTest {

    protected ZoomSlider panel;

    @Before
    public final void setUp() {
        panel = new ZoomSlider(10, 1000, 100);
    }

    @Test
    public void testConstruct(){
        Assert.assertNotEquals(new ZoomSlider(10, 1000, 100), null);
        Assert.assertNotEquals(new ZoomSlider(10, 10, 10), null);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ZoomSlider(10, 1000, 1);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ZoomSlider(10, 1000, 1001);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ZoomSlider(1000, 10, 100);
        });
    }

    @Test
    public void testGetValue() throws NoSuchFieldException, IllegalAccessException {
        Field sliderField = ZoomSlider.class.getDeclaredField("slider");
        Field textBoxField = ZoomSlider.class.getDeclaredField("textBox");
        sliderField.setAccessible(true);
        textBoxField.setAccessible(true);

        JSlider slider = (JSlider) sliderField.get(panel);
        JTextField textbox = (JTextField) textBoxField.get(panel);

        int sets[] = {1, 10, 100, 200, 1000, 1100};
        int expects[] = {10, 10, 100, 200, 1000, 1000};
        for (int i=0; i<sets.length; i++) {
            panel.setValue(sets[i]);
            Assert.assertEquals(sets[i], panel.getValue());
            Assert.assertEquals(Integer.toString(sets[i]), textbox.getText());
            Assert.assertEquals(expects[i], slider.getValue());
        }
    }

    @After
    public final void tearDown() {
        this.panel = null;
    }
}