package ru.avokin.uidiff.diff.filediff.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.avokin.filediff.CodeBlock;
import ru.avokin.filediff.Difference;
import ru.avokin.filediff.LongestCommonSubsequenceSearcher;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;

import java.util.ArrayList;

/**
 * User: Andrey Vokin
 * Date: 12.08.2010
 */
public class DiffAdjusterTest {

    private static final int CHAR_HEIGHT = 20;

    private static final int WINDOW_HEIGHT = 300;

    private Difference d;

    private java.util.List<FileDiffCodeBlock> codeBlockList;

    private java.util.List<FileDiffCodeBlock> oppositeCodeBlockList;

    private FileDiffAdjuster diffAdjuster;

    @Before
    public void init() {
        d = LongestCommonSubsequenceSearcher.createDifference(9, 9, 15, 15);

        codeBlockList = new ArrayList<FileDiffCodeBlock>();
        codeBlockList.add(d.getLeftCodeBlock());

        oppositeCodeBlockList = new ArrayList<FileDiffCodeBlock>();
        oppositeCodeBlockList.add(d.getRightCodeBlock());

        diffAdjuster = new FileDiffAdjuster(CHAR_HEIGHT);
    }

    @Test
    public void testGetLastBlockBeforeMiddleArea() {
        int sourceScrollValue = 100;
        int middle = WINDOW_HEIGHT / 2 + sourceScrollValue;

        int indexOfLastBlockBeforeMiddleLine = diffAdjuster.getIndexOfLastBlockBeforeMiddleArea(middle, codeBlockList);
        Assert.assertEquals(0, indexOfLastBlockBeforeMiddleLine);
    }

    @Test
    public void testGetBlockInMiddleArea() {
        // Block passed.
        int sourceScrollValue = 100;
        int middle = WINDOW_HEIGHT / 3 + sourceScrollValue;
        CodeBlock cb = diffAdjuster.getBlockInMiddleArea(-1, middle, codeBlockList);
        Assert.assertNull(cb);

        // Last point of the block.
        sourceScrollValue = 99;
        middle = WINDOW_HEIGHT / 3 + sourceScrollValue;
        cb = diffAdjuster.getBlockInMiddleArea(-1, middle, codeBlockList);
        Assert.assertNotNull(cb);


        // First point of the block in middle area.
        sourceScrollValue = 60;
        middle = WINDOW_HEIGHT / 3 + sourceScrollValue;
        cb = diffAdjuster.getBlockInMiddleArea(-1, middle, codeBlockList);
        Assert.assertNotNull(cb);

        // Block hasn't come into middle area yet.
        sourceScrollValue = 59;
        middle = WINDOW_HEIGHT / 3 + sourceScrollValue;
        cb = diffAdjuster.getBlockInMiddleArea(-1, middle, codeBlockList);
        Assert.assertNull(cb);
    }

    @Test
    public void testDeltaOfPassedBlocks() {
        int delta = diffAdjuster.deltaOfPassedBlocks(codeBlockList, oppositeCodeBlockList, 0);

        int expectedDelta = (d.getRightCodeBlock().getEndLine() - d.getLeftCodeBlock().getEndLine()) * CHAR_HEIGHT;
        Assert.assertEquals(expectedDelta, delta);
    }

    @Test
    public void testDeltaInMiddle() {
        int sourceScrollValue = 60;
        int middle = WINDOW_HEIGHT / 3 + sourceScrollValue;
        int delta = diffAdjuster.deltaInMiddle(codeBlockList, oppositeCodeBlockList, middle, 0, 0);
        Assert.assertEquals(0, delta);

        sourceScrollValue = 80;
        middle = WINDOW_HEIGHT / 3 + sourceScrollValue;
        delta = diffAdjuster.deltaInMiddle(codeBlockList, oppositeCodeBlockList, middle, 0, 0);
        Assert.assertEquals(60, delta);

        sourceScrollValue = 100;
        middle = WINDOW_HEIGHT / 3 + sourceScrollValue;
        delta = diffAdjuster.deltaInMiddle(codeBlockList, oppositeCodeBlockList, middle, 0, 0);
        Assert.assertEquals(120, delta);
    }

    @Test
    public void testDelta() {
        int sourceScrollValue = 60;
        int delta = diffAdjuster.delta(codeBlockList, oppositeCodeBlockList, sourceScrollValue, WINDOW_HEIGHT);
        Assert.assertEquals(0, delta);

        sourceScrollValue = 80;
        delta = diffAdjuster.delta(codeBlockList, oppositeCodeBlockList, sourceScrollValue, WINDOW_HEIGHT);
        Assert.assertEquals(60, delta);

        sourceScrollValue = 99;
        delta = diffAdjuster.delta(codeBlockList, oppositeCodeBlockList, sourceScrollValue, WINDOW_HEIGHT);
        Assert.assertEquals(117, delta);

        sourceScrollValue = 100;
        delta = diffAdjuster.delta(codeBlockList, oppositeCodeBlockList, sourceScrollValue, WINDOW_HEIGHT);
        Assert.assertEquals(120, delta);

        sourceScrollValue = 101;
        delta = diffAdjuster.delta(codeBlockList, oppositeCodeBlockList, sourceScrollValue, WINDOW_HEIGHT);
        Assert.assertEquals(120, delta);

        sourceScrollValue = 120;
        delta = diffAdjuster.delta(codeBlockList, oppositeCodeBlockList, sourceScrollValue, WINDOW_HEIGHT);
        Assert.assertEquals(120, delta);
    }
}
