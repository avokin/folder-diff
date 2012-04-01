package ru.avokin.uidiff.mainframe.view;

import ru.avokin.swing.hoverbutton.HoverIconButton;

import javax.swing.*;
import java.awt.*;

/**
 * User: Andrey Vokin
 * Date: 17.09.2010
 */
class TabComponent extends JPanel {

    public TabComponent(final JTabbedPane pane, MainFrame.CloseAction closeAction, Icon icon) {
        super(new FlowLayout(FlowLayout.LEFT));

        JLabel label = new JLabel() {
            public String getText() {
                int i = pane.indexOfTabComponent(TabComponent.this);
                if (i != -1) {
                    return pane.getTitleAt(i);
                }
                return null;
            }
        };
        if (icon != null) {
            label.setIcon(icon);
        }
        setOpaque(false);

        add(label);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        if (closeAction != null) {
            JButton button = new HoverIconButton(closeAction);
            add(button);
        }
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }
}
