package com.clementvillanueva.duxburycorrector.views;

import com.clementvillanueva.duxburycorrector.controllers.Controller;

import javax.swing.*;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class Window extends JFrame {

    public Window() {
        super("DBT Corrector - Correction");
        new Controller(this);
        setSize(980, 630);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

}
