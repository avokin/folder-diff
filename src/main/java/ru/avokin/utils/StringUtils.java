package ru.avokin.utils;

import ru.avokin.swing.codeviewer.model.LinePosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 29.09.2010
 */
public class StringUtils {
    
    private static final String LINE_SPLITTER = "\n";

    /**
     * Calculate start and end positions of each lines in the text.
     * @param text String to parse.
     * @return List of LinePosition objects.
     */
    public static List<LinePosition> prepareLinePositions(String text) {
        text = text.replace("\r", "");
        List<LinePosition> result = new ArrayList<LinePosition>();

        int start = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '\n') {
                LinePosition lp = new LinePosition(start, i);
                result.add(lp);
                start = i + 1;
            }
        }

        if (start < text.length()) {
            LinePosition lp = new LinePosition(start, text.length() - 1);
            result.add(lp);
        }

        return result;
    }

    public static String createAlignedLineNumber(int value, String maxValueRepresentation) {
        StringBuilder result = new StringBuilder(Integer.toString(value));
        while (result.length() <= maxValueRepresentation.length()) {
            result.insert(0, ' ');
        }
        return result.toString();
    }

    public static List<String> splitToLines(String s) {
        String[] resultArray = s.split(LINE_SPLITTER);
        return Arrays.asList(resultArray);
    }
}
