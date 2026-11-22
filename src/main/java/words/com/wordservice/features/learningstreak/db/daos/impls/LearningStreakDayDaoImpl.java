package words.com.wordservice.features.learningstreak.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import words.com.wordservice.features.learningstreak.db.daos.LearningStreakDayDao;
import words.com.wordservice.features.learningstreak.db.entities.LearningStreakDayEntity;
import words.com.wordservice.features.learningstreak.db.searches.LearningStreakDaySearch;

@Service
@RequiredArgsConstructor
class LearningStreakDayDaoImpl implements LearningStreakDayDao {
    private final LearningStreakDayJpaRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<LearningStreakDayEntity> findBy(LearningStreakDaySearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(LearningStreakDaySearch search) {
        return repository.exists(search);
    }

    @Override
    @Transactional
    public void upsert(LearningStreakDayEntity entity) {
        repository.upsert(
                entity.getId().getUserId(),
                entity.getId().getDay(),
                entity.getWordsCount(),
                entity.getZoneId(),
                entity.getUtcOffsetMinutes(),
                entity.getCreatedAt()
        );
    }
}
