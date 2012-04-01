package ru.avokin.highlighting;

import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 05.10.2010
 */
public interface CodeHighlighter {
    
    List<HighlightedCodeBlock> highlight(String text);
}
