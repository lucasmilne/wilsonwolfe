package com.lucas.wilsonwolfe.wordjumble;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DecryptionChecker {
    private static Logger logger = LoggerFactory.getLogger(DecryptionChecker.class);

    public boolean isReadable(String word) {
        logger.info("Trying word {}", word);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<WordDefinition[]> response = restTemplate.getForEntity("https://api.dictionaryapi.dev/api/v2/entries/en/" + word, WordDefinition[].class);
            logger.info("Response {}", response.getBody());
            return true;
        } catch (Exception e) {
            logger.debug(e.getMessage());
        }
        return false;
    }

    public static class WordDefinition {
        private String word;

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return "WordDefinition{" +
                    "word='" + word + '\'' +
                    '}';
        }
    }
}
