package ru.avokin.uidiff.fs.common.view;

import javax.swing.plaf.basic.BasicTreeUI;
import java.awt.event.MouseEvent;

/**
 * User: Andrey Vokin
 * Date: 10.09.2010
 */
public class FsTreeUI extends BasicTreeUI {
    
    @Override
    protected boolean isToggleEvent(MouseEvent event) {
        return false;
    }
}
