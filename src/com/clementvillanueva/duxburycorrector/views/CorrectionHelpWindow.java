package com.clementvillanueva.duxburycorrector.views;

import com.clementvillanueva.duxburycorrector.controllers.Controller;

import javax.swing.*;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class CorrectionHelpWindow extends JFrame {

    public CorrectionHelpWindow(Controller controller) {
        super("DBT Corrector - Aide à la correction");

        JLabel helpLabel = new JLabel(
                "<html>" +
                "<body>" +
                "<h2>Utilisation de la correction:</h2>" +
                "<ol>" +
                "<li>" +
                "Cliquez sur le bouton \"Ouvrir un fichier\": une fenêtre de l'explorateur de fichier s'ouvre." +
                "<br />Sélectionnez ensuite le fichier de votre choix.<br />Attention, il est possible de " +
                "choisir un fichier seulement si son format est supporté par DBT Corrector:<br /> les formats " +
                "supportés sont Braille codé US (.brf), Braille codé local (.bff) et Texte (.txt).<br />" +
                "Une fois votre sélection effectuée, cliquer sur le bouton \"Ouvrir\" de l'explorateur de fichier." +
                "<br />Si le fichier est volumineux, il se peut que le chargement prenne du temps, patientez." +
                "</li>" +
                "<br />" +
                "<li>" +
                "Une fois le texte affiché, vous remarquerez qu'il vous est désormais possible de cocher la case " +
                "\"Texte abrégé\"<br /> si vous souhaitez analyser un fichier rédigé en braille abrégé.<br />Si le fichier " +
                "est rédigé en braille intégral, veillez à ce que la case \"Texte abrégé\" ne soit pas cochée." +
                "</li>" +
                "<br />" +
                "<li>" +
                "Afin de procéder à l'analyse du texte, veuillez cliquer sur le bouton \"Analyser\".<br />" +
                "Notez que le temps de recherche est proportionnel au nombre d'entrées que vous avez ajouté " +
                "dans les différents<br /> dictionnaires.<br/>Néanmoins, le traitement est optimisé pour être le " +
                "plus rapide possible." +
                "</li>" +
                "<br />" +
                "<li>" +
                "Une fois l'analyse terminée, un rapport est affiché sur le panneau de droite.<br /> Le bouton " +
                "\"Enregistrer le rapport\" est désormais activé et <br />vous permet de sauvegarder le rapport " +
                "d'analyse dans un fichier texte afin de <br />conserver une tace du travail effectué sur ce document." +
                "ce fichier se situe dans le même dossier que le <br />document analysé et se nomme de la manière " +
                "suivante: rapport_nomdudocument.txt." +
                "</li>" +
                "<br />" +
                "<li>" +
                "Si vous le souhaitez, il vous est possible de traiter un nouveau document en effectuant à " +
                "nouveau<br /> les étapes précédentes." +
                "</li>" +
                "</ol>" +
                "</body>" +
                "</html>"
        );

        JButton closeButton = new JButton("Fermer");
        closeButton.setName("correction_help_close");
        closeButton.addActionListener(controller);

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
        setSize(725, 540);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

}
