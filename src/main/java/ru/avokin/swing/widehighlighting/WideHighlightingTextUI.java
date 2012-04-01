package ru.avokin.swing.widehighlighting;

import javax.swing.plaf.basic.BasicEditorPaneUI;
import javax.swing.text.Caret;

/**
 * User: Andrey Vokin
 * Date: 16.07.2010
 */
public class WideHighlightingTextUI extends BasicEditorPaneUI {
    
    @Override
     protected Caret createCaret() {
        return WideSelectionCaret.getInstance();
    }
}
