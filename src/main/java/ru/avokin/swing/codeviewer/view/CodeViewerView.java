package ru.avokin.swing.codeviewer.view;

import ru.avokin.highlighting.HighlightedCodeBlock;
import ru.avokin.swing.codeviewer.model.CodeViewerModel;

import javax.swing.text.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/**
 * User: Andrey Vokin
 * Date: 23.07.2010
 */
class CodeViewerView extends WrappedPlainView {

    private final Map desktopHints;

    public CodeViewerView(Element elem) {
        super(elem);

        Toolkit tk = Toolkit.getDefaultToolkit();
        desktopHints = (Map)(tk.getDesktopProperty("awt.font.desktophints"));
    }
    
    protected java.util.List<HighlightedCodeBlock> getBlocksInDiapason(java.util.List<HighlightedCodeBlock> lexedBlocks, int p0, int p1) {

        int leftArrayPos = Collections.binarySearch(lexedBlocks, new HighlightedCodeBlock(null, p0, p0),
                CodeBlockComparator.getInstance());
        int rightArrayPos = Collections.binarySearch(lexedBlocks, new HighlightedCodeBlock(null, p1, p1),
                CodeBlockComparator.getInstance());

        java.util.List<HighlightedCodeBlock> result = new ArrayList<HighlightedCodeBlock>();
        for (int i = leftArrayPos; i <= rightArrayPos; i++) {
            result.add(lexedBlocks.get(i));
        }

        if (result.size() == 0) {
            result.add(new HighlightedCodeBlock(Color.black, p0, p1));
            return result;
        }

        return result;
    }

    @Override
    protected int drawUnselectedText(Graphics g, int x, int y, int p0, int p1) throws BadLocationException {
        if (desktopHints != null) {
            ((Graphics2D) g).addRenderingHints(desktopHints);
        }

        Document doc = getDocument();
        // By the last symbol in segment I mean last included.
        p1--;
        if (p0 >= doc.getLength()) {
            p0--;
        }
        
        java.util.List<HighlightedCodeBlock> highlightedCodeBlockList;
        if (doc instanceof CodeViewerModel) {
            CodeViewerModel model = (CodeViewerModel) doc;
            highlightedCodeBlockList = model.getHighlightedCodeBlockList();
        } else {
            highlightedCodeBlockList = new ArrayList<HighlightedCodeBlock>();
            highlightedCodeBlockList.add(new HighlightedCodeBlock(Color.black, p0, p1));
        }
        highlightedCodeBlockList = getBlocksInDiapason(highlightedCodeBlockList, p0, p1);


        Color oldColor = g.getColor();
        for (HighlightedCodeBlock block : highlightedCodeBlockList) {
            Color c = block.getColor();
            g.setColor(c);
            Segment segment = getLineBuffer();
            int start = Math.max(block.getStart(), p0);
            int end = Math.min(block.getEnd(), p1);
            doc.getText(start, end - start + 1, segment);
            x = Utilities.drawTabbedText(segment, x, y, g, this, 0);
        }
        g.setColor(oldColor);
        return x;
    }
}
