package ru.avokin.swing.codeviewer.view;

import ru.avokin.highlighting.HighlightedCodeBlock;

import java.util.Comparator;

/**
 * User: Andrey Vokin
 * Date: 07.10.2010
 */
public class CodeBlockComparator implements Comparator<HighlightedCodeBlock> {

    public static final CodeBlockComparator INSTANCE = new CodeBlockComparator();

    private CodeBlockComparator() {
    }

    public static CodeBlockComparator getInstance() {
        return INSTANCE;
    }

    public int compare(HighlightedCodeBlock o1, HighlightedCodeBlock o2) {
        if (o1.getStart() <= o2.getStart() && o2.getEnd() <= o1.getEnd()) {
            return 0;
        } else if (o2.getStart() <= o1.getStart() && o1.getEnd() <= o2.getEnd()) {
            return 0;
        } else {
            return o1.getStart() - o2.getStart();
        }
    }
}
