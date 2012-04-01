package ru.avokin.highlighting.impl;

import ru.avokin.highlighting.CodeHighlighter;
import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.utils.FileUtils;

import java.io.File;
import java.util.Map;

/**
 * User: Andrey Vokin
 * Date: 06.10.2010
 */
public class CodeHighlighterManagerImpl implements CodeHighlighterManager {

    private final Map<String, CodeHighlighter> fileExtensionToHighlightersMap;

    private final CodeHighlighter defaultCodeHighlighter;

    public CodeHighlighterManagerImpl(Map<String, CodeHighlighter> fileExtensionToHighlightersMap,
                                      CodeHighlighter defaultCodeHighlighter) {
        this.fileExtensionToHighlightersMap = fileExtensionToHighlightersMap;
        this.defaultCodeHighlighter = defaultCodeHighlighter;
    }

    public CodeHighlighter getCodeHighlighter(File file) {
        String fileExtension = FileUtils.getExtension(file);

        CodeHighlighter result = fileExtensionToHighlightersMap.get(fileExtension);
        if (result == null) {
            result = defaultCodeHighlighter;
        }
        return result; 
    }
}
