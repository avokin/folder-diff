package ru.avokin.uidiff.common.controller;

import ru.avokin.uidiff.common.model.AbstractModel;
import ru.avokin.uidiff.common.view.View;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;

/**
 * User: Andrey Vokin
 * Date: 14.09.2010
 */
public abstract class AbstractController {

    protected final ViewManager viewManager;

    protected View view;

    protected AbstractModel model;

    protected AbstractController(ViewManager viewManager) {
        this.viewManager = viewManager;
    }
}
