package ru.avokin.lexer.java;

import com.intellij.lexer.JavaTokenType;
import com.intellij.psi.JavaLexer;
import junit.framework.Assert;
import org.junit.Test;
import ru.avokin.lexer.Lexer;
import ru.avokin.lexer.TokenType;
import ru.avokin.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 06.10.2010
 */
public class JavaLexerTest {

    Lexer javaLexer;

    String fileContent;

    private void init(String testFolder) throws URISyntaxException, IOException {
        javaLexer = new JavaLexer(true, true);

        URL urlFile1 = getClass().getResource(testFolder + "/javaFile.txt");
        File file1 = new File(urlFile1.toURI());
        fileContent = FileUtils.readFileAsString(file1.getAbsolutePath());
    }

    @Test
    public void test01() throws IOException, URISyntaxException {
        init("/lexer/java/test01");
        
        int[] startPositions = new int[] {0, 5, 6, 15, 16, 17, 18, 24, 25, 26, 27, 28, 29, 37, 38, 39, 40};
        int[] endPositions = new int[] {5, 6, 15, 16, 17, 18, 24, 25, 26, 27, 28, 29, 37, 38, 39, 40, 41};
        TokenType[] tokenTypes = new TokenType[] {JavaTokenType.CLASS_KEYWORD, JavaTokenType.WHITE_SPACE,
                JavaTokenType.IDENTIFIER, JavaTokenType.WHITE_SPACE, JavaTokenType.LBRACE, JavaTokenType.WHITE_SPACE,
                JavaTokenType.IDENTIFIER, JavaTokenType.WHITE_SPACE, JavaTokenType.IDENTIFIER,
                JavaTokenType.WHITE_SPACE, JavaTokenType.EQ, JavaTokenType.WHITE_SPACE, JavaTokenType.STRING_LITERAL,
                JavaTokenType.SEMICOLON, JavaTokenType.WHITE_SPACE, JavaTokenType.RBRACE, JavaTokenType.WHITE_SPACE};

        javaLexer.reset(fileContent);
        int count = 0;
        while (true) {
            TokenType tokenType = javaLexer.nextToken();
            if (tokenType == null) {
                break;
            }

            int startPosition = javaLexer.getTokenStart();
            int endPosition = javaLexer.getTokenEnd();
            Assert.assertEquals(tokenTypes[count], tokenType);
            Assert.assertEquals(startPositions[count], startPosition);
            Assert.assertEquals(endPositions[count], endPosition);
            count++;
        }

        Assert.assertEquals(startPositions.length, count);
    }
}
