package words.com.wordservice.features.words.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.features.words.db.actions.WordDeleteAction;
import words.com.wordservice.features.words.db.entities.WordEntity;
import words.com.wordservice.features.words.db.searches.WordSearch;

import java.util.Collection;
import java.util.List;

public interface WordDao {

    Page<WordEntity> findBy(WordSearch search, Pageable pageable);

    default List<WordEntity> findBy(WordSearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    void save(Collection<WordEntity> entities);

    void update(WordEntity entity);

    void delete(Collection<WordDeleteAction> actions);
}
