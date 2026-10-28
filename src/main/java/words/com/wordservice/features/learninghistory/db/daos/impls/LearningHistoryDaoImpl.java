package words.com.wordservice.features.learninghistory.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.features.learninghistory.db.daos.LearningHistoryDao;
import words.com.wordservice.features.learninghistory.db.entities.LearningHistoryEntity;
import words.com.wordservice.features.learninghistory.db.projections.LearningProgressProjection;
import words.com.wordservice.features.learninghistory.db.projections.StatisticsLearningHistoryProjection;
import words.com.wordservice.features.learninghistory.db.searches.LearningHistorySearch;
import words.com.wordservice.features.learninghistory.db.searches.LearningProgressSearch;
import words.com.wordservice.features.learninghistory.db.searches.StatisticsLearningHistorySearch;
import words.com.wordservice.features.shared.utils.Range;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
class LearningHistoryDaoImpl implements LearningHistoryDao {
    // an IN list must not be empty; the query ignores it when hasCefrs is false
    private static final String NO_CEFR = "";

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
    public LearningProgressProjection findProgress(LearningProgressSearch search) {
        var cefrs = search.cefrs().stream().map(Enum::name).toList();
        return repository.findProgressBy(
                search.userId(),
                !cefrs.isEmpty(),
                cefrs.isEmpty() ? List.of(NO_CEFR) : cefrs,
                search.dayStart(),
                search.dayEnd()
        );
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
