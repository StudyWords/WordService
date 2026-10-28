package words.com.wordservice.features.words.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import words.com.wordservice.features.words.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.features.words.db.daos.PinnedWordDao;
import words.com.wordservice.features.words.db.entities.PinnedWordEntity;
import words.com.wordservice.features.words.db.searches.PinnedWordSearch;

import java.time.OffsetDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
class PinnedWordDaoImpl implements PinnedWordDao {
    private final PinnedWordRepository repository;
    @Override
    @Transactional(readOnly = true)
    public Page<PinnedWordEntity> findBy(PinnedWordSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    @Transactional
    public void save(Collection<PinnedWordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void addGrades(Collection<UserWordGradeUpdateAction> actions) {
        var lastReadDate = OffsetDateTime.now();
        for (UserWordGradeUpdateAction action : actions) {
            repository.updateGrade(action.value(), lastReadDate, action.userWordId());
        }
    }

    @Override
    @Transactional
    public void delete(PinnedWordSearch search) {
        var entities = repository.findAll(search);
        if (entities.isEmpty()) {
            return;
        }
        repository.deleteAll(entities);
    }
}
