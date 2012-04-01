package ru.avokin.uidiff.common.view.actions.impl;

import ru.avokin.uidiff.common.view.actions.ActionManager;
import ru.avokin.uidiff.common.view.actions.BaseAction;

import java.util.*;

/**
 * User: Andrey Vokin
 * Date: 14.09.2010
 */
public class ActionManagerImpl implements ActionManager {

    private final Map<String, BaseAction> actionMap;

    private final List<BaseAction> actionList;

    public ActionManagerImpl() {
        actionMap = new HashMap<String, BaseAction>();
        actionList = new ArrayList<BaseAction>();
    }

    public void registerAction(BaseAction action) {
        actionMap.put(action.getName(), action);
        actionList.add(action);
    }

    public BaseAction getAction(String actionName) {
        return actionMap.get(actionName);
    }

    public List<BaseAction> getAllActions() {
        return Collections.unmodifiableList(actionList);
    }
}
