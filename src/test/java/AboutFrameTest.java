import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JTextComponentMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.*;

public class AboutFrameTest extends AssertJSwingTestCaseTemplate {
    protected FrameFixture frame;

    @Before
    public final void setUp() {
        frame = new FrameFixture(new AboutFrame());
        frame.show();
    }

    @Test
    public void testFrame(){
        frame.requireTitle("");
        frame.requireVisible();
        frame.requireEnabled();
    }

    @Test
    public void testText(){
        JTextComponentFixture aboutText = frame.textBox(JTextComponentMatcher.any());
        aboutText.requireNotEditable();
        aboutText.requireVisible();
        Assert.assertNotEquals(0, aboutText.text().length());
    }

    @Test
    public void testButton(){
        JButtonFixture btn = frame.button(JButtonMatcher.withText("OK"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();
        frame.requireNotVisible();
    }

    @After
    public final void tearDown() {
        frame.cleanUp();
        this.frame = null;
    }

}