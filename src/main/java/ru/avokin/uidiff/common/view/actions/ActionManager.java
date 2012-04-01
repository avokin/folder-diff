package ru.avokin.uidiff.common.view.actions;

import java.util.List;

/**
 * User: Andrey Vokin
 * Date: 14.09.2010
 */
public interface ActionManager {
    
    public void registerAction(BaseAction action);

    public BaseAction getAction(String actionName);

    public List<BaseAction> getAllActions();
}
