package com.example.jobflow.controllers.front.project;

import java.util.ArrayList;
import java.util.List;

public class BadWordDetector {
    private static List<String> badWords = new ArrayList<>();

    static {
        // Add some example bad words
        badWords.add("noya");
        badWords.add("bou6");
        badWords.add("noya");

        // Add more bad words as needed
    }

    public static boolean containsBadWord(String text) {
        for (String word : badWords) {
            if (text.toLowerCase().contains(word)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getBadWords() {
        return badWords;
    }
}
