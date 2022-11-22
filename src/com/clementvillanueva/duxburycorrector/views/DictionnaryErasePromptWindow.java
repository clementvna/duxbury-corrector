package com.clementvillanueva.duxburycorrector.views;

import com.clementvillanueva.duxburycorrector.controllers.Controller;

import javax.swing.*;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class DictionnaryErasePromptWindow extends JFrame {

    public DictionnaryErasePromptWindow(Controller controller) {
        super("Effacer le dictionnaire");

        JLabel messageLabel = new JLabel("Souhaitez-vous vraiment effacer ce dictionnaire ?");

        JButton confirmButton = new JButton("Effacer");
        confirmButton.setName("dictionnary_erase_confirm");
        confirmButton.addActionListener(controller);

        JButton cancelButton = new JButton("Annuler");
        cancelButton.setName("dictionnary_erase_cancel");
        cancelButton.addActionListener(controller);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(confirmButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(messageLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(confirmButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setAutoCreateContainerGaps(true);

        getContentPane().setLayout(layout);
        setSize(312, 102);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
