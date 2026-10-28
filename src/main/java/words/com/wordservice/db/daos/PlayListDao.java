package words.com.wordservice.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.db.entities.WordPlayListEntity;
import words.com.wordservice.db.projections.PlayListCountProjection;
import words.com.wordservice.db.searches.WordPlayListCountSearch;
import words.com.wordservice.db.searches.WordPlayListSearch;
import words.com.wordservice.playlist.db.actions.PlayListUpdateAction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PlayListDao {

    Page<WordPlayListEntity> findBy(WordPlayListSearch search, Pageable pageable);

    List<WordPlayListEntity> findBy(WordPlayListSearch search);

    Page<PlayListCountProjection> findBy(WordPlayListCountSearch search, Pageable pageable);

    List<PlayListCountProjection> findBy(WordPlayListCountSearch search);

    Optional<PlayListCountProjection> findRandomByUserId(String userId);

    void saveAll(Collection<WordPlayListEntity> entities);

    void updateAll(Collection<WordPlayListEntity> entities);

    int update(PlayListUpdateAction action);

    void updateGrades(Collection<UserWordGradeUpdateAction> actions);

    void delete(WordPlayListSearch search);
}
