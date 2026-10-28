package words.com.wordservice.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.main.models.filters.WordFilter;
import words.com.wordservice.main.models.words.ModifyWord;
import words.com.wordservice.main.models.words.Word;
import words.com.wordservice.main.models.words.WordDeleteDto;

import java.util.Collection;

public interface WordService {
    Page<Word> findBy(WordFilter filter);

    void saveAll(Collection<ModifyWord> words);

    void updateAll(Collection<ModifyWord> words);

    void delete(WordDeleteDto options);
}
