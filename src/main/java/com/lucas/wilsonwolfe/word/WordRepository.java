package com.lucas.wilsonwolfe.word;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends PagingAndSortingRepository<Word, Word.WordId> {

    public boolean existsWordByWord(String word);
}
