package ru.avokin.utils;

import java.io.*;

/**
 * UiUtils to read, copy and write files.
 */
public class FileUtils {

    /**
     * Reads file content.
     * @param filePath absolute path to file to be read.
     * @return String (file content).
     * @throws IOException in case of trouble during reading the file.
     */
    public static String readFileAsString(String filePath) throws IOException {
        StringBuilder result = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line).append("\n");
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }

        return result.toString();
    }

    public static String getExtension(File file) {
        String result = null;
        if (file != null) {

            int dotIndex = file.getName().lastIndexOf('.');
            if (dotIndex > 0) {
                result = file.getName().substring(dotIndex + 1);
            }
        }
        return result;
    }
}
