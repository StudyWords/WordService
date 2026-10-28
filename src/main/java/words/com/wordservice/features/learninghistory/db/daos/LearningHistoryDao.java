package words.com.wordservice.features.learninghistory.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.projections.LearningProgressProjection;
import words.com.wordservice.features.learninghistory.db.projections.StatisticsLearningHistoryProjection;
import words.com.wordservice.features.learninghistory.db.searches.LearningHistorySearch;
import words.com.wordservice.features.learninghistory.db.searches.LearningProgressSearch;
import words.com.wordservice.features.learninghistory.db.searches.StatisticsLearningHistorySearch;

import java.util.Collection;
import java.util.List;

public interface LearningHistoryDao {

    default List<StatisticsLearningHistoryProjection> findBy(StatisticsLearningHistorySearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    default List<LearningHistoryEntity> findBy(LearningHistorySearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    Page<StatisticsLearningHistoryProjection> findBy(StatisticsLearningHistorySearch search, Pageable pageable);

    Page<LearningHistoryEntity> findBy(LearningHistorySearch search, Pageable pageable);

    LearningProgressProjection findProgress(LearningProgressSearch search);

    void save(Collection<LearningHistoryEntity> entities);

    void delete(LearningHistorySearch search);
}
