package com.lucas.wilsonwolfe.wordjumble;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class KeywordCipher implements Cipher {
    private String keyword;

    public KeywordCipher(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String decrypt(String text) {
        return decrypt(text, keyword);
    }

    static char[] alphabet = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p','q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    private String decrypt(String text, final String key) {
        String result = "";
        text = text.toLowerCase();
        char[] newAlpha = createAlpha(key);
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            try {
                if (c < 'a' || c > 'z') {
                    result += c;
                    continue;
                }
                int regIndex = getRegIndex(c, newAlpha);
                result += alphabet[regIndex];
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(String.format("Fail on %s", c));
            }
        }
        return result;
    }

    private int getRegIndex(char c, char[] alpha) {
        for (int i = 0; i < alpha.length; i++) {
            if (alpha[i] == c) {
                return i;
            }
        }
        throw new IllegalArgumentException("Invalid char " + c);
    }

    private char[] createAlpha(String key) {
        key = key.toLowerCase();
        Set<Character> newAlpha = new LinkedHashSet<>();
        char[] keyChars = key.toCharArray();
        for(int i = 0; i < keyChars.length; i++) {
            newAlpha.add(keyChars[i]);
        }

        for (int i = 0; i < alphabet.length; i++) {
            char c = alphabet[i];
            if(!key.contains(String.valueOf(c))) {
                newAlpha.add(c);
            }
        }

        return  newAlpha.stream()
                .map(ch -> ch.toString())
                .collect(Collectors.joining())
                .toCharArray();
    }
}
