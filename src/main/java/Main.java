/*
 * %W% %E%
 *
 * Copyright (c) 1998 Sun Microsystems, Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Sun
 * Microsystems, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Sun.
 *
 * SUN MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. SUN SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */

import com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.avokin.highlighting.CodeHighlighterManager;
import ru.avokin.uidiff.diff.common.view.AbstractDiffSplitPane;
import ru.avokin.uidiff.mainframe.controller.MainFrameController;
import ru.avokin.uidiff.diff.filediff.view.FileDiffSplitPane;
import ru.avokin.uidiff.common.view.viewManager.ViewManager;

import javax.swing.*;
import java.awt.*;

public class Main {

    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("config.xml");

    private static final String BEAN_NAME_VIEW_FACTORY = "viewManager";

    private static final String BEAN_NAME_CODE_HIGHLIGHTER_MANAGER = "codeHighlighterManager";

    private static void initUI() {
        try {
            UIManager.setLookAndFeel(new WindowsClassicLookAndFeel());
        } catch (Exception e) {
            System.err.println("Couldn't use system look and feel.");
        }

        Toolkit.getDefaultToolkit().setDynamicLayout(true);
        UIManager.getDefaults().put(FileDiffSplitPane.UI_CLASS_ID, "ru.avokin.uidiff.diff.filediff.view.FileDiffSplitPaneUI");
        UIManager.getDefaults().put(AbstractDiffSplitPane.UI_CLASS_ID, "ru.avokin.uidiff.diff.filediff.view.FileDiffSplitPaneUI");
    }

    public static void main(String[] args) {
        initUI();

        ViewManager viewFactory = (ViewManager) applicationContext.getBean(BEAN_NAME_VIEW_FACTORY);
        CodeHighlighterManager codeHighlighterManager = (CodeHighlighterManager) applicationContext.getBean(BEAN_NAME_CODE_HIGHLIGHTER_MANAGER);

        MainFrameController mainController = new MainFrameController(viewFactory, codeHighlighterManager);
        mainController.start();
    }
}
