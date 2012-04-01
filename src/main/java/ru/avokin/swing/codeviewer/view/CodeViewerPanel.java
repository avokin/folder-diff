package ru.avokin.swing.codeviewer.view;

import ru.avokin.swing.codeviewer.model.LinePosition;

import ru.avokin.filediff.CodeBlock;
import ru.avokin.settings.ColorManager;
import ru.avokin.settings.FontManager;
import ru.avokin.swing.codeviewer.model.CodeViewerModel;
import ru.avokin.swing.codeviewer.model.LineNumberPosition;
import ru.avokin.swing.utils.UiUtils;
import ru.avokin.swing.widehighlighting.WideHighlighterPainter;
import ru.avokin.swing.widehighlighting.WideLinePainter;
import ru.avokin.swing.widehighlighting.WidePainter;
import ru.avokin.utils.StringUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 23.07.2010
 */
public class CodeViewerPanel extends JPanel {

    private final CodeViewerModel model;

    protected final CodeViewerPane editor;

    protected final LineNumbersPane numbers;

    private final JScrollPane scrollPane;

    public CodeViewerPanel(CodeViewerModel model) {
        super(new GridBagLayout());
        this.model = model;

        editor = createViewer(model);
        scrollPane = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        if (model.getLineNumberPosition() == LineNumberPosition.left) {
            scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }

        numbers = createLineNumbers(model);

        scrollPane.setRowHeaderView(numbers);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        add(scrollPane, UiUtils.createWideGridBagConstraints(0, 0, 1, UiUtils.DEFAULT_INSET, UiUtils.DEFAULT_INSET));
        setBorder(BorderFactory.createEmptyBorder());
        setMinimumSize(new Dimension(getWidth(), getHeight()));
    }

    protected CodeViewerPane createViewer(CodeViewerModel model) {
        CodeViewerPane result = new CodeViewerPane();
        result.setSelectedTextColor(Color.white);
        CodeViewerEditorKit kit = new CodeViewerEditorKit();
        result.setEditorKit(kit);
        result.setDocument(model);
        result.setSelectionColor(ColorManager.selectionColor);

        result.setFont(FontManager.getFont());

        result.setEditable(false);
        result.setBorder(BorderFactory.createEmptyBorder());
        result.setText(model.getText());
        result.setCaretPosition(0);

        return result;
    }

    protected LineNumbersPane createLineNumbers(CodeViewerModel model) {
        String maxValueRepresentation = Integer.toString(model.getLineCount());
        StringBuilder numberText = new StringBuilder();
        for (int i = 1; i <= model.getLineCount(); i++) {
            String lineNumber = StringUtils.createAlignedLineNumber(i, maxValueRepresentation);
            numberText.append(lineNumber).append(" \n");
        }

        java.util.List<LinePosition> lineNumbersLinePositionList = StringUtils.prepareLinePositions(numberText.toString());
        getModel().setLineNumbersLinePositionList(lineNumbersLinePositionList);

        LineNumbersPane result = new LineNumbersPane(model.getLineNumberPosition());
        result.setEnabled(false);
        result.setFocusable(false);
        result.setText(numberText.toString());
        result.setFont(FontManager.getFont());
        result.setDisabledTextColor(ColorManager.lineNumber);
        result.setSelectionColor(ColorManager.selectionColor);
        result.setBorder(BorderFactory.createEmptyBorder());

        return result;
    }

    public FontMetrics getFontMetrics() {
        return editor.getFontMetrics(editor.getFont());
    }

    public JScrollBar getVerticalScrollBar() {
        return scrollPane.getVerticalScrollBar();
    }

    public JScrollBar getHorizontalScrollBar() {
        return scrollPane.getHorizontalScrollBar();
    }

    protected void scrollToLine(int lineNumber) {
        int scrollValue = (lineNumber - 1) * getFontMetrics().getHeight() - getHeight() /  2;
        getVerticalScrollBar().setValue(scrollValue);
    }

    protected WidePainter addHighlighter(JEditorPane editor, java.util.List<LinePosition> linePositionList,CodeBlock cb,
                                         Color backgroundColor, Color borderColor) {
        LinePosition lpTop = linePositionList.get(Math.min(linePositionList.size() - 1, cb.getStartLine()));
        LinePosition lpBottom = linePositionList.get(cb.getEndLine());

        WidePainter painter;
        if (cb.getStartLine() <= cb.getEndLine()) {
            painter = new WideHighlighterPainter(backgroundColor, borderColor, lpTop.getLeft(), lpBottom.getLeft());
        } else {
            boolean downLine = linePositionList.size() > cb.getStartLine();
            painter = new WideLinePainter(borderColor, downLine);
            lpBottom = lpTop;
        }
        try {
            editor.getHighlighter().addHighlight(lpTop.getLeft(), lpBottom.getRight() + 1, painter);
        } catch (BadLocationException ignored) {
        }

        return painter;
    }

    protected CodeViewerModel getModel() {
        return model;
    }
}