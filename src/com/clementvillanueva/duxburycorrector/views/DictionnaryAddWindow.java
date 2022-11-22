package com.clementvillanueva.duxburycorrector.views;

import com.clementvillanueva.duxburycorrector.controllers.Controller;

import javax.swing.*;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class DictionnaryAddWindow extends JFrame {

    private final JTextField mFirstField;
    private final JTextField mSecondField;

    public DictionnaryAddWindow(Controller controller) {
        super("Ajouter une erreur");

        mFirstField = new JTextField(20);
        mSecondField = new JTextField(20);

        JLabel firstFieldLabel = new JLabel();
        JLabel secondFieldLabel = new JLabel();

        firstFieldLabel.setText("Erreur");
        secondFieldLabel.setText("Correction");

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(controller);
        addButton.setName("dictionnary_add_confirm");

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(controller);
        cancelButton.setName("dictionnary_add_cancel");

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(firstFieldLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(mFirstField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(secondFieldLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(mSecondField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(firstFieldLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mFirstField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(secondFieldLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mSecondField, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setAutoCreateContainerGaps(true);

        getContentPane().setLayout(layout);
        setSize(253, 187);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public String getFirstFieldContent() {
        return mFirstField.getText();
    }

    public String getSecondFieldContent() {
        return mSecondField.getText();
    }

}