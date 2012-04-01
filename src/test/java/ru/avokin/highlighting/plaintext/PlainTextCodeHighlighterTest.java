package ru.avokin.highlighting.plaintext;

import junit.framework.Assert;
import org.junit.Test;
import ru.avokin.highlighting.CodeHighlighter;
import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.highlighting.impl.plaintext.PlainTextCodeHighlighter;
import ru.avokin.utils.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 06.10.2010
 */
public class PlainTextCodeHighlighterTest {

    private CodeHighlighter plainTextCodeHighlighter;

    private String fileContent;

    public void init(String testFolder) throws URISyntaxException, IOException {
        plainTextCodeHighlighter = new PlainTextCodeHighlighter();

        URL urlFile = getClass().getResource(testFolder + "/textFile.txt");
        File file = new File(urlFile.toURI());

        fileContent = FileUtils.readFileAsString(file.getAbsolutePath());
    }

    @Test
    public void test01() throws IOException, URISyntaxException {
        init("/highlighting/plaintext/test01");
        fileContent = fileContent.trim();
        List<HighlightedCodeBlock> result = plainTextCodeHighlighter.highlight(fileContent);

        Assert.assertEquals(1, result.size());

        Assert.assertEquals(Color.black, result.get(0).getColor());
        Assert.assertEquals(0, result.get(0).getStart());
        Assert.assertEquals(fileContent.length() - 1, result.get(0).getEnd());
    }
}
