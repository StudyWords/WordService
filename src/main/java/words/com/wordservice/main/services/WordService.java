package words.com.wordservice.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.main.models.filters.WordFilter;
import words.com.wordservice.main.models.words.Word;
import words.com.wordservice.main.models.words.WordDeleteDto;
import words.com.wordservice.main.models.words.WordDraft;

import java.util.Collection;

public interface WordService {
    Page<Word> findBy(WordFilter filter);

    void save(Collection<WordDraft> words);

    void update(Collection<WordDraft> words);

    void delete(WordDeleteDto options);
}
