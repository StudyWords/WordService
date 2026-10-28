package words.com.wordservice.features.words.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import words.com.wordservice.features.words.db.actions.UserWordDeleteAction;
import words.com.wordservice.features.words.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.features.words.db.actions.UserWordUpsertAction;
import words.com.wordservice.features.words.db.daos.UserWordDao;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.searches.UserWordSearch;
import words.com.wordservice.features.words.db.searches.WordSearch;
import words.com.wordservice.features.words.main.enums.WordType;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
class UserWordDaoImpl implements UserWordDao {
    private final UserWordRepository repository;
    private final WordRepository wordRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<UserWordEntity> findBy(UserWordSearch s, Pageable pageable) {
        return repository.findAll(s, pageable);
    }

    @Override
    @Transactional
    public void save(Collection<UserWordEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void update(UserWordEntity entity) {
        repository.save(entity);
    }

    @Override
    @Transactional
    public void upsertAll(Collection<UserWordUpsertAction> entities) {
        for (UserWordUpsertAction action : entities) {
            repository.upsert(
                    action.id(),
                    OffsetDateTime.now(),
                    action.customImageFileName(),
                    action.customSoundFileName(),
                    OffsetDateTime.now(),
                    0,
                    action.userId(),
                    action.wordId()
            );
        }
    }

    @Override
    @Transactional
    public List<UserWordGradeUpdateAction> addGrades(Collection<UserWordGradeUpdateAction> actions) {
        var lastReadDate = OffsetDateTime.now();
        return actions.stream()
                .filter(action -> repository.updateGrade(
                        action.value(),
                        lastReadDate,
                        action.userWordId(),
                        action.userId()
                ) > 0)
                .toList();
    }

    @Override
    @Transactional
    public void delete(Collection<UserWordDeleteAction> actions) {
        for (UserWordDeleteAction action : actions) {
            var searchBuilder = UserWordSearch.builder()
                    .userWordId(action.id())
                    .wordId(action.wordId());

            if (StringUtils.hasText(action.userId())) {
                searchBuilder.userId(action.userId());
            }
            var search = searchBuilder.build();
            if (repository.delete(search) == 0){
                continue;
            }
            var wordSearch = WordSearch.builder()
                    .wordId(action.wordId())
                    .type(WordType.CUSTOM)
                    .build();
            wordRepository.delete(wordSearch);
        }
    }

    @Override
    @Transactional
    public void delete(List<UserWordEntity> entities) {
        repository.deleteAll(entities);
    }

    @Override
    @Transactional(readOnly = true)
    public long count(UserWordSearch s) {
        return repository.count(s);
    }
}
