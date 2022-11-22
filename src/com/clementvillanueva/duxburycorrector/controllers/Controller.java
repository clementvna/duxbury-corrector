package com.clementvillanueva.duxburycorrector.controllers;

import com.clementvillanueva.duxburycorrector.models.*;
import com.clementvillanueva.duxburycorrector.views.*;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class Controller implements ActionListener {

    /**
     * Main duxburycorrector window, works as a JPanel container, extends JFrame
     */
    private final Window mWindow;

    /**
     * Correction view that should be in the main window when duxburycorrector starts, extends JPanel
     */
    private final CorrectionView mCorrectionView;

    /**
     * Dictionnary view that replaces the correction view when user accesses dictionnary, extends JPanel
     */
    private DictionnaryView mDictionnaryView;

    /**
     * Other windows that need to be accessed by the controller, depending on the action
     */
    private DictionnaryDeletePromptWindow mDictionnaryDeletePromptWindow;
    private DictionnaryErasePromptWindow mDictionnaryErasePromptWindow;
    private CorrectionHelpWindow mCorrectionHelpWindow;
    private DictionnaryHelpWindow mDictionnaryHelpWindow;
    private DictionnaryAddWindow mDictionnaryAddWindow;

    /**
     * File that should be processed
     */
    private static File mFile;

    /**
     * Main controller constructor
     * @param window the main JFrame in which ActionEvent have to be treated
     */
    public Controller(Window window) {
        mWindow = window;
        mCorrectionView = new CorrectionView(this);
        mCorrectionView.setContractedBoxEnabled(false);
        mCorrectionView.setAnalyzeButtonEnabled(false);
        mCorrectionView.setSaveLogButtonEnabled(false);
        mWindow.add(mCorrectionView);
        mWindow.validate();
        mWindow.repaint();
    }

    /**
     * Main controller for this duxburycorrector
     * @param event the ActionEvent to identify the action to perform
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (((JButton)event.getSource()).getName()) {
            case "correction_analyze":
                Corrector corrector = new Corrector(mFile);
                String messages = "";
                ArrayList<ArrayList<String>> lists = new ArrayList<>();
                if (mCorrectionView.isContractedBoxSelected())
                    lists.add(corrector.contracted());
                long start = System.currentTimeMillis();
                try {
                    lists.add(corrector.integral());
                    lists.add(corrector.syntax());
                    lists.add(corrector.syllable());
                    lists.add(corrector.italic());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                long stop = System.currentTimeMillis();
                long time = stop - start;
                for (ArrayList<String> list : lists)
                    for (String message : list)
                        messages += "\n" + message + "\n";
                if (messages.isEmpty())
                    messages = "\n\nAucune erreur trouvée.\n";
                messages += "\n";
                String header = "Créé le " + new SimpleDateFormat("dd/MM/yyyy").format(new Date()) + " à " +
                        new SimpleDateFormat("HH:mm").format(new Date()) + " par DBT Corrector\n";
                String file = "Fichier traité : " + mFile.getPath() + "\n";
                String footer = "\nAnalyse terminée. (" + time + "ms)";
                String separator = createSeparator(header.length(), file.length(), messages, footer.length());
                mCorrectionView.setLogTextAreaContent(header + file + separator + messages + separator + footer);
                if (!mCorrectionView.getLogTextAreaContent().isEmpty())
                    mCorrectionView.setSaveLogButtonEnabled(true);
                break;
            case "correction_openfile":
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".bff");
                    }

                    @Override
                    public String getDescription() {
                        return "Fichier braille format local (.bff)";
                    }
                });
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".brf");
                    }

                    @Override
                    public String getDescription() {
                        return "Fichier braille format US (.brf)";
                    }
                });
                fileChooser.addChoosableFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                    }

                    @Override
                    public String getDescription() {
                        return "Fichier texte (.txt)";
                    }
                });
                fileChooser.setCurrentDirectory(mFile);
                if (fileChooser.showOpenDialog(mCorrectionView) == JFileChooser.APPROVE_OPTION) {
                    mFile = fileChooser.getSelectedFile();
                    try {
                        mCorrectionView.readDocumentTextArea(new BufferedReader(new FileReader(mFile)));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    mCorrectionView.eraseLog();
                    mCorrectionView.setLineNumberVisible();
                }
                break;
            case "correction_savelog":
                boolean mLogSaved = false;
                try {
                    String path = mFile.getPath().replace(mFile.getName(), "");
                    String name = removeFileExtension(mFile.getName());
                    FileWriter fw = new FileWriter(path + "rapport_" + name + ".txt");
                    String text = mCorrectionView.getLogTextAreaContent();
                    BufferedReader mBufferedReader = new BufferedReader(new StringReader(text));
                    for (String line; (line = mBufferedReader.readLine()) != null;)
                        fw.write(line + System.lineSeparator());
                    fw.close();
                    mLogSaved = true;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                LogSavedPopUpWindow mLogSavedPopUpWindow = new LogSavedPopUpWindow(mLogSaved);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mLogSavedPopUpWindow.dispose();
                    }
                }, 2500);
                break;
            case "correction_dictionnary":
                if (mCorrectionHelpWindow != null)
                    mCorrectionHelpWindow.dispose();
                mDictionnaryView = new DictionnaryView(this);
                mWindow.setTitle("DBT Corrector - Dictionnaire d'erreurs");
                mWindow.getContentPane().removeAll();
                mWindow.add(mDictionnaryView);
                mWindow.validate();
                mWindow.repaint();
                break;
            case "correction_help":
                if (mCorrectionHelpWindow != null)
                    mCorrectionHelpWindow.dispose();
                mCorrectionHelpWindow = new CorrectionHelpWindow(this);
                break;
            case "correction_help_close":
                mCorrectionHelpWindow.dispose();
                break;
            case "dictionnary_back":
                if (mDictionnaryAddWindow != null)
                    mDictionnaryAddWindow.dispose();
                if (mDictionnaryDeletePromptWindow != null)
                    mDictionnaryDeletePromptWindow.dispose();
                if (mDictionnaryErasePromptWindow != null)
                    mDictionnaryErasePromptWindow.dispose();
                if (mDictionnaryHelpWindow != null)
                    mDictionnaryHelpWindow.dispose();
                mWindow.setTitle("DBT Corrector - Correction");
                mWindow.getContentPane().removeAll();
                mWindow.add(mCorrectionView);
                mWindow.validate();
                mWindow.repaint();
                break;
            case "dictionnary_add":
                if (mDictionnaryAddWindow != null)
                    mDictionnaryAddWindow.dispose();
                if (mDictionnaryHelpWindow != null)
                    mDictionnaryHelpWindow.dispose();
                mWindow.setEnabled(false);
                mDictionnaryAddWindow = new DictionnaryAddWindow(this);
                break;
            case "dictionnary_add_confirm":
                String firstFieldContent = mDictionnaryAddWindow.getFirstFieldContent();
                String secondFieldContent = mDictionnaryAddWindow.getSecondFieldContent();
                switch (DictionnaryView.CURRENT_INDEX) {
                    case 0:
                        DatabaseHandler.add(DatabaseHandler.CONTRACTED_TABLE_ID, new StringPair(firstFieldContent, secondFieldContent));
                        mDictionnaryView.updateAbregeTable();
                        break;
                    case 1:
                        DatabaseHandler.add(DatabaseHandler.INTEGRAL_TABLE_ID, new StringPair(firstFieldContent, secondFieldContent));
                        mDictionnaryView.updateIntegralTable();
                        break;
                    case 2:
                        DatabaseHandler.add(DatabaseHandler.SYLLABLE_TABLE_ID, new StringPair(firstFieldContent, secondFieldContent));
                        mDictionnaryView.updateSyllabeTable();
                        break;
                }
                mDictionnaryAddWindow.dispose();
                mDictionnaryView.setButtonsState();
                mWindow.setEnabled(true);
                break;
            case "dictionnary_add_cancel":
                mDictionnaryAddWindow.dispose();
                mWindow.setEnabled(true);
                break;
            case "dictionnary_delete":
                if (mDictionnaryDeletePromptWindow != null)
                    mDictionnaryDeletePromptWindow.dispose();
                if (mDictionnaryHelpWindow != null)
                    mDictionnaryHelpWindow.dispose();
                mWindow.setEnabled(false);
                mDictionnaryDeletePromptWindow = new DictionnaryDeletePromptWindow(this);
                break;
            case "dictionnary_delete_confirm":
                int[] rows;
                String first;
                String second;
                switch (DictionnaryView.CURRENT_INDEX) {
                    case 0:
                        rows = mDictionnaryView.getAbregeTableSelectedRows();
                        for (int row : rows) {
                            first = mDictionnaryView.getAbregeTableValueAt(row, 0);
                            second = mDictionnaryView.getAbregeTableValueAt(row, 1);
                            if (first != null && !first.isEmpty() && second != null && !second.isEmpty())
                                DatabaseHandler.remove(DatabaseHandler.CONTRACTED_TABLE_ID, new StringPair(first, second));
                        }
                        mDictionnaryView.updateAbregeTable();
                        break;
                    case 1:
                        rows = mDictionnaryView.getIntegralTableSelectedRows();
                        for (int row : rows) {
                            first = mDictionnaryView.getIntegralTableValueAt(row, 0);
                            second = mDictionnaryView.getIntegralTableValueAt(row, 1);
                            if (first != null && !first.isEmpty() && second != null && !second.isEmpty())
                                DatabaseHandler.remove(DatabaseHandler.INTEGRAL_TABLE_ID, new StringPair(first, second));
                        }
                        mDictionnaryView.updateIntegralTable();
                        break;
                    case 2:
                        rows = mDictionnaryView.getSyllabeTableSelectedRows();
                        for (int row : rows) {
                            first = mDictionnaryView.getSyllabeTableValueAt(row, 0);
                            second = mDictionnaryView.getSyllabeTableValueAt(row, 1);
                            if (first != null && !first.isEmpty() && second != null && !second.isEmpty())
                                DatabaseHandler.remove(DatabaseHandler.SYLLABLE_TABLE_ID, new StringPair(first, second));
                        }
                        mDictionnaryView.updateSyllabeTable();
                        break;
                }
                mDictionnaryDeletePromptWindow.dispose();
                mDictionnaryView.setButtonsState();
                mWindow.setEnabled(true);
                break;
            case "dictionnary_delete_cancel":
                mDictionnaryDeletePromptWindow.dispose();
                mWindow.setEnabled(true);
                break;
            case "dictionnary_erase":
                if (mDictionnaryErasePromptWindow != null)
                    mDictionnaryErasePromptWindow.dispose();
                mWindow.setEnabled(false);
                mDictionnaryErasePromptWindow = new DictionnaryErasePromptWindow(this);
                break;
            case "dictionnary_erase_confirm":
                switch (DictionnaryView.CURRENT_INDEX) {
                    case 0:
                        DatabaseHandler.erase(DatabaseHandler.CONTRACTED_TABLE_ID);
                        mDictionnaryView.updateAbregeTable();
                        break;
                    case 1:
                        DatabaseHandler.erase(DatabaseHandler.INTEGRAL_TABLE_ID);
                        mDictionnaryView.updateIntegralTable();
                        break;
                    case 2:
                        DatabaseHandler.erase(DatabaseHandler.SYLLABLE_TABLE_ID);
                        mDictionnaryView.updateSyllabeTable();
                        break;
                }
                mDictionnaryErasePromptWindow.dispose();
                mDictionnaryView.setButtonsState();
                mWindow.setEnabled(true);
                break;
            case "dictionnary_erase_cancel":
                mDictionnaryErasePromptWindow.dispose();
                mWindow.setEnabled(true);
                break;
            case "dictionnary_help":
                if (mDictionnaryHelpWindow != null)
                    mDictionnaryHelpWindow.dispose();
                mDictionnaryHelpWindow = new DictionnaryHelpWindow(this);
                break;
            case "dictionnary_help_close":
                if (mDictionnaryHelpWindow != null)
                    mDictionnaryHelpWindow.dispose();
                break;
        }
    }

    /**
     * Creates a line of hyphens to separate log header and footer from messages, based on the max log line length
     * @param headerLength number of characters in the log header line
     * @param footerLength number of characters in the log footer line
     * @param messages number of characters of the longer line in log messages
     * @return a String representing a line of hyphens of length of the longer line in the log
     */
    private String createSeparator(int headerLength, int file, String messages, int footerLength) {
        String res = "";
        int n;
        if (headerLength > file)
            n = headerLength;
        else if (file > footerLength)
            n = file;
        else
            n = footerLength;
        try {
            BufferedReader reader = new BufferedReader(new StringReader(messages));
            for (String line; (line = reader.readLine()) != null;)
                if (line.length() > n)
                    n = line.length();
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < n; i++)
            res += '-';
        return res;
    }

    /**
     * Trims extension from filename (foo.extension)
     * @param s is the filename (foo.extension)
     * @return the filename without the extension (foo)
     */
    private String removeFileExtension(String s) {
        String separator = System.getProperty("file.separator");
        String filename;
        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;
        return filename.substring(0, extensionIndex);
    }

    /**
     * Used in the document text area to set the file on drag and drop
     * @param file is the file dropped
     */
    public static void setFile(File file) {
        mFile = file;
    }

}