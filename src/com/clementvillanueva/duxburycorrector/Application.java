package com.clementvillanueva.duxburycorrector;

import com.clementvillanueva.duxburycorrector.controllers.Controller;
import com.clementvillanueva.duxburycorrector.models.DatabaseHandler;
import com.clementvillanueva.duxburycorrector.views.Window;

import java.io.File;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

class Application {

    public static void main(String[] args) {
        System.setProperty("file.encoding", "UTF-8");
        if (!new File("database.db").exists())
            DatabaseHandler.create();
        new Controller(new Window());
    }

}