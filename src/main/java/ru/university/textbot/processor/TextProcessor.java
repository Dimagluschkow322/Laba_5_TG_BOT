package ru.university.textbot.processor;

public class TextProcessor {

    public String removeVowels(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }

        StringBuilder sb = new StringBuilder();
        String vowels = "аеёиоуыэюяАЕЁИОУЫЭЮЯ";

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (vowels.indexOf(c) == -1) {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    public int countRemovedVowels(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int k = 0;
        String vowels = "аеёиоуыэюяАЕЁИОУЫЭЮЯ";

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (vowels.indexOf(c) != -1) {
                k++;
            }
        }

        return k;
    }
}