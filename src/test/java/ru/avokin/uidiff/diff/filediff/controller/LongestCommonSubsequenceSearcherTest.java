package ru.avokin.uidiff.diff.filediff.controller;

import junit.framework.Assert;
import org.junit.Test;
import ru.avokin.filediff.Difference;
import ru.avokin.filediff.FileDifference;
import ru.avokin.filediff.LongestCommonSubsequenceSearcher;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 25.08.2010
 */
public class LongestCommonSubsequenceSearcherTest {

    @Test
    public void testFindLongestCommonSubsequence() {
        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        l1.add("c");
        l1.add("d");
        l1.add("e");
        List<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("b");
        l2.add("g");
        l2.add("e");

        int[][] max = LongestCommonSubsequenceSearcher.findLongestCommonSubsequence(l1, l2);
        Assert.assertEquals(5, max.length);
        Assert.assertEquals(4, max[0].length);

        Assert.assertEquals(1, max[0][0]);
        Assert.assertEquals(2, max[1][1]);
        Assert.assertEquals(2, max[1][1]);
        Assert.assertEquals(3, max[4][3]);
    }

    @Test
    public void testDiff1() {
        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        l1.add("c");
        l1.add("d");
        l1.add("e");
        List<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("b");
        l2.add("g");
        l2.add("e");

        FileDifference diffResult = LongestCommonSubsequenceSearcher.diff(l1, l2);
        Assert.assertEquals(1, diffResult.getDifferenceList().size());

        Difference d = diffResult.getDifferenceList().get(0);
        Assert.assertEquals(2, d.getLeftCodeBlock().getStartLine());
        Assert.assertEquals(3, d.getLeftCodeBlock().getEndLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getStartLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getEndLine());

    }

    @Test
    public void testDiff2() {
        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        l1.add("c");
        l1.add("d");
        l1.add("e");
        List<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("b");
        l2.add("e");

        FileDifference diffResult = LongestCommonSubsequenceSearcher.diff(l1, l2);
        Assert.assertEquals(1, diffResult.getDifferenceList().size());

        Difference d = diffResult.getDifferenceList().get(0);
        Assert.assertEquals(2, d.getLeftCodeBlock().getStartLine());
        Assert.assertEquals(3, d.getLeftCodeBlock().getEndLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getStartLine());
        Assert.assertEquals(1, d.getRightCodeBlock().getEndLine());
    }

    @Test
    public void testDiff3() {
        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        l1.add("c");
        l1.add("d");
        l1.add("e");
        List<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("b");
        l2.add("g");

        FileDifference diffResult = LongestCommonSubsequenceSearcher.diff(l1, l2);
        Assert.assertEquals(1, diffResult.getDifferenceList().size());

        Difference d = diffResult.getDifferenceList().get(0);
        Assert.assertEquals(2, d.getLeftCodeBlock().getStartLine());
        Assert.assertEquals(4, d.getLeftCodeBlock().getEndLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getStartLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getEndLine());
    }

    @Test
    public void testDiff4() {
        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        List<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("b");
        l2.add("g");

        FileDifference diffResult = LongestCommonSubsequenceSearcher.diff(l1, l2);
        Assert.assertEquals(1, diffResult.getDifferenceList().size());

        Difference d = diffResult.getDifferenceList().get(0);
        Assert.assertEquals(2, d.getLeftCodeBlock().getStartLine());
        Assert.assertEquals(1, d.getLeftCodeBlock().getEndLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getStartLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getEndLine());
    }

    @Test
    public void testDiff5() {
        List<String> l1 = new ArrayList<String>();
        l1.add("p");
        l1.add("a");
        l1.add("b");
        List<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("b");
        l2.add("g");

        FileDifference diffResult = LongestCommonSubsequenceSearcher.diff(l1, l2);
        Assert.assertEquals(2, diffResult.getDifferenceList().size());

        Difference d = diffResult.getDifferenceList().get(0);
        Assert.assertEquals(0, d.getLeftCodeBlock().getStartLine());
        Assert.assertEquals(0, d.getLeftCodeBlock().getEndLine());
        Assert.assertEquals(0, d.getRightCodeBlock().getStartLine());
        Assert.assertEquals(-1, d.getRightCodeBlock().getEndLine());

        d = diffResult.getDifferenceList().get(1);
        Assert.assertEquals(3, d.getLeftCodeBlock().getStartLine());
        Assert.assertEquals(2, d.getLeftCodeBlock().getEndLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getStartLine());
        Assert.assertEquals(2, d.getRightCodeBlock().getEndLine());
    }
}
