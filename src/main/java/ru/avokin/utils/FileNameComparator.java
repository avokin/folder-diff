package ru.avokin.utils;

import java.io.File;
import java.util.Comparator;

/**
 * User: Andrey Vokin
 * Date: 20.08.2010
 */
public class FileNameComparator implements Comparator<File> {

    private static final FileNameComparator INSTANCE = new FileNameComparator();

    private FileNameComparator() {
    }

    public static FileNameComparator getInstance() {
        return INSTANCE;
    }

    public int compare(File o1, File o2) {
        if (o1.isDirectory() && !o2.isDirectory()) {
            return -1;
        } else //noinspection IfMayBeConditional
            if (!o1.isDirectory() && o2.isDirectory()) {
            return 1;
        } else {
            return o1.getAbsolutePath().compareToIgnoreCase(o2.getAbsolutePath());
        }
    }
}
