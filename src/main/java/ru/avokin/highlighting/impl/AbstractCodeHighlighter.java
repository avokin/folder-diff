package ru.avokin.highlighting.impl;

import ru.avokin.highlighting.CodeHighlighter;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * User: Andrey Vokin
 * Date: 06.10.2010
 */
public abstract class AbstractCodeHighlighter implements CodeHighlighter {

    protected Map<String, Color> schema;

    protected AbstractCodeHighlighter() {
        Properties properties;
        try {
            properties = new Properties();
            properties.load(AbstractCodeHighlighter.class.getResourceAsStream(getPropertyFilePath()));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't initialize java plugin", e);
        }

        schema = new HashMap<String, Color>();
        for (Object o : properties.keySet()) {
            String key = (String) o;
            String value = properties.getProperty(key);

            int[] rgb = getRGB(value);
            Color c = new Color(rgb[0], rgb[1], rgb[2]);

            schema.put(key, c);
        }
    }

    protected int[] getRGB(String s) {
        if (s.length() != 6) {
            throw new IllegalArgumentException("Invalid color RGB representation");
        }

        return new int[] {Integer.parseInt(s.substring(0, 2), 16), Integer.parseInt(s.substring(2, 4), 16),
                Integer.parseInt(s.substring(4, 6), 16)};
    }

    protected abstract String getPropertyFilePath();
}
