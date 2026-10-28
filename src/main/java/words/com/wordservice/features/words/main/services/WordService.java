package words.com.wordservice.features.words.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.features.words.main.models.Word;
import words.com.wordservice.features.words.main.models.WordDeleteDto;
import words.com.wordservice.features.words.main.models.WordDraft;
import words.com.wordservice.features.words.main.models.WordFilter;

import java.util.Collection;

public interface WordService {
    Page<Word> findBy(WordFilter filter);

    void save(Collection<WordDraft> words);

    void update(Collection<WordDraft> words);

    void delete(WordDeleteDto options);
}
