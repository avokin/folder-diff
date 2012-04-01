package ru.avokin.filediff;

import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 30.09.2010
 */
public class FileDifference {

    private final List<Difference> differenceList;

    private List<FileDiffCodeBlock> leftCodeBlockList;

    private List<FileDiffCodeBlock> rightCodeBlockList;

    public FileDifference(List<Difference> differenceList) {
        this.differenceList = Collections.unmodifiableList(differenceList);

        leftCodeBlockList = new ArrayList<FileDiffCodeBlock>();
        rightCodeBlockList = new ArrayList<FileDiffCodeBlock>(
                
        );
        for (Difference d : differenceList) {
            leftCodeBlockList.add(d.getLeftCodeBlock());
            rightCodeBlockList.add(d.getRightCodeBlock());
        }

        leftCodeBlockList = Collections.unmodifiableList(leftCodeBlockList);
        rightCodeBlockList = Collections.unmodifiableList(rightCodeBlockList);
    }

    public List<Difference> getDifferenceList() {
        return differenceList;
    }

    public List<FileDiffCodeBlock> getLeftCodeBlockList() {
        return leftCodeBlockList;
    }

    public List<FileDiffCodeBlock> getRightCodeBlockList() {
        return rightCodeBlockList;
    }
}
