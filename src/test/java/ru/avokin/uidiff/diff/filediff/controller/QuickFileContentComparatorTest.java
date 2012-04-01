package ru.avokin.uidiff.diff.filediff.controller;

import junit.framework.Assert;
import org.junit.Test;
import ru.avokin.filediff.QuickFileContentComparator;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 02.10.2010
 */
public class QuickFileContentComparatorTest {

    private File file1;

    private File file2;

    private void init(String testFolder) throws URISyntaxException {
        URL urlFile1 = getClass().getResource(testFolder + "/file1.txt");
        file1 = new File(urlFile1.toURI());
        URL urlFile2 = getClass().getResource(testFolder + "/file2.txt");
        file2 = new File(urlFile2.toURI());
    }

    @Test
    public void test01() throws IOException, URISyntaxException {
        init("/filediff/test01");
        Assert.assertFalse(QuickFileContentComparator.equals(file1, file2));
    }

    @Test
    public void test02() throws IOException, URISyntaxException {
        init("/filediff/test02");
        Assert.assertFalse(QuickFileContentComparator.equals(file1, file2));
    }

    @Test
    public void test03() throws IOException, URISyntaxException {
        init("/filediff/test03");
        Assert.assertTrue(QuickFileContentComparator.equals(file1, file2));
    }
}
