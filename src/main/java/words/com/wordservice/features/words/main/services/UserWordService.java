package words.com.wordservice.features.words.main.services;

import org.springframework.data.domain.Page;
import words.com.wordservice.features.words.main.models.*;

import java.util.Collection;
import java.util.List;

public interface UserWordService {

    Page<UserWord> findBy(UserWordFilter filter);

    void save(Collection<UserWordCreateDto> userWords);

    List<UserWord> link(Collection<UserWordLink> userWords);

    void update(UserWordEditDto userWord);

    void addGrades(Collection<UserWordGrade> grades);

    long count(UserWordFilter filter);

    void delete(Collection<UserWordDeleteDto> userWords);
}
