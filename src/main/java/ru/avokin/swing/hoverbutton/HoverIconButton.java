package ru.avokin.swing.hoverbutton;

import ru.avokin.uidiff.common.view.actions.BaseAction;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User: Andrey Vokin
 * Date: 17.09.2010
 */
public class HoverIconButton extends JButton {

    private final Icon icon;

    private final Icon hoverIcon;

    public HoverIconButton(BaseAction action) {
        super(action);
        icon = (Icon) action.getValue(Action.SMALL_ICON);
        hoverIcon = (Icon) action.getValue(BaseAction.HOVER_ICON);
        setBorder(BorderFactory.createEmptyBorder());

        setFocusable(false);
        setOpaque(false);
        MouseListenerImpl mouseListener = new MouseListenerImpl();
        this.addMouseListener(mouseListener);
    }

    private class MouseListenerImpl implements MouseListener {

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
            if (isEnabled()) {
                setIcon(hoverIcon);
            }
        }

        public void mouseExited(MouseEvent e) {
           setIcon(icon);
        }
    }
}
