package ru.avokin.uidiff.diff.common.view;

import ru.avokin.settings.ColorManager;
import ru.avokin.swing.utils.UiUtils;
import ru.avokin.uidiff.common.view.AbstractViewListener;
import ru.avokin.uidiff.common.view.View;
import ru.avokin.uidiff.common.view.actions.ActionManager;
import ru.avokin.uidiff.common.view.actions.CurrentDifferenceMovedListener;
import ru.avokin.uidiff.common.view.actions.NextDiffAction;
import ru.avokin.uidiff.common.view.actions.PrevDiffAction;
import ru.avokin.uidiff.diff.common.model.AbstractDiffModel;
import ru.avokin.uidiff.diff.common.model.DiffModelChangedListener;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * User: Andrey Vokin
 * Date: 23.08.2010
 */
public abstract class AbstractDiffFrame extends View {

    private static final String SELECTED_DIFF_NUMBER_STATUS_MESSAGE = "Difference %s (from %s) selected";

    protected static final String DIFF_NUMBER_STATUS_MESSAGE = "%s differences found";

    protected final AbstractDiffModel model;

    private final AbstractDiffSplitPane diffSplitPane;

    private JLabel lblStatus;

    private final NextDiffAction nextDiffAction;

    private final PrevDiffAction prevDiffAction;

    protected AbstractDiffFrame(AbstractDiffModel model, ActionManager actionManager) throws HeadlessException {
        super(model);
        
        this.model = model;
        setLayout(new GridBagLayout());

        diffSplitPane = createSplitPane(model);
        add(diffSplitPane, UiUtils.createWideGridBagConstraints(0, 0, 1, 0, 0));
        //diffSplitPane.setDividerLocation((getWidth() - diffSplitPane.getDividerSize()) / 2);

        GridBagConstraints gbc = UiUtils.createWideGridBagConstraints(0, 1, 1, 0, 0);
        gbc.weighty = 0;
        JPanel legendaPanel = createLegendaPanel();
        add(legendaPanel, gbc);

        DiffComponentListener cl = new DiffComponentListener();
        addComponentListener(cl);

        DiffModelChangedListenerImpl diffModelChangedListener = new DiffModelChangedListenerImpl();
        model.addDiffModelChangedListener(diffModelChangedListener);

        gbc = UiUtils.createWideGridBagConstraints(0, 2, 1, 0, 0);
        gbc.weighty = 0;
        JPanel statusBarPanel = createStatusBar();
        add(statusBarPanel, gbc);

        nextDiffAction = (NextDiffAction) actionManager.getAction(NextDiffAction.NAME);
        prevDiffAction = (PrevDiffAction) actionManager.getAction(PrevDiffAction.NAME);
        CurrentDifferenceMovedListener currentDifferenceMovedListener = new CurrentDifferenceMovedListener() {
            public void CurrentDifferenceIncremented() {
                for (AbstractViewListener dvl : viewListenerList) {
                    ((DiffViewListener) dvl).nextDifference();
                }
            }

            public void CurrentDifferenceDecremented() {
                for (AbstractViewListener dvl : viewListenerList) {
                    ((DiffViewListener) dvl).previousDifference();
                }
            }
        };
        nextDiffAction.addListener(this, currentDifferenceMovedListener);
        prevDiffAction.addListener(this, currentDifferenceMovedListener);
    }

    protected void setStatusBarMessage(String text) {
        lblStatus.setText(text);
    }

    protected abstract AbstractDiffSplitPane createSplitPane(AbstractDiffModel model);

    protected JPanel createLegendaPanel() {
        JPanel result = new JPanel();

        Border border = BorderFactory.createLineBorder(Color.black, 1);

        JLabel lblChanged = new JLabel("Changed");
        JLabel lblChangedColor = new JLabel("         ");
        lblChangedColor.setOpaque(true);
        lblChangedColor.setBorder(border);
        lblChangedColor.setBackground(ColorManager.changed);

        JLabel lblDeleted = new JLabel("Deleted");

        JLabel lblDeletedColor = new JLabel("         ");
        lblDeletedColor.setOpaque(true);
        lblDeletedColor.setBorder(border);
        lblDeletedColor.setBackground(ColorManager.deleted);

        JLabel lblAdded = new JLabel("Added");

        JLabel lblAddedColor = new JLabel("         ");
        lblAddedColor.setOpaque(true);
        lblAddedColor.setBorder(border);
        lblAddedColor.setBackground(ColorManager.added);

        result.add(lblChangedColor);
        result.add(lblChanged);

        result.add(lblAddedColor);
        result.add(lblAdded);

        result.add(lblDeletedColor);
        result.add(lblDeleted);

        return result;
    }

    protected JPanel createStatusBar() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));
        result.setBorder(BorderFactory.createEtchedBorder());
        lblStatus = new JLabel(" ");
        result.add(lblStatus);
        return result;
    }

    protected void selectDifference(int number) {
        diffSplitPane.selectDifference(number);
        setStatusBarMessage(String.format(SELECTED_DIFF_NUMBER_STATUS_MESSAGE, number + 1, model.getDifferenceCount()));
    }

    @Override
    public void performAdjusting() {
        diffSplitPane.initAdjustmentListeners();
    }

    private class DiffComponentListener implements ComponentListener {

        public void componentResized(ComponentEvent e) {
            diffSplitPane.setDividerLocation(0.5);
        }

        public void componentMoved(ComponentEvent e) {
        }

        public void componentShown(ComponentEvent e) {
        }

        public void componentHidden(ComponentEvent e) {
        }
    }

    @Override
    public void initActions() {
        super.initActions();
        nextDiffAction.setEnabled(model.isNextDiffActionEnabled());
        prevDiffAction.setEnabled(model.isPreviousDiffActionEnabled());
    }

    class DiffModelChangedListenerImpl implements DiffModelChangedListener {

        public void CurrentDifferenceChanged(int number) {
            selectDifference(number);
        }

        public void nextDiffActionEnablingChanged() {
            nextDiffAction.setEnabled(model.isNextDiffActionEnabled());
        }

        public void previousDiffActionEnablingChanged() {
            prevDiffAction.setEnabled(model.isPreviousDiffActionEnabled());
        }
    }
}
