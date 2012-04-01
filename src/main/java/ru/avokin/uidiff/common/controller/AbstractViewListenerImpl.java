package ru.avokin.uidiff.common.controller;

import ru.avokin.uidiff.common.view.AbstractViewListener;

/**
 * User: Andrey Vokin
 * Date: 03.10.2010
 */
public class AbstractViewListenerImpl implements AbstractViewListener {

    protected final AbstractController controller;

    protected AbstractViewListenerImpl(AbstractController controller) {
        this.controller = controller;
    }
}
