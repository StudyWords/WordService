package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.entities.PinnedWordEntity;
import words.com.wordservice.db.searches.PinnedWordSearch;

import java.util.Collection;
import java.util.List;

public interface PinnedWordDao {

    Page<PinnedWordEntity> findBy(PinnedWordSearch search, Pageable pageable);

    default List<PinnedWordEntity> findBy(PinnedWordSearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    void save(Collection<PinnedWordEntity> entities);

    void delete(PinnedWordSearch search);
}
