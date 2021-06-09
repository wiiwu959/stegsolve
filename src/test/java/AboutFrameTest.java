import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.testing.AssertJSwingTestCaseTemplate;
import org.junit.*;

public class AboutFrameTest extends AssertJSwingTestCaseTemplate {
    protected FrameFixture frame;
    private JButtonFixture okButtonFixture;

    @Before
    public final void setUp() {
        this.setUpRobot();
        AboutFrame aboutframe = GuiActionRunner.execute(() -> new AboutFrame());
        this.frame = new FrameFixture(this.robot(), aboutframe);
        this.frame.show();
        this.okButtonFixture = this.frame.button(JButtonMatcher.withText("OK"));
    }

    @Test
    public void test(){
        this.okButtonFixture.requireVisible().requireEnabled().click();
    }

    @After
    public final void tearDown() {
        this.frame = null;
    }

}