package com.clementvillanueva.duxburycorrector.models;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

public class StringPair {

    /**
     * Represent the first String and the second String of the StringPair
     */
    private final String mFirst;
    private final String mSecond;

    /**
     * StringPair constructor
     * @param first is the first String of the StrinPair
     * @param second is the second String of the StringPair
     */
    public StringPair(String first, String second) {
        mFirst = first;
        mSecond = second;
    }

    /**
     * Access the first String of the StringPair
     * @return the first String of the StringPair
     */
    public String getFirst() {
        return mFirst;
    }

    /**
     * Access the second String of the StrinPair
     * @return the second String of the StringPair
     */
    public String getSecond() {
        return mSecond;
    }

    /**
     * Checks if a StringPair is equals to another Object
     * @param o is the Object to be compared
     * @return 'true' if the StringPair is equals to Object, else 'false'
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof StringPair) {
            StringPair sp = ((StringPair) o);
            return mFirst.equals(sp.getFirst()) && mSecond.equals(sp.getSecond());
        }
        return false;
    }

    /**
     * Gives a String representation of the StringPair
     * @return a String that is the reprensentation of the StringPair
     */
    @Override
    public String toString() {
        return "First: " + mFirst + "\nSecond: " + mSecond;
    }

}
