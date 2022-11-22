package com.clementvillanueva.duxburycorrector.models;

import java.sql.*;
import java.util.ArrayList;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class DatabaseHandler {

    /**
     * Contains all tables names in the database
     */
    private static final String[] DATABASE_TABLES_NAMES = {"contracted", "integral", "syllable"};

    /**
     * Assign an id to database tables
     */
    public static final int CONTRACTED_TABLE_ID = 0;
    public static final int INTEGRAL_TABLE_ID = 1;
    public static final int SYLLABLE_TABLE_ID = 2;

    /**
     * Checks if the database id is correct
     * @param id is the id to check
     * @return true is the id is correct, false otherwise
     */
    private static boolean checkTableId(int id) {
        return (id >= CONTRACTED_TABLE_ID && id <= SYLLABLE_TABLE_ID);
    }

    /**
     * Creates the database with all the tables in DATABASE_TABLES_NAMES
     */
    public static void create() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement();
            for (String table : DATABASE_TABLES_NAMES) {
                String query = "CREATE TABLE IF NOT EXISTS " + table +
                        " (mistake TEXT UNIQUE NOT NULL, correction TEXT UNIQUE NOT NULL);";
                statement.executeUpdate(query);
            }
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Fetches the content of the table designated by an id
     * @param id identifies the database table in which the content should be fetched
     * @return a list containing all the tuples contained in the table
     */
    public static ArrayList<StringPair> getContent(int id) {
        if (!checkTableId(id))
            throw new IllegalArgumentException("Bad database table id.");
        ArrayList<StringPair> list = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement();
            String query = "SELECT mistake, correction FROM " + DATABASE_TABLES_NAMES[id] + " ORDER BY mistake ASC;";
            ResultSet set = statement.executeQuery(query);
            while (set.next())
                list.add(new StringPair(set.getString("mistake"), set.getString("correction")));
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    /**
     * Adds a mistaken word with its correction in a table designated by an id
     * @param id identifies the database table in which the word and its correction should be added
     * @param pair is the couple of String containing a mistaken word and its correction to add
     */
    public static void add(int id, StringPair pair) {
        if (!checkTableId(id))
            throw new IllegalArgumentException("Bad database table id.");
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement();
            String query = "INSERT INTO " + DATABASE_TABLES_NAMES[id] + " (mistake, correction) " +
                    "VALUES ('" + pair.getFirst() + "', '" + pair.getSecond() + "');";
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Removes a mistaken word with its correction in a table designated by an id
     * @param id identifies the database table in which the word and its correction should be removed
     * @param pair is the couple of String containing a mistaken word and its correction to remove
     */
    public static void remove(int id, StringPair pair) throws IllegalArgumentException {
        if (!checkTableId(id))
            throw new IllegalArgumentException("Bad database table id.");
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement();
            String query = "DELETE FROM " + DATABASE_TABLES_NAMES[id] + " WHERE mistake = '" + pair.getFirst() +
                    "' AND correction = '" + pair.getSecond() + "';";
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Erases a table designated by an id
     * @param id identifies the database table to be erased
     */
    public static void erase(int id) throws IllegalArgumentException {
        if (!checkTableId(id))
            throw new IllegalArgumentException("Bad database table id.");
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            Statement statement = connection.createStatement();
            String query = "DELETE FROM " + DATABASE_TABLES_NAMES[id] + ";";
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Checks if the database table designated by the id is empty
     * @param id identifies the database table in which the word and its correction should be removed
     * @return true if the database table is not empty, false otherwise
     */
    public static boolean isEmpty(int id) throws IllegalArgumentException {
        if (!checkTableId(id))
            throw new IllegalArgumentException("Bad database table id.");
        return getContent(id).isEmpty();
    }

}