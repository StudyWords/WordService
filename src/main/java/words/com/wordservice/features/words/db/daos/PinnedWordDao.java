package words.com.wordservice.features.words.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.features.words.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.features.words.db.entities.PinnedWordEntity;
import words.com.wordservice.features.words.db.searches.PinnedWordSearch;

import java.util.Collection;
import java.util.List;

public interface PinnedWordDao {

    Page<PinnedWordEntity> findBy(PinnedWordSearch search, Pageable pageable);

    default List<PinnedWordEntity> findBy(PinnedWordSearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    void save(Collection<PinnedWordEntity> entities);

    void addGrades(Collection<UserWordGradeUpdateAction> actions);

    void delete(PinnedWordSearch search);
}
