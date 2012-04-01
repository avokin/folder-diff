package ru.avokin.swing.codeviewer;

import junit.framework.Assert;
import org.junit.Test;
import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.swing.codeviewer.view.CodeBlockComparator;

/**
 * User: Andrey Vokin
 * Date: 07.10.2010
 */
public class CodeBlockComparatorTest {

    @Test
    public void testCompare() {
        HighlightedCodeBlock o1 = new HighlightedCodeBlock(null, 0, 5);
        HighlightedCodeBlock o2 = new HighlightedCodeBlock(null, 1, 4);
        Assert.assertTrue(0 == CodeBlockComparator.getInstance().compare(o1, o2));

        o1 = new HighlightedCodeBlock(null, 3, 5);
        o2 = new HighlightedCodeBlock(null, 1, 2);
        Assert.assertTrue(0 < CodeBlockComparator.getInstance().compare(o1, o2));

        o1 = new HighlightedCodeBlock(null, 3, 5);
        o2 = new HighlightedCodeBlock(null, 6, 9);
        Assert.assertTrue(0 > CodeBlockComparator.getInstance().compare(o1, o2));

        o1 = new HighlightedCodeBlock(null, 1, 5);
        o2 = new HighlightedCodeBlock(null, 1, 4);
        Assert.assertTrue(0 == CodeBlockComparator.getInstance().compare(o1, o2));

        o1 = new HighlightedCodeBlock(null, 1, 5);
        o2 = new HighlightedCodeBlock(null, 4, 5);
        Assert.assertTrue(0 == CodeBlockComparator.getInstance().compare(o1, o2));
    }
}
