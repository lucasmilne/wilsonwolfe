package com.lucas.wilsonwolfe.wordjumble;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class CaeserWithKeyword implements Cipher {
    private String keyword;
    private int shift;

    public CaeserWithKeyword(String keyword, int shift) {
        this.keyword = keyword;
        this.shift = shift;
    }

    @Override
    public String decrypt(String text) {
        return decrypt(text, keyword);
    }



    private String decrypt(String text, final String key) {
        text = text.toLowerCase();
        char[] newAlpha = createAlpha(key);
        return decrypt(text, shift, newAlpha);
    }

    public String decrypt(String cipher, int shift, char[] newAlpha) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < cipher.length(); i++) {
            char c = cipher.charAt(i);
            try {
                if (c < 'a' || c > 'z') {
                    result.append(c);
                    continue;
                }
                int regIndex = getCodedIndex(c, newAlpha);
                int shiftedIndex = getShiftedIndex(regIndex, shift);
                result.append(Constants.ALPHABET[shiftedIndex]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(String.format("Fail on %s", c));
            }
        }
        return result.toString();
    }

    private int getShiftedIndex(int regIndex, int shift) {
        int shifted = regIndex - shift;
        if (shifted < 0) {
            return 26 + shifted;
        }
        return shifted;
    }

    private int getCodedIndex(char c, char[] newAlpha) {
        for (int i = 0; i < newAlpha.length; i++) {
            if (newAlpha[i] == c) {
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

        for (int i = 0; i < Constants.ALPHABET.length; i++) {
            char c = Constants.ALPHABET[i];
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
