package ru.avokin.highlighting;

import java.io.File;

/**
 * User: Andrey Vokin
 * Date: 05.10.2010
 */
public interface CodeHighlighterManager {
    
    CodeHighlighter getCodeHighlighter(File f);
}
