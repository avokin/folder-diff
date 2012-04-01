package ru.avokin.highlighting.java;

import junit.framework.Assert;
import org.junit.Test;
import ru.avokin.highlighting.CodeHighlighter;
import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.highlighting.impl.java.JavaCodeHighlighter;
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
public class JavaCodeHighlighterTest {

    private CodeHighlighter javaCodeHighlighter;

    private String fileContent;

    public void init(String testFolder) throws URISyntaxException, IOException {
        javaCodeHighlighter = new JavaCodeHighlighter();

        URL urlFile = getClass().getResource(testFolder + "/javaFile.txt");
        File file = new File(urlFile.toURI());

        fileContent = FileUtils.readFileAsString(file.getAbsolutePath());
    }

    @Test
    public void test01() throws IOException, URISyntaxException {
        init("/highlighting/java/test01");
        List<HighlightedCodeBlock> result = javaCodeHighlighter.highlight(fileContent.trim());

        Assert.assertEquals(12, result.size());

        // class keyword.
        Assert.assertEquals(new Color(0, 0, 128), result.get(0).getColor());

        for (int i = 1; i < 9; i++) {
            Assert.assertEquals(Color.black, result.get(i).getColor());
        }

        // String literal.
        Assert.assertEquals(new Color(0, 128, 0), result.get(9).getColor());

        for (int i = 10; i < result.size(); i++) {
            Assert.assertEquals(Color.black, result.get(i).getColor());
        }
    }
}
