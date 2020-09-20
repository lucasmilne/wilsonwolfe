package com.lucas.wilsonwolfe.word;

import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="wn_synset")
public class Word {
    @EmbeddedId
    private WordId id;

    @Column(name = "word")
    private String word;

    public WordId getId() {
        return id;
    }

    public void setId(WordId id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Embeddable
    public static class WordId implements Serializable {
        @Column(name = "synset_id")
        private int synsetId;
        @Column(name = "w_num")
        private int definition;

        protected WordId(){}

        public WordId(int synsetId, int definition) {
            this.synsetId = synsetId;
            this.definition = definition;
        }

        public int getSynsetId() {
            return synsetId;
        }

        public void setSynsetId(int synsetId) {
            this.synsetId = synsetId;
        }

        public int getDefinition() {
            return definition;
        }

        public void setDefinition(int definition) {
            this.definition = definition;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WordId wordId = (WordId) o;
            return synsetId == wordId.synsetId &&
                    definition == wordId.definition;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(synsetId, definition);
        }
    }
}
