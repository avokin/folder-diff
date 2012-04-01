package ru.avokin.swing.hoverbutton;

import ru.avokin.settings.ColorManager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * User: Andrey Vokin
 * Date: 08.09.2010
 */
public class HoverButton extends JButton {

    private static final Dimension PREFERRED_SIZE = new Dimension(24, 24);

    private final Color notHoverColor = UIManager.getColor("control");

    private final Color hoverBorderColor = Color.black;

    private final Border hoverBorder = BorderFactory.createLineBorder(hoverBorderColor);

    private final Border notHoverBorder = BorderFactory.createLineBorder(notHoverColor);

    private MouseStatus status;

    public HoverButton(Action action) {
        super(action);
        setHideActionText(true);
        MouseListenerImpl mouseListener = new MouseListenerImpl();
        this.addMouseListener(mouseListener);
        setFocusable(false);
        setBorder(notHoverBorder);
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }

    private class MouseListenerImpl implements MouseListener {

        public void mouseClicked(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            if (isEnabled()) {
                status = MouseStatus.pressed;
                setBackground(ColorManager.pressedBackgroundColor);
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (status == MouseStatus.pressed) {
                status = MouseStatus.inside;
                setBackground(ColorManager.hoverBackgroundColor);
            }
        }

        public void mouseEntered(MouseEvent e) {
            if (isEnabled()) {
                status = MouseStatus.inside;
                setBorder(hoverBorder);
                setBackground(ColorManager.hoverBackgroundColor);
            }
        }

        public void mouseExited(MouseEvent e) {
            status = MouseStatus.outside;
            setBorder(notHoverBorder);
            setBackground(notHoverColor);
        }
    }

    enum MouseStatus {
        outside,
        inside,
        pressed
    }
}
