package ru.avokin.uidiff.mainframe.view;

import ru.avokin.swing.hoverbutton.HoverButton;
import ru.avokin.swing.utils.UiUtils;
import ru.avokin.uidiff.common.view.actions.*;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

/**
 * User: Andrey Vokin
 * Date: 10.09.2010
 */
public class MainFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 1000;

    private static final int DEFAULT_HEIGHT = 600;

    private static final URL ICON_CLOSE_TAB_URL = MainFrame.class.getResource("/icons/close.png");

    private static final Icon ICON_CLOSE_TAB = new ImageIcon(ICON_CLOSE_TAB_URL);

    private static final URL HOVER_ICON_CLOSE_TAB_URL = MainFrame.class.getResource("/icons/hoverClose.png");

    private static final Icon HOVER_ICON_CLOSE_TAB = new ImageIcon(HOVER_ICON_CLOSE_TAB_URL);

    private static final URL ICON_FOLDER_DIFF_URL = MainFrame.class.getResource("/icons/folderDiff.png");

    private static final ImageIcon ICON_FOLDER_DIFF = new ImageIcon(ICON_FOLDER_DIFF_URL);

    private final JTabbedPane tabbedPane;

    private final ActionManager actionManager;

    private final ViewManager viewManager;

    private final CancelAction cancelAction;

    public MainFrame(ActionManager actionManager, ViewManager viewManager) {
        super("File and folder diff utility");
        this.actionManager = actionManager;
        this.viewManager = viewManager;

        createActions();

        cancelAction = (CancelAction) actionManager.getAction(CancelAction.NAME);

        setIconImage(ICON_FOLDER_DIFF.getImage());

        setLayout(new GridBagLayout());
        setJMenuBar(createMenu());

        JPanel toolbar = createToolbar();
        GridBagConstraints gbc = UiUtils.createGridBagConstraints(0, 0, UiUtils.DEFAULT_INSET, UiUtils.DEFAULT_INSET);
        gbc.anchor = GridBagConstraints.WEST;
        getContentPane().add(toolbar, gbc);

        tabbedPane = new JTabbedPane();
        tabbedPane.setFocusable(false);
        getContentPane().add(tabbedPane, UiUtils.createWideGridBagConstraints(0, 1, 1, 0, 0));
        tabbedPane.addChangeListener(new MainFrameChangeListener());

        DefaultKeyboardFocusManager dkfm = new DefaultKeyboardFocusManager();
        KeyboardFocusManager.setCurrentKeyboardFocusManager(dkfm);

        dkfm.addKeyEventDispatcher(new KeyEventDispatcher() {
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() ==  KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_TAB && (e.getModifiers() & KeyEvent.CTRL_MASK) == KeyEvent.CTRL_MASK) {
                        if ((e.getModifiers() & KeyEvent.SHIFT_MASK) == KeyEvent.SHIFT_MASK) {
                            if (tabbedPane.getSelectedIndex() > 0) {
                                tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() - 1);
                            }
                        } else {
                            if (tabbedPane.getSelectedIndex() < tabbedPane.getTabCount() - 1) {
                                tabbedPane.setSelectedIndex(tabbedPane.getSelectedIndex() + 1);
                            }
                        }
                        return true;
                    }

                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        if (cancelAction.isEnabled()) {
                            cancelAction.actionPerformed(null);
                        }
                    }
                }

                return false;
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    protected void createActions() {


        BaseAction nextDiffAction = new NextDiffAction(viewManager);
        actionManager.registerAction(nextDiffAction);

        BaseAction prevDiffAction = new PrevDiffAction(viewManager);
        actionManager.registerAction(prevDiffAction);

        BaseAction expandDiffAction = new ExpandDiffAction(viewManager);
        actionManager.registerAction(expandDiffAction);

        BaseAction expandAllAction = new ExpandAllAction(viewManager);
        actionManager.registerAction(expandAllAction);

        BaseAction collapseAllAction = new CollapseAllAction(viewManager);
        actionManager.registerAction(collapseAllAction);

        BaseAction compareAction = new CompareAction(viewManager);
        actionManager.registerAction(compareAction);

        BaseAction cancelAction = new CancelAction(viewManager);
        actionManager.registerAction(cancelAction);
    }

    public void addTab(String title, JPanel tab, Icon icon, boolean closable) {
        tabbedPane.add(title, tab);

        TabComponent tabComponent;
        if (closable) {
            CloseAction closeAction = new CloseAction();
            tabComponent = new TabComponent(tabbedPane, closeAction, icon);
            closeAction.setTabComponent(tabComponent);
        } else {
            tabComponent = new TabComponent(tabbedPane, null, icon);
        }
        tabbedPane.setTabComponentAt(tabbedPane.getTabCount() - 1, tabComponent);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }

    public Component getSelectedTab() {
        return tabbedPane.getSelectedComponent();
    }

    protected JPanel createToolbar() {
        JPanel result = new JPanel(new FlowLayout(FlowLayout.LEFT));

        java.util.List<BaseAction> allActions = actionManager.getAllActions();
        for (Action a : allActions) {
            HoverButton hb = new HoverButton(a);
            result.add(hb);
        }

        return result;
    }

    protected JMenuBar createMenu() {
        JMenuBar menu = new JMenuBar();

        JMenu mnuFile = new JMenu("File");
        menu.add(mnuFile);

        JMenuItem mnuExit = new JMenuItem("Exit");
        mnuFile.add(mnuExit);
        mnuExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        JMenu mnuActions = new JMenu("Actions");
        menu.add(mnuActions);

        java.util.List<BaseAction> allActions = actionManager.getAllActions();
        for (Action a : allActions) {
            JMenuItem mniCompareFolders = new JMenuItem(a);
            mnuActions.add(mniCompareFolders);
        }


        return menu;
    }

    protected void disableActions() {
        java.util.List<BaseAction> allActions = actionManager.getAllActions();
        for (Action action : allActions) {
            action.setEnabled(false);
        }
    }

    protected void activateTab() {
        disableActions();
        viewManager.getCurrentView().initActions();
    }

    class CloseAction extends BaseAction {

        private static final String NAME = "";

        private TabComponent tabComponent;

        protected CloseAction() {
            super(NAME, ICON_CLOSE_TAB, HOVER_ICON_CLOSE_TAB);
        }

        public void setTabComponent(TabComponent tabComponent) {
            this.tabComponent = tabComponent;
        }

        public void actionPerformed(ActionEvent e) {
            int i = tabbedPane.indexOfTabComponent(tabComponent);
            if (i != -1) {
                tabbedPane.remove(i);
            }
        }
    }

    private class MainFrameChangeListener implements ChangeListener {

        public void stateChanged(ChangeEvent e) {
            activateTab();
        }
    }
}
