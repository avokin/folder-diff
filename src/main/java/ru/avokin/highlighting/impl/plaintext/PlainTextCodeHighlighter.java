package ru.avokin.highlighting.impl.plaintext;

import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.highlighting.impl.AbstractCodeHighlighter;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 06.10.2010
 */
public class PlainTextCodeHighlighter extends AbstractCodeHighlighter {

    private final Color c;

    public PlainTextCodeHighlighter() {
        c = schema.get("all");
    }

    public List<HighlightedCodeBlock> highlight(String text) {
        List<HighlightedCodeBlock> result = new ArrayList<HighlightedCodeBlock>();
        result.add(new HighlightedCodeBlock(c, 0, text.length() - 1));
        return result;
    }

    @Override
    protected String getPropertyFilePath() {
        return "/highlighting/plain-text-highlighter.properties";
    }
}
