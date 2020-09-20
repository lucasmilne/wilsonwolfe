package com.lucas.wilsonwolfe.wordjumble;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//@Component
public class LetterPermutationsRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(LetterPermutationsRunner.class);
    private final static String[] words = {"CAESAR", "SHIFT", "VERTICAL", "SYMMETRY", "CENTRE", "LINE", "MIRROR", "SAME", "IMAGE"};
    private final static String[] mirrors = {"YFPYZI", "KRHNK", "BZDRZQRG", "GDRQCAML", "AQLIXP", "PNKS", "XAFSDS", "WTOR", "EJNVI"};

    Map<Character, List<Character>> multiMap = new HashMap<>();

    @Autowired
    DecryptionChecker decryptionChecker;

    @Override
    public void run(String... args) throws Exception {
        initMap();
        makeMap();
        decrypt();
    }

    private void decrypt() {
        List<String> currentPerms = Lists.newArrayList("");
        for (Character c : Constants.BIG_WORD.toLowerCase().toCharArray()) {
            List<Character> mapping = getMapping(c);
            currentPerms = newPerms(currentPerms, mapping);
        }

        for (String decryptedWord : currentPerms) {
            if (decryptionChecker.isReadable(decryptedWord)) {
                Toolkit.getDefaultToolkit().beep();
                logger.info("WE'VE DONE IT {}:{}", decryptedWord, decryptedWord);
                System.exit(0);
            }
        }
    }

    private List<Character> getMapping(Character c) {
        if (multiMap.containsKey(c)) {
            return multiMap.get(c);
        }
        return Lists.newArrayList(c);
    }

    private List<String> newPerms(List<String> currentPerms, List<Character> mapping) {
        return mapping.stream().flatMap(c -> currentPerms.stream().map(s -> s + c)).collect(Collectors.toList());
    }

    private void printMap() {
        for (int i = 0; i < Constants.ALPHABET.length; i++) {
            List<Character> charList = multiMap.get(Constants.ALPHABET[i]);
            logger.info("{} maps to {}", Constants.ALPHABET[i], charList);
        }
    }


    private void initMap() {
        for (int i = 0; i < Constants.ALPHABET.length; i++) {
            Character c = Constants.ALPHABET[i];
            multiMap.put(c, new ArrayList<>());
        }
    }

    private void makeMap() {
        String allWords = String.join("", words).toLowerCase();
        String allMirrors = String.join("", mirrors).toLowerCase();

        for (int i = 0; i < allWords.length(); i++) {
            Character c = allMirrors.charAt(i);
            List<Character> charList = multiMap.get(c);
            if (!charList.contains(allWords.charAt(i))) {
                charList.add(allWords.charAt(i));
            }
        }
    }


}
