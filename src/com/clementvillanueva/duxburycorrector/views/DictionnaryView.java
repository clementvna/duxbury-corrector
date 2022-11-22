package com.clementvillanueva.duxburycorrector.views;

import com.clementvillanueva.duxburycorrector.controllers.Controller;
import com.clementvillanueva.duxburycorrector.models.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class DictionnaryView extends JPanel {

    private final JButton mDeleteButton;
    private final JButton mEraseButton;

    private final JTable mAbregeTable;
    private final JTable mIntegralTable;
    private final JTable mSyllabeTable;

    public static int CURRENT_INDEX;

    public DictionnaryView(Controller mController) {
        super();

        JButton addButton = new JButton("Ajouter");
        addButton.setName("dictionnary_add");
        addButton.addActionListener(mController);

        mDeleteButton = new JButton("Supprimer");
        mDeleteButton.setName("dictionnary_delete");
        mDeleteButton.addActionListener(mController);

        mEraseButton = new JButton("Effacer le dictionnaire");
        mEraseButton.setName("dictionnary_erase");
        mEraseButton.addActionListener(mController);

        JButton helpButton = new JButton("Aide");
        helpButton.setName("dictionnary_help");
        helpButton.addActionListener(mController);

        JButton backButton = new JButton("Retour");
        backButton.setName("dictionnary_back");
        backButton.addActionListener(mController);

        String firstColumTitle = "Erreur";
        String secondColumTitle = "Correction";

        mAbregeTable = new Table();
        mAbregeTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(firstColumTitle);
        mAbregeTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(secondColumTitle);
        mAbregeTable.setGridColor(Color.CYAN);

        mIntegralTable = new Table();
        mIntegralTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(firstColumTitle);
        mIntegralTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(secondColumTitle);
        mIntegralTable.setGridColor(Color.CYAN);

        mSyllabeTable = new Table();
        mSyllabeTable.getTableHeader().getColumnModel().getColumn(0).setHeaderValue(firstColumTitle);
        mSyllabeTable.getTableHeader().getColumnModel().getColumn(1).setHeaderValue(secondColumTitle);
        mSyllabeTable.setGridColor(Color.CYAN);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(950, 545));
        tabbedPane.addChangeListener(e -> CURRENT_INDEX = ((JTabbedPane) e.getSource()).getSelectedIndex());
        tabbedPane.add(new JScrollPane(mAbregeTable));
        tabbedPane.add(new JScrollPane(mIntegralTable));
        tabbedPane.add(new JScrollPane(mSyllabeTable));
        tabbedPane.setTitleAt(0, "Erreurs d'abrégé");
        tabbedPane.setTitleAt(1, "Erreurs d'intégral");
        tabbedPane.setTitleAt(2, "Erreurs de syllable");
        tabbedPane.setSelectedIndex(0);

        GroupLayout layout = new GroupLayout(this);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mDeleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mEraseButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(100)
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addGap(50)
                                .addComponent(helpButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(addButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(mDeleteButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(mEraseButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(helpButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))
        );
        layout.setAutoCreateContainerGaps(true);
        setLayout(layout);
        updateAbregeTable();
        updateIntegralTable();
        updateSyllabeTable();
        setButtonsState();
    }

    public void updateAbregeTable() {
        for (int i = 0; i < mAbregeTable.getRowCount(); i++)
            for (int j = 0; j < mAbregeTable.getColumnCount(); j++)
                mAbregeTable.setValueAt("", i, j);
        ArrayList<StringPair> res = DatabaseHandler.getContent(DatabaseHandler.CONTRACTED_TABLE_ID);
        for (int i = 0; i < res.size(); i++) {
            mAbregeTable.setValueAt(res.get(i).getFirst(), i, 0);
            mAbregeTable.setValueAt(res.get(i).getSecond(), i, 1);
        }
        ((AbstractTableModel) mAbregeTable.getModel()).fireTableDataChanged();
    }

    public void updateIntegralTable() {
        for (int i = 0; i < mIntegralTable.getRowCount(); i++)
            for (int j = 0; j < mIntegralTable.getColumnCount(); j++)
                mIntegralTable.setValueAt("", i, j);
        ArrayList<StringPair> res = DatabaseHandler.getContent(DatabaseHandler.INTEGRAL_TABLE_ID);
        for (int i = 0; i < res.size(); i++) {
            mIntegralTable.setValueAt(res.get(i).getFirst(), i, 0);
            mIntegralTable.setValueAt(res.get(i).getSecond(), i, 1);
        }
        ((AbstractTableModel) mAbregeTable.getModel()).fireTableDataChanged();
    }

    public void updateSyllabeTable() {
        for (int i = 0; i < mSyllabeTable.getRowCount(); i++)
            for (int j = 0; j < mSyllabeTable.getColumnCount(); j++)
                mSyllabeTable.setValueAt("", i, j);
        ArrayList<StringPair> res = DatabaseHandler.getContent(DatabaseHandler.SYLLABLE_TABLE_ID);
        for (int i = 0; i < res.size(); i++) {
            mSyllabeTable.setValueAt(res.get(i).getFirst(), i, 0);
            mSyllabeTable.setValueAt(res.get(i).getSecond(), i, 1);
        }
        ((AbstractTableModel) mSyllabeTable.getModel()).fireTableDataChanged();
    }

    public void setButtonsState() {
        boolean check = true;
        if (DatabaseHandler.isEmpty(CURRENT_INDEX))
            check = false;
        mDeleteButton.setEnabled(check);
        mEraseButton.setEnabled(check);
    }

    public String getAbregeTableValueAt(int row, int column) {
        return (String) mAbregeTable.getValueAt(row, column);
    }

    public String getIntegralTableValueAt(int row, int column) {
        return (String) mIntegralTable.getValueAt(row, column);
    }

    public String getSyllabeTableValueAt(int row, int column) {
        return (String) mSyllabeTable.getValueAt(row, column);
    }

    public int[] getAbregeTableSelectedRows() {
        return mAbregeTable.getSelectedRows();
    }

    public int[] getIntegralTableSelectedRows() {
        return mIntegralTable.getSelectedRows();
    }

    public int[] getSyllabeTableSelectedRows() {
        return mSyllabeTable.getSelectedRows();
    }

    private class Table extends JTable {

        public Table() {
            super(30, 2);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }

    }

}
