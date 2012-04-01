package ru.avokin.uidiff.common.view.actions;

import ru.avokin.uidiff.common.view.View;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Andrey Vokin
 * Date: 14.09.2010
 */
public abstract class BaseAction<T> extends AbstractAction {

    public static final String HOVER_ICON = "hoverIcon";

    private final Map<View, List<T>> listeners;

    private ViewManager viewManager;

    private String name;

    protected BaseAction(ViewManager viewManager, String name, Icon icon) {
        super(name, icon);
        this.name = name;
        this.viewManager = viewManager;
        listeners = new HashMap<View, List<T>>();
    }

    protected BaseAction(String name, Icon icon, Icon hoverIcon) {
        super(name, icon);
        putValue(HOVER_ICON, hoverIcon);
        listeners = new HashMap<View, List<T>>();
    }

    public List<T> getListeners(View view) {
        List<T> result = listeners.get(view);
        if (result == null) {
            result = new ArrayList<T>();
            listeners.put(view, result);
        }

        return result;
    }

    public List<T> getListeners() {
        return getListeners(viewManager.getCurrentView());
    }

    public void addListener(View view, T listener) {
        List<T> controllerListeners = getListeners(view);
        controllerListeners.add(listener);
    }

    public String getName() {
        return name;
    }
}
