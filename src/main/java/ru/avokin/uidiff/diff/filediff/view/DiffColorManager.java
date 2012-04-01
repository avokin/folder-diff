package ru.avokin.uidiff.diff.filediff.view;

import ru.avokin.settings.ColorManager;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;

import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 02.10.2010
 */
class DiffColorManager {
    
    public static Color getColor(FileDiffCodeBlock cb) {
        Color result;
        switch (cb.getStatus()) {
            case added:
                result = ColorManager.added;
                break;
            case changed:
                result = ColorManager.changed;
                break;
            case deleted:
                result = ColorManager.deleted;
                break;
            default:
                throw new IllegalStateException("Unexpected diff status");
        }
        return result;
    }
}
