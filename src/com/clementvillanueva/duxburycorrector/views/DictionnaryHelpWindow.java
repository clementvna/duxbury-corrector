package com.clementvillanueva.duxburycorrector.views;

import com.clementvillanueva.duxburycorrector.controllers.Controller;

import javax.swing.*;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class DictionnaryHelpWindow extends JFrame {

    public DictionnaryHelpWindow(Controller mController) {
        super("DBT Corrector - Aide du dictionnaire");

        JLabel helpLabel = new JLabel(
                "<html>" +
                        "<body>" +
                        "<h2>Manipulation du dictionnaire:</h2>" +
                        "<ul>" +
                        "<li>" +
                        "Le dictionnaire est divisé en trois tableaux: le tableau \"Erreurs d'abrégé\"<br /> contient la liste " +
                        "des mots pouvant être abrégés, complétés par <br />leur écriture intégrale, le tableau " +
                        "\"Erreurs d'intégral\" contient les erreurs<br /> commises par Duxbury lors de la transcription d'un " +
                        "mot intégral qui ne doit pas <br />être abrégé et le tableau \"Erreurs de syllable\" recense toutes " +
                        "les syllable pouvant <br />causer des erreurs de transcription (comme \"oe\" qui serait abrégé en \"œ\")." +
                        "</li>" +
                        "<br />" +
                        "<li>" +
                        "Les opération disponible sur les différent dictionnaires sont l'ajout <br />de mots et d'erreurs, leur " +
                        "suppression avec demande de confirmation <br />et l'effacement complet d'un dictionnaire. <br />Notez que cette " +
                        "dernière opération est irréversible, soyez sûr de <br />choisir la bonne option dans la fenêtre de confirmation." +
                        "</li>" +
                        "</ul>" +
                        "</body>" +
                        "</html>"
        );

        JButton closeButton = new JButton("Fermer");
        closeButton.setName("dictionnary_help_close");
        closeButton.addActionListener(mController);

        GroupLayout layout = new GroupLayout(getContentPane());
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(helpLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addComponent(closeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(helpLabel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(closeButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
        );
        layout.setAutoCreateContainerGaps(true);

        getContentPane().setLayout(layout);
        setSize(540, 348);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
