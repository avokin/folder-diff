package ru.avokin.filediff;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * User: Andrey Vokin
 * Date: 02.10.2010
 */
public class QuickFileContentComparator {

    public static boolean equals(File f1, File f2) throws IOException {
        if (f1.length() != f2.length()) {
            return false;
        }
        
        boolean result = true;
        FileInputStream fis1 = null;
        FileInputStream fis2 = null;
        try {
            fis1 = new FileInputStream(f1);
            fis2 = new FileInputStream(f2);

            byte[] buf1 = new byte[1024];
            byte[] buf2 = new byte[1024];
            int i;
            int j;
            while (((i = fis1.read(buf1)) != -1) && (j = fis2.read(buf2)) != -1) {
                if (i != j) {
                    result = false;
                    break;
                }
                if (!Arrays.equals(buf1, buf2)) {
                    result = false;
                    break;
                }
            }
        } finally {
            try {
                if (fis1 != null) {
                    fis1.close();
                }
            } finally {
                if (fis2 != null) {
                    fis2.close();
                }
            }
        }
        return result;
    }
}
