package ru.avokin.uidiff.diff.filediff.view;

import ru.avokin.settings.ColorManager;
import ru.avokin.swing.codeviewer.model.LineNumberPosition;
import ru.avokin.swing.codeviewer.view.CodeViewerPanel;
import ru.avokin.swing.widehighlighting.WidePainter;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeBlock;
import ru.avokin.uidiff.diff.filediff.model.FileDiffCodeViewerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Andrey Vokin
 * Date: 18.08.2010
 */
public class FileDiffCodeViewerPanel extends CodeViewerPanel {

    private static final int SCROLL_STEP = 5;

    private final Map<FileDiffCodeBlock, WidePainter> startLineToBlockPainterEditorMap;

    private final Map<FileDiffCodeBlock, WidePainter> startLineToBlockPainterNumberMap;

    public FileDiffCodeViewerPanel(FileDiffCodeViewerModel model) {
        super(model);
        startLineToBlockPainterEditorMap = new HashMap<FileDiffCodeBlock, WidePainter>();
        startLineToBlockPainterNumberMap = new HashMap<FileDiffCodeBlock, WidePainter>();

        bindKeyActions();
    }

    public void addHighlighter(FileDiffCodeBlock cb, Color c) {
        WidePainter wpEditor =  addHighlighter(editor, getModel().getLinePositionList(), cb, c, ColorManager.highlightBorder);
        startLineToBlockPainterEditorMap.put(cb, wpEditor);

        WidePainter wpNumber = addHighlighter(numbers, getModel().getLineNumbersLinePositionList(), cb, c, ColorManager.highlightBorder);
        startLineToBlockPainterNumberMap.put(cb, wpNumber);
    }

    protected void bindKeyActions() {
        AbstractAction moveRightAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int value = getHorizontalScrollBar().getValue();
                int move = getModel().getLineNumberPosition() == LineNumberPosition.left ? -SCROLL_STEP : SCROLL_STEP;
                getHorizontalScrollBar().setValue(value + move);
            }
        };

        AbstractAction moveLeftAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int value = getHorizontalScrollBar().getValue();
                int move = getModel().getLineNumberPosition() == LineNumberPosition.left ? SCROLL_STEP : -SCROLL_STEP;
                getHorizontalScrollBar().setValue(value + move);
            }
        };

        AbstractAction moveUpAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int value = getVerticalScrollBar().getValue();
                getVerticalScrollBar().setValue(value - SCROLL_STEP);
            }
        };

        AbstractAction moveDownAction = new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                int value = getVerticalScrollBar().getValue();
                getVerticalScrollBar().setValue(value + SCROLL_STEP);
            }
        };

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "moveRightAction");
        getActionMap().put("moveRightAction", moveRightAction);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "moveLeftAction");
        getActionMap().put("moveLeftAction", moveLeftAction);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveUpAction");
        getActionMap().put("moveUpAction", moveUpAction);

        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveDownAction");
        getActionMap().put("moveDownAction", moveDownAction);
    }

    public void selectFileDiffCodeBlock(FileDiffCodeBlock cb, boolean withScrolling) {
        FileDiffCodeViewerModel model = (FileDiffCodeViewerModel) getModel();
        if (model.getSelectedCodeBlock() != null) {
            WidePainter wpEditor = startLineToBlockPainterEditorMap.get(model.getSelectedCodeBlock());
            WidePainter wpNumber = startLineToBlockPainterNumberMap.get(model.getSelectedCodeBlock());

            wpEditor.setBorderColor(ColorManager.highlightBorder);
            wpNumber.setBorderColor(ColorManager.highlightBorder);
        }
        model.setSelectedCodeBlock(cb);
        
        WidePainter wpEditor = startLineToBlockPainterEditorMap.get(cb);
        WidePainter wpNumber = startLineToBlockPainterNumberMap.get(cb);

        wpEditor.setBorderColor(ColorManager.selectedBorder);
        wpNumber.setBorderColor(ColorManager.selectedBorder);

        if (withScrolling) {
            super.scrollToLine(cb.getStartLine());
        }
    }
}
