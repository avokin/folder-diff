package ru.avokin.uidiff.diff.common.model;

import ru.avokin.uidiff.common.model.AbstractModel;

import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 23.08.2010
 */
public abstract class AbstractDiffModel extends AbstractModel {

    private int currentDifference;

    private final DiffSideModel leftSideModel;

    private final DiffSideModel rightSideModel;

    protected boolean nextDiffActionEnabled;

    protected boolean previousDiffActionEnabled;

    protected final List<DiffModelChangedListener> diffModelChangedListenerList;

    protected AbstractDiffModel(DiffSideModel leftSideModel, DiffSideModel rightSideModel) {
        this.leftSideModel = leftSideModel;
        this.rightSideModel = rightSideModel;

        diffModelChangedListenerList = new ArrayList<DiffModelChangedListener>();
        currentDifference = -1;
    }


    public int getCurrentDifference() {
        return currentDifference;
    }

    public void setCurrentDifference(int currentDifference) {
        this.currentDifference = currentDifference;
        for (DiffModelChangedListener l : diffModelChangedListenerList) {
            l.CurrentDifferenceChanged(currentDifference);
        }
    }

    public void addDiffModelChangedListener(DiffModelChangedListener l) {
        diffModelChangedListenerList.add(l);
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void removeDiffModelChangedListener(DiffModelChangedListener l) {
        diffModelChangedListenerList.remove(l);
    }

    public abstract int getDifferenceCount();

    public DiffSideModel getLeftSideModel() {
        return leftSideModel;
    }

    public DiffSideModel getRightSideModel() {
        return rightSideModel;
    }

    public boolean isNextDiffActionEnabled() {
        return nextDiffActionEnabled;
    }

    public boolean isPreviousDiffActionEnabled() {
        return previousDiffActionEnabled;
    }

    public void setNextDiffActionEnabled(boolean nextDiffActionEnabled) {
        this.nextDiffActionEnabled = nextDiffActionEnabled;
        for (DiffModelChangedListener l : diffModelChangedListenerList) {
            l.nextDiffActionEnablingChanged();
        }
    }

    public void setPreviousDiffActionEnabled(boolean previousDiffActionEnabled) {
        this.previousDiffActionEnabled = previousDiffActionEnabled;
        for (DiffModelChangedListener l : diffModelChangedListenerList) {
            l.previousDiffActionEnablingChanged();
        }
    }
}
