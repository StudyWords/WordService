package words.com.wordservice.features.learninghistory.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.features.learninghistory.db.daos.LearningHistoryDao;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.projections.CountLearningHistoryProjection;
import words.com.wordservice.features.learninghistory.db.projections.StatisticsLearningHistoryProjection;
import words.com.wordservice.features.learninghistory.db.searches.CountLearningHistorySearch;
import words.com.wordservice.features.learninghistory.db.searches.LearningHistorySearch;
import words.com.wordservice.features.learninghistory.db.searches.StatisticsLearningHistorySearch;
import words.com.wordservice.features.shared.utils.Range;

import java.util.Collection;

@Service
@RequiredArgsConstructor
class LearningHistoryDaoImpl implements LearningHistoryDao {
    private final LearningHistoryRepository repository;
    @Override
    @Transactional(readOnly = true)
    public Page<StatisticsLearningHistoryProjection> findBy(StatisticsLearningHistorySearch search, Pageable pageable) {
        return repository.findStatisticsBy(
                CollectionUtils.isEmpty(search.userIds()),
                CollectionUtils.isEmpty(search.userIds()) ? null : search.userIds(),
                Range.from(search.date()),
                Range.to(search.date()),
                pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LearningHistoryEntity> findBy(LearningHistorySearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CountLearningHistoryProjection> findBy(CountLearningHistorySearch search, Pageable pageable) {
        return repository.findCountBy(
                CollectionUtils.isEmpty(search.userIds()),
                CollectionUtils.isEmpty(search.userIds()) ? null : search.userIds(),
                pageable);
    }

    @Override
    @Transactional
    public void save(Collection<LearningHistoryEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void delete(LearningHistorySearch search) {
        repository.delete(search);
    }
}
