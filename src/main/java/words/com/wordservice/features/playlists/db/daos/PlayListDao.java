package words.com.wordservice.features.playlists.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.features.playlists.db.actions.PlayListUpdateAction;
import words.com.wordservice.features.playlists.db.entities.WordPlayListEntity;
import words.com.wordservice.features.playlists.db.projections.PlayListCountProjection;
import words.com.wordservice.features.playlists.db.searches.WordPlayListCountSearch;
import words.com.wordservice.features.playlists.db.searches.WordPlayListSearch;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlayListDao {

    Page<WordPlayListEntity> findBy(WordPlayListSearch search, Pageable pageable);

    default List<WordPlayListEntity> findBy(WordPlayListSearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    Page<PlayListCountProjection> findBy(WordPlayListCountSearch search, Pageable pageable);

    default List<PlayListCountProjection> findBy(WordPlayListCountSearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    Optional<PlayListCountProjection> findRandomByUserId(String userId);

    void save(Collection<WordPlayListEntity> entities);

    void update(Collection<WordPlayListEntity> entities);

    int update(PlayListUpdateAction action);

    void delete(WordPlayListSearch search);
}
