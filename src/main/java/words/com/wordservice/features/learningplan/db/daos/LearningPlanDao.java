package words.com.wordservice.features.learningplan.db.daos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import words.com.wordservice.features.learningplan.db.entities.LearningPlanEntity;
import words.com.wordservice.features.learningplan.db.searches.LearningPlanSearch;

import java.util.List;

public interface LearningPlanDao {

    Page<LearningPlanEntity> findBy(LearningPlanSearch search, Pageable pageable);

    default List<LearningPlanEntity> findBy(LearningPlanSearch search) {
        return findBy(search, Pageable.unpaged()).getContent();
    }

    LearningPlanEntity save(LearningPlanEntity entity);

    void update(LearningPlanEntity entity);
}
