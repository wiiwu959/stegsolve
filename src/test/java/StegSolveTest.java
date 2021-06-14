import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.*;

public class StegSolveTest {

    protected FrameFixture frame = null;

    @Before
    public final void setUp() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<StegSolve> stegSolveConstructor = StegSolve.class.getDeclaredConstructor();
        Assert.assertTrue(Modifier.isPrivate(stegSolveConstructor.getModifiers()));
        stegSolveConstructor.setAccessible(true);
        frame = new FrameFixture(stegSolveConstructor.newInstance());
    }

    @Test
    public void test(){

    }

    @After
    public final void tearDown(){
        frame.cleanUp();
        frame = null;
    }
}
