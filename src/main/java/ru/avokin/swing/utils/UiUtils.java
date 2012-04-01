package ru.avokin.swing.utils;

import java.awt.*;


/**
 * User: Andrey Vokin
 * Date: 21.05.2010
 */
public class UiUtils {

    public static final int DEFAULT_INSET = 1;

    public static GridBagConstraints createGridBagConstraints(int gridx, int gridy, int leftInset, int rightInset) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.insets = new Insets(DEFAULT_INSET, leftInset, DEFAULT_INSET, rightInset);

        return gbc;
    }

    public static GridBagConstraints createWideGridBagConstraints(int gridX, int gridY, int gridWidth, int leftInset, int rightInset) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = gridX;
        gbc.gridy = gridY;
        gbc.gridwidth = gridWidth;

        gbc.insets = new Insets(DEFAULT_INSET, leftInset, DEFAULT_INSET, rightInset);

        return gbc;
    }
}
