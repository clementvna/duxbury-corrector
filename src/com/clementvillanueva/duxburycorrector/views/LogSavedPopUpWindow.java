package com.clementvillanueva.duxburycorrector.views;

import javax.swing.*;
import java.awt.*;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class LogSavedPopUpWindow extends JFrame {

    public LogSavedPopUpWindow(boolean success) {
        super("Enregistrement du rapport");

        String labelText;
        if (success)
            labelText =
                "<html>" +
                    "<body>" +
                        "Le rapport a été enregistré avec succès." +
                    "</body>" +
                "</html>";
        else
            labelText =
                "<html>" +
                    "<body>" +
                        "Une erreur est survenue lors de l'enregistrement du fichier." +
                    "</body>" +
                    "</html>";

        JLabel label = new JLabel(labelText, SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(300, 80));

        getContentPane().add(label, BorderLayout.CENTER);
        setSize(280, 100);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

}
