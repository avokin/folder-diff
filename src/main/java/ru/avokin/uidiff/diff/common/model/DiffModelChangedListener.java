package ru.avokin.uidiff.diff.common.model;

/**
 * User: Andrey Vokin
 * Date: 24.08.2010
 */
public interface DiffModelChangedListener {

    void CurrentDifferenceChanged(int number);

    void nextDiffActionEnablingChanged();

    void previousDiffActionEnablingChanged();
}
