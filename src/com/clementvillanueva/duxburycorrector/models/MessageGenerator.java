package com.clementvillanueva.duxburycorrector.models;

/**
 * @author Clément Villanueva
 * @version 1.0.0
 */

abstract class MessageGenerator {

    /**
     * String table containing all error types
     */
    private static final String[] MISTAKE_TYPES = {
        "ITALIC_THREE_WORDS",
        "ITALIC_PUNCTUATION",
        "SYNTAX_HOUR12",
        "SYNTAX_HOUR24",
        "SYNTAX_DATE",
        "SYNTAX_EMAIL",
        "SYNTAX_PHONE",
        "SYNTAX_MOBILE",
        "SYNTAX_EURO",
        "SYNTAX_DOLLAR",
        "SYNTAX_MEASUREMENT",
        "WORD_ABREGE",
        "WORD_INTEGRAL",
        "WORD_SYLLABE"
    };

    /**
     * Error keys to be used by the corrector
     */
    public static final String ITALIC_THREE_WORDS_MISTAKE_KEY = "ITALIC_THREE_WORDS";
    public static final String ITALIC_PUNCTUATION_MISTAKE_KEY = "ITALIC_PUNCTUATION";
    public static final String SYNTAX_HOUR12_MISTAKE_KEY = "SYNTAX_HOUR12";
    public static final String SYNTAX_HOUR24_MISTAKE_KEY = "SYNTAX_HOUR24";
    public static final String SYNTAX_DATE_MISTAKE_KEY = "SYNTAX_DATE";
    public static final String SYNTAX_EMAIL_MISTAKE_KEY = "SYNTAX_EMAIL";
    public static final String SYNTAX_PHONE_MISTAKE_KEY = "SYNTAX_PHONE";
    public static final String SYNTAX_MOBILE_MISTAKE_KEY = "SYNTAX_MOBILE";
    public static final String SYNTAX_EURO_MISTAKE_KEY = "SYNTAX_EURO";
    public static final String SYNTAX_DOLLAR_MISTAKE_KEY = "SYNTAX_DOLLAR";
    public static final String SYNTAX_MEASUREMENT_MISTAKE_KEY = "SYNTAX_MEASUREMENT";
    public static final String WORD_ABREGE_MISTAKE_KEY = "WORD_ABREGE";
    public static final String WORD_INTEGRAL_MISTAKE_KEY = "WORD_INTEGRAL";
    public static final String WORD_SYLLABE_MISTAKE_KEY = "WORD_SYLLABE";

    /**
     * Checks if mistake type is valid
     * @param type is the mistake type to be checked
     * @return true if the mistake type is valid, false otherwise
     */
    private static boolean check(String type) {
        boolean check = false;
        for (String item : MISTAKE_TYPES)
            if (type.equals(item)) {
                check = true;
                break;
            }
        return check;
    }

    /**
     * Generates a message composed by the type, the line number, the problem and a correction suggestion
     * @param line is the line number where the mistake was found
     * @param type is the mistake type
     * @return the message
     * @throws IllegalArgumentException if the mistake type is not valid
     */
    public static String generate(int line, String type) throws IllegalArgumentException {
        if (!check(type))
            throw new IllegalArgumentException("Bad mistake type.");
        String message = "";
        switch (type) {
            case ITALIC_THREE_WORDS_MISTAKE_KEY:
                message = "Erreur d'italic trouvée (ligne " + line + "):\nProblème: le caractère " +
                        "d'italic a été trouvé avant plus de trois mots.\nSuggestion: Utiliser le caractère " +
                        "d'italic adapté pour marquer l'italic sur une phrase complète.";
                break;
            case ITALIC_PUNCTUATION_MISTAKE_KEY:
                message = "Erreur d'italic trouvée (ligne " + line + "):\nProblème: le caractère d' " +
                        "\"italic\" se trouve avant un caractère de ponctuation.\nSuggestion: enlever le " +
                        "caractère d'italic avant le caractère de ponctuation.";
                break;
        }
        return message;
    }

