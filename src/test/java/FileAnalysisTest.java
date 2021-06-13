import org.assertj.swing.core.matcher.JButtonMatcher;
import org.assertj.swing.core.matcher.JTextComponentMatcher;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.fixture.JButtonFixture;
import org.assertj.swing.fixture.JTextComponentFixture;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

// coverage 98% (581/587)

public class FileAnalysisTest {
    protected FrameFixture frame;

    @Test
    public final void testFrame() {
        File file = new File("src/test/testcase/minion.jpg");
        frame = new FrameFixture(new FileAnalysis(file));
        frame.show();

        frame.requireTitle("File Format Analysis");

        JButtonFixture btn = frame.button(JButtonMatcher.withText("OK"));
        btn.requireVisible();
        btn.requireEnabled();
        btn.click();
        frame.requireNotVisible();
    }

    @Test
    public void testErrorReading(){
        File file = new File("src/test/testcase/fileanalysis/notexist");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Error reading file: "));
    }

    @Test
    public final void testTooShort() {
        File file = new File("src/test/testcase/fileanalysis/short");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("file too short?"));
    }

    @Test // normal one
    public final void testBMP1() {
        File file = new File("src/test/testcase/fileanalysis/bmp1.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("File Header info:"));
        Assert.assertTrue(content.contains("Bitmap data starts at:"));
        Assert.assertTrue(content.contains("Compression: 0 (No compression)"));
        Assert.assertTrue(content.contains("Dump of gap \n" + "    between header and start of color table:"));
        Assert.assertTrue(content.contains("Hex:"));
        Assert.assertTrue(content.contains("Ascii:"));
        Assert.assertTrue(content.contains("Row filler bytes dump:"));
    }

    @Test // File is too short to contain headers
    public final void testBMP2() {
        File file = new File("src/test/testcase/fileanalysis/bmp2.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("File is too short to contain headers"));
    }

    @Test // f.length>fsz; compress == 1; get_word(28)<=8; ctstart!=offbits
    public final void testBMP3() {
        File file = new File("src/test/testcase/fileanalysis/bmp3.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Additional bytes at end of file = "));
        Assert.assertTrue(content.contains("Compression: \n" + "    1 (RLE for 8bpp RGB)"));
        Assert.assertTrue(content.contains("Color Index Table (NB fourth entry of each index \n"
                + "    should be zero, order is b,g,r,a):"));
        Assert.assertTrue(content.contains("Color table finishes at"));
        Assert.assertTrue(content.contains("Further \n" +
                "    checking is only done when there is no compression"));
    }

    @Test // compress == 2; ctstart<offbits;
    public final void testBMP4() {
        File file = new File("src/test/testcase/fileanalysis/bmp4.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Compression: 2 (RLE for 4bpp RGB)"));
        Assert.assertTrue(content.contains("Dump of gap between \n" + "    color table and image:"));
    }

    @Test // compress == 3;
    public final void testBMP5() {
        File file = new File("src/test/testcase/fileanalysis/bmp5.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Compression: 3 (Raw RGB with packing)"));
    }

    @Test // compress == 0x32776173;
    public final void testBMP6() {
        File file = new File("src/test/testcase/fileanalysis/bmp6.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Compression: 32776173 (Raw RGB)"));
    }

    @Test // compress == 0x41424752;
    public final void testBMP7() {
        File file = new File("src/test/testcase/fileanalysis/bmp7.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Compression: 41424752 (Raw RGB with alpha)"));
    }

    @Test // compress == 0x54424752;
    public final void testBMP8() {
        File file = new File("src/test/testcase/fileanalysis/bmp8.bmp");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Compression: 54424752 (Raw RGB with transparency)"));
    }



    @Test
    public final void testPNG1() {
        File file = new File("src/test/testcase/fileanalysis/png1.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Critical - necessary \n" +
                "    for display of image MUST BE recognized to proceed"));
        Assert.assertTrue(content.contains("Public"));
        Assert.assertTrue(content.contains("Unsafe to \n" +
                "    copy unless known to software"));
        Assert.assertTrue(content.contains("(deflate)"));
    }

    @Test
    public final void testPNG2() {
        File file = new File("src/test/testcase/fileanalysis/png2.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains(" (unknown)"));
        Assert.assertFalse(content.contains(" (deflate)"));
        Assert.assertFalse(content.contains(" (adaptive)"));
        Assert.assertFalse(content.contains(" (none)"));
        Assert.assertTrue(content.contains(" (adam7)"));
    }

    @Test
    public final void testPNG3() {
        File file = new File("src/test/testcase/fileanalysis/png3.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains(" (unknown)"));
        Assert.assertFalse(content.contains(" (deflate)"));
        Assert.assertFalse(content.contains(" (adaptive)"));
        Assert.assertFalse(content.contains(" (none)"));
        Assert.assertFalse(content.contains(" (adam7)"));
    }

    @Test // typ == 0x504C5445, 0x624B4744
    public final void testPNG4() {
        File file = new File("src/test/testcase/fileanalysis/png4.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("RGB entries"));
        Assert.assertTrue(content.contains("Alert:"));
    }

    @Test // typ == 0x6348524D, 0x67414D41
    public final void testPNG5() {
        File file = new File("src/test/testcase/fileanalysis/png5.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("NB data"));
        Assert.assertTrue(content.contains("gamma"));
    }

    @Test // typ == 0x68495354, 0x70485973
    public final void testPNG6() {
        File file = new File("src/test/testcase/fileanalysis/png6.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("histogram"));
        Assert.assertTrue(content.contains("Physical"));
    }

    @Test // typ == 0x73424954, 0x74455874
    public final void testPNG7() {
        File file = new File("src/test/testcase/fileanalysis/png7.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Significant"));
        Assert.assertTrue(content.contains("Consists"));
    }

    @Test // typ == 0x74494D45, 0x74524E53
    public final void testPNG8() {
        File file = new File("src/test/testcase/fileanalysis/png8.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("modification time"));
        Assert.assertTrue(content.contains("Transparency"));
    }

    @Test // typ == 0x7a545874, Unknown
    public final void testPNG9() {
        File file = new File("src/test/testcase/fileanalysis/png9.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Keyword"));
        Assert.assertTrue(content.contains("Unknown"));
    }

    @Test // typ == Premature end to file?
    public final void testPNG10() {
        File file = new File("src/test/testcase/fileanalysis/png10.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Premature"));
        Assert.assertTrue(content.contains("Ancillary"));
        Assert.assertTrue(content.contains("Private"));
        Assert.assertTrue(content.contains("reserved flag set"));
        Assert.assertTrue(content.contains("Safe to copy"));
    }

    @Test // Not enough room in file for data?
    public final void testPNG11() {
        File file = new File("src/test/testcase/fileanalysis/png11.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("room"));
    }

    @Test // file too short?
    public final void testPNG12() {
        File file = new File("src/test/testcase/fileanalysis/png12.png");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("short"));
    }


    @Test
    public final void testGIF1() {
        File file = new File("src/test/testcase/fileanalysis/gif1.gif");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("LCT Size"));
        Assert.assertTrue(content.contains("(Interlace)"));
        Assert.assertTrue(content.contains("Sort)"));
        Assert.assertTrue(content.contains("(**Reserved flags set **)"));
    }

    @Test
    public final void testGIF2() {
        File file = new File("src/test/testcase/fileanalysis/gif2.gif");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Comment"));
    }

    @Test
    public final void testGIF3() {
        File file = new File("src/test/testcase/fileanalysis/gif3.gif");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Plain"));
    }

    @Test
    public final void testGIF4() {
        File file = new File("src/test/testcase/fileanalysis/gif4.gif");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Application"));
    }

    @Test
    public final void testGIF5() {
        File file = new File("src/test/testcase/fileanalysis/gif5.gif");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("short"));
    }

    @Test
    public final void testGIF6() {
        File file = new File("src/test/testcase/fileanalysis/gif6.gif");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("short"));
    }

    @Test
    public final void testGIF7() {
        File file = new File("src/test/testcase/fileanalysis/gif7.gif");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("short"));
    }




    @Test
    public final void testJPG1() {
        File file = new File("src/test/testcase/fileanalysis/jpg1.jpg");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Baseline"));
    }

    @Test
    public final void testJPG2() {
        File file = new File("src/test/testcase/fileanalysis/jpg2.jpg");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Extended"));
        Assert.assertTrue(content.contains("Progressive"));
        Assert.assertTrue(content.contains("Lossless"));
    }

    @Test
    public final void testJPG3() {
        File file = new File("src/test/testcase/fileanalysis/jpg3.jpg");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Restart"));
    }

    @Test
    public final void testJPG4() {
        File file = new File("src/test/testcase/fileanalysis/jpg4.jpg");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("Define"));
        Assert.assertTrue(content.contains("Interval"));
        Assert.assertTrue(content.contains("Expand"));
        Assert.assertTrue(content.contains("Comment"));

    }

    @Test
    public final void testBadFormat() {
        File file = new File("src/test/testcase/fileanalysis/badformat");
        frame = new FrameFixture(new FileAnalysis(file));

        JTextComponentFixture analysisText = frame.textBox(JTextComponentMatcher.any());
        analysisText.requireVisible();
        String content = analysisText.text();

        Assert.assertTrue(content.contains("File format analysis code not done yet!"));
    }

    @After
    public final void tearDown() {
        frame.cleanUp();
        this.frame = null;
    }
}