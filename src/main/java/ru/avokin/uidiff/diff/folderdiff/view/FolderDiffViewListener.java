package ru.avokin.uidiff.diff.folderdiff.view;

import ru.avokin.uidiff.diff.common.view.DiffViewListener;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public interface FolderDiffViewListener extends DiffViewListener {

    void compare();

    void cancel();
}
