package words.com.wordservice.features.learningplan.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import words.com.wordservice.features.learningplan.db.daos.LearningPlanDao;
import words.com.wordservice.features.learningplan.db.entities.LearningPlanEntity;
import words.com.wordservice.features.learningplan.db.searches.LearningPlanSearch;

@Service
@RequiredArgsConstructor
class LearningPlanDaoImpl implements LearningPlanDao {
    private final LearningPlanRepository repository;
    @Override
    @Transactional(readOnly = true)
    public Page<LearningPlanEntity> findBy(LearningPlanSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    @Transactional
    public LearningPlanEntity save(LearningPlanEntity entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public void update(LearningPlanEntity entity) {
        repository.save(entity);
    }
}
