package ru.avokin.highlighting.impl.java;

import com.intellij.lexer.JavaTokenType;
import com.intellij.psi.JavaLexer;
import org.apache.log4j.Logger;
import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.highlighting.impl.AbstractCodeHighlighter;
import ru.avokin.lexer.Lexer;
import ru.avokin.lexer.TokenType;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 05.10.2010
 */
public class JavaCodeHighlighter extends AbstractCodeHighlighter {

    private static final Logger logger = Logger.getLogger(JavaCodeHighlighter.class);

    @Override
    protected String getPropertyFilePath() {
        return "/highlighting/java-highlighter.properties";
    }

    public List<HighlightedCodeBlock> highlight(String text) {
        List<HighlightedCodeBlock> result = new ArrayList<HighlightedCodeBlock>();
        Lexer lexer = new JavaLexer(true, true);
        lexer.reset(text);

        try {
            while (true) {
                TokenType tokenType = lexer.nextToken();
                if (tokenType == null) {
                    break;
                }
                if (JavaTokenType.KEYWORD_BIT_SET.contains(tokenType)) {
                    Color c = schema.get("keyword");
                    HighlightedCodeBlock hcb = new HighlightedCodeBlock(c, lexer.getTokenStart(), lexer.getTokenEnd() - 1);
                    result.add(hcb);
                } else if (JavaTokenType.STRING_LITERAL_SET.contains(tokenType)) {
                    Color c = schema.get("string");
                    HighlightedCodeBlock hcb = new HighlightedCodeBlock(c, lexer.getTokenStart(), lexer.getTokenEnd() - 1);
                    result.add(hcb);
                } else if (JavaTokenType.NUMBER_LITERAL_SET.contains(tokenType)) {
                    Color c = schema.get("number");
                    HighlightedCodeBlock hcb = new HighlightedCodeBlock(c, lexer.getTokenStart(), lexer.getTokenEnd() - 1);
                    result.add(hcb);
                } else {
                    Color c = Color.black;
                    HighlightedCodeBlock hcb = new HighlightedCodeBlock(c, lexer.getTokenStart(), lexer.getTokenEnd() - 1);
                    result.add(hcb);
                }
            }
        } catch (Exception e) {
            logger.error(e, e);
        }

        return result;
    }
}