    /**
     * Generates a message composed by the type, the line number, the problem and a correction suggestion
     * @param line is the line number where the mistake was found
     * @param matched is the mistaken word that was found
     * @param type ui the mistake type
     * @return the message
     * @throws IllegalArgumentException if the mistake type is not valid
     */
    public static String generate(int line, String matched, String type) throws IllegalArgumentException {
        if (!check(type))
            throw new IllegalArgumentException("Bad mistake type.");
        String message = "";
        switch (type) {
            case SYNTAX_HOUR12_MISTAKE_KEY:
                matched = matched.replaceAll("`", "");
                matched = matched.replaceAll("\\s", "");
                message = (matched.contains(":")) ?
                        "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: l'heure au format 12 heures\"" +
                                matched + " à été coupée entre deux lignes.\nSuggestion: remplacer \"" + matched +
                                "\" par \"" + matched.replaceAll(":", "h") + "\" et la déplacer à la ligne suivante." :
                        "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: l'heure au format 12 heures\"" +
                                matched + "\" à été coupée entre deux lignes.\nSuggestion: déplacer \"" + matched +
                                "\" à la ligne suivante.";
                break;
            case SYNTAX_HOUR24_MISTAKE_KEY:
                matched = matched.replaceAll("`", "");
                matched = matched.replaceAll("\\s", "");
                message = (matched.contains(":")) ?
                        "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: l'heure au format 24 heures \"" +
                                matched + "\" à été coupée entres deux lignes.\nSuggestion: remplacer \"" + matched +
                                "\" par \"" + matched.replaceAll(":", "h") + "\" et la déplacer à la ligne suivante." :
                        "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: l'heure au format 24 heures \"" +
                                matched + "\" à été coupée entre deux lignes.\nSuggestion: déplacer \"" + matched +
                                "\" à la ligne suivante.";
                break;
            case SYNTAX_DATE_MISTAKE_KEY:
                matched = matched.replaceAll("`", "");
                matched = matched.replaceAll("'", "/");
                message = "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: la date \"" + matched +
                        "\" à été coupée entre deux lignes.\nSuggestion: déplacer \"" + matched +
                        "\" à la ligne suivante.";
                break;
            case SYNTAX_EMAIL_MISTAKE_KEY:
                message = "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: l'adresse email \"" +
                        matched + "\" à été coupée entre deux lignes.\nSuggestion: déplacer \"" + matched +
                        "\" à la ligne suivante.";
                break;
            case SYNTAX_PHONE_MISTAKE_KEY:
                message = "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: le numéro de " +
                        "téléphone fixe \"" + matched + "\" à été coupé entre deux lignes.\nSuggestion: déplacer " +
                        matched + "\" à la ligne suivante.";
                break;
            case SYNTAX_MOBILE_MISTAKE_KEY:
                message = "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: le numéro de " +
                        "téléphone mobile \"" + matched + "\" à été coupé entre deux lignes.\nSuggestion: déplacer \"" +
                        matched + "\" à la ligne suivante.";
                break;
            case SYNTAX_EURO_MISTAKE_KEY:
                message = "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: le montant en Euro \"" +
                        matched + "\" à été coupé entre deux lignes.\nSuggestion: déplacer \"" + matched +
                        "\" à la ligne suivante.";
                break;
            case SYNTAX_DOLLAR_MISTAKE_KEY:
                message = "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: le montant en Dollar \"" +
                        matched + "\" à été coupé entre deux lignes.\nSuggestion: déplacer \"" + matched +
                        "\" à la ligne suivante.";
                break;
            case SYNTAX_MEASUREMENT_MISTAKE_KEY:
                message = "Erreur de syntaxe trouvée (ligne " + line + "):\nProblème: la mesure \"" + matched +
                        "\" à été coupée entre deux lignes.\nSuggestion: déplacer \"" + matched +
                        "\" à la ligne suivante.";
                break;
        }
        return message;
    }

    /**
     * Generates a message composed by the type, the line number, the problem and a correction suggestion
     * @param line is the line number where the mistake was found
     * @param first is the misktaken word
     * @param second is the mistaken word correction
     * @param type us the mistake type
     * @return the message
     * @throws IllegalArgumentException if the mistake type is not valid
     */
    public static String generate(int line, String first, String second, String type) throws IllegalArgumentException {
        if (!check(type))
            throw new IllegalArgumentException("Bad mistake type.");
        String message = "";
        switch (type) {
            case WORD_INTEGRAL_MISTAKE_KEY:
                message = "Erreur d'abrégé trouvée (ligne " + line + "): \nProblème: le " +
                        "mot \"" + first + "\" peut être abrégé.\nSuggestion: remplacer \"" +
                        first + "\" par \"" + second + "\".";
                break;
            case WORD_ABREGE_MISTAKE_KEY:
                message = "Erreur de transcription trouvée (ligne " + line +
                        "): \nProblème: \"" + first + "\" ne doit pas être abrégé.\nSuggestion: remplacer \"" +
                        first + "\" par \"" + second + "\".";
                break;
            case WORD_SYLLABE_MISTAKE_KEY:
                message = "Source d'erreur trouvée (ligne \"" + line + "\"):\nAvertissement: " +
                        "la syllable \"" + first + "\" trouvée dans le mot \"" + second + "\" .\nSuggestion: " +
                        "vérifier que l'utilisation de \"" + first + "\" est correcte.";
                break;
        }
        return message;
    }

}