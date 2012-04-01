package ru.avokin.uidiff.diff.common.model;

/**
 * User: Andrey Vokin
 * Date: 01.10.2010
 */
public class DiffSideModel {

    private final String fileName;

    private final String filePath;

    public DiffSideModel(String fileName, String filePath) {
        this.fileName = fileName;
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }
}
