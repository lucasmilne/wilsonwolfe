package com.lucas.wilsonwolfe.wordjumble;

public class CaesarShift implements Cipher {
    private String keyword;
    private int shift;

    public CaesarShift(int shift) {
        this.shift = shift;
    }

    @Override
    public String decrypt(String text) {
        text = text.toLowerCase();
        char[] newAlpha = Constants.ALPHABET;
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
}
