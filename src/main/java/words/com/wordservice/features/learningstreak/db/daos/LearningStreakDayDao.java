package words.com.wordservice.features.learningstreak.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.features.learningstreak.db.entities.LearningStreakDayEntity;
import words.com.wordservice.features.learningstreak.db.searches.LearningStreakDaySearch;

import java.util.List;

public interface LearningStreakDayDao {

    Page<LearningStreakDayEntity> findBy(LearningStreakDaySearch search, Pageable pageable);

    default List<LearningStreakDayEntity> findBy(LearningStreakDaySearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    boolean exists(LearningStreakDaySearch search);

    /**
     * Inserts the day, or keeps the greater words count if the day is already stored.
     */
    void upsert(LearningStreakDayEntity entity);
}
