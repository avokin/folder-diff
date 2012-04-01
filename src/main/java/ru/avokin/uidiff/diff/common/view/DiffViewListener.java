package ru.avokin.uidiff.diff.common.view;

import ru.avokin.uidiff.common.view.AbstractViewListener;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public interface DiffViewListener extends AbstractViewListener {
    
    void nextDifference();

    void previousDifference();
}
