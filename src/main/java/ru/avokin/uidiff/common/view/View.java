package ru.avokin.uidiff.common.view;

import ru.avokin.uidiff.common.model.AbstractModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public abstract class View extends JPanel {

    protected final List<AbstractViewListener> viewListenerList;

    protected final AbstractModel model;

    protected View(AbstractModel model) {
        this.model = model;
        viewListenerList = new ArrayList<AbstractViewListener>();
    }

    public void addViewListener(AbstractViewListener vl) {
        viewListenerList.add(vl);
    }

    public void initActions() {
    }

    public void performAdjusting() {}
}
