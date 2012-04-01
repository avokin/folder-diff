package ru.avokin.highlighting;

import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 05.10.2010
 */
public class HighlightedCodeBlock {

    private final Color color;

    private final int start;

    private final int end;

    public HighlightedCodeBlock(Color color, int start, int end) {
        this.color = color;
        this.start = start;
        this.end = end;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public Color getColor() {
        return color;
    }
}
