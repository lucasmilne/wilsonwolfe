package com.lucas.wilsonwolfe.wordjumble;

import com.lucas.wilsonwolfe.word.Word;
import com.lucas.wilsonwolfe.word.WordRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CaesarAllWordRunner implements CommandLineRunner {
    private static Logger logger = LoggerFactory.getLogger(CaesarAllWordRunner.class);

    private final static String[] keys = {"CAESAR", "SHIFT", "VERTICAL", "SYMMETRY", "CENTRE", "LINE", "MIRROR", "SAME", "IMAGE"};
    private final static String[] derivitives = {"DIAMETER", "RADIUS", "PALINDROME", "Reflection"};
    private final static String[] mirrors = {"YFPYZI", "KRHNK", "BZDRZQRG", "GDRQCAML", "AQLIXP", "PNKS", "XAFSDS", "WTOR", "EJNVI"};

    private final static String[] fromSecondToLastRow = {"declassified",
            "opnwlddtqtpo",
            "nomvkccspson",
            "zayhwooebeaz",
            "higpewwmjmih",
            "mnlujbbrornm",
            "straphhxuxts",
            "tusbqiiyvyut",
            "efdmbttjgjfe",
            "lmktiaaqnqml",
            "abzixppfcfba",
            "fgencuukhkgf",
            "ghfodvvlilhg",
            "pqoxmeeuruqp",
            "jkirgyyolokj",
            "yzxgvnndadzy",
            "wxvetllbybxw",
            "ijhqfxxnknji",
            "cdbkzrrhehdc",
            "vwudskkaxawv",
            "qrpynffvsvrq",
            "bcajyqqgdgcb",
            "kljshzzpmplk",
            "uvtcrjjzwzvu",
            "xywfummczcyx"};



    private static List<String> all;

    static {
        all = new ArrayList<>();
        all.addAll(Arrays.asList(fromSecondToLastRow));
    }

    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private TaskExecutor taskExecutor;

    private Iterable<Word> words;
    private int count = 0;

    @Override
    public void run(String... args) throws Exception {
        for (String word : all) {
            doRun(word);
        }
    }

    public void doRun(String word) {
        count++;
        if (isAlpha(word)) {
            for (int i = 0; i < 26; i++) {
                CaeserWithKeyword cipher = new CaeserWithKeyword(word, i);
                String decodedText = cipher.decrypt(Constants.ENCODED_TEXT);
                logger.info("{} : {}", word, decodedText);

                if (isReadable(decodedText)) {
                    Toolkit.getDefaultToolkit().beep();
                    logger.info("WE'VE DONE IT {}:{}", word, decodedText);
                    System.exit(0);
                }
            }
        }
        if (count % 100 == 0) {
            logger.info("Processed {} words", count);
        }
    }

    private boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    private boolean isReadable(String decodedText) {
        String[] words = decodedText.split(" ");
        String word = words[5];
        logger.debug("Trying word {}", word);
        return wordRepository.existsWordByWord(word);
    }

}
