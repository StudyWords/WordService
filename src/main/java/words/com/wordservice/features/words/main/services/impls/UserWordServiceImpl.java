package words.com.wordservice.features.words.main.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import words.com.wordservice.features.learninghistory.db.daos.LearningHistoryDao;
import words.com.wordservice.features.words.db.actions.UserWordUpsertAction;
import words.com.wordservice.features.words.db.daos.PinnedWordDao;
import words.com.wordservice.features.words.db.daos.UserWordDao;
import words.com.wordservice.features.words.db.daos.WordDao;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.searches.Operations;
import words.com.wordservice.features.words.db.searches.PinnedWordSearch;
import words.com.wordservice.features.words.db.searches.UserWordSearch;
import words.com.wordservice.features.words.main.enums.WordType;
import words.com.wordservice.features.words.main.exceptions.UserWordNotFoundException;
import words.com.wordservice.features.words.main.mappers.UserWordDomainMapper;
import words.com.wordservice.features.words.main.mappers.UserWordSearchMapper;
import words.com.wordservice.features.words.main.models.*;
import words.com.wordservice.features.words.main.services.UserWordService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
class UserWordServiceImpl implements UserWordService {
    private final WordDao wordDao;
    private final UserWordDao userWordDao;
    private final UserWordDomainMapper userWordDomainMapper;
    private final UserWordSearchMapper userWordSearchMapper;
    private final LearningHistoryDao learningHistoryDao;
    private final PinnedWordDao pinnedWordDao;

    @Override
    public Page<UserWord> findBy(UserWordFilter filter) {
        var search = userWordSearchMapper.toSearch(filter);
        var pageable = userWordSearchMapper.toPageable(filter);
        return userWordDao.findBy(search, pageable)
                .map(userWordDomainMapper::toModel);
    }

    @Override
    @Transactional
    public void save(Collection<UserWordCreateDto> userWords) {
        var userWordEntities = userWords.stream()
                .map(userWordDomainMapper::toEntity)
                .toList();

        var wordEntities = userWordEntities.stream()
                .map(UserWordEntity::getWord)
                .toList();

        wordDao.save(wordEntities);
        userWordDao.save(userWordEntities);

        var historyEntities = userWords.stream()
                .map(userWordDomainMapper::toLearningHistoryEntity)
                .toList();
        learningHistoryDao.save(historyEntities);
    }

    @Override
    @Transactional
    public List<UserWord> link(Collection<UserWordLink> userWords) {
        var actions = userWords.stream()
                .map(userWordDomainMapper::toAction)
                .toList();
        userWordDao.upsertAll(actions);

        var historyEntities = userWords.stream()
                .map(userWordDomainMapper::toLearningHistoryEntity)
                .toList();
        learningHistoryDao.save(historyEntities);
        var ids = actions.stream().map(UserWordUpsertAction::id).toList();
        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        var filter = UserWordFilter.builder().page(0).size(ids.size()).userWordIds(ids).build();
        return findBy(filter).getContent();
    }

    @Override
    @Transactional
    public void update(UserWordEditDto userWord) {
        var search = UserWordSearch.builder()
                .userWordId(userWord.id())
                .userId(userWord.userId())
                .build();
        var userWordEntity = userWordDao.findBy(search)
                .stream()
                .findFirst()
                .orElseThrow(() -> new UserWordNotFoundException("User word not found"));
        userWordDao.update(userWordDomainMapper.toEntity(userWordEntity, userWord));
        if (userWordEntity.getWord().getType() == WordType.PUBLIC) {
            return;
        }
        wordDao.update(userWordDomainMapper.toEntity(userWordEntity.getWord(), userWord));
    }

    @Override
    public void addGrades(Collection<UserWordGrade> grades) {
        var actions = grades.stream()
                .map(userWordDomainMapper::toAction)
                .toList();
        userWordDao.addGrades(actions);
    }

    @Override
    public long count(UserWordFilter filter) {
        return userWordDao.count(userWordSearchMapper.toSearch(filter));
    }

    @Override
    public void delete(Collection<UserWordDeleteDto> userWords) {
        var searches = userWords.stream()
                .map(userWordDomainMapper::toSearch)
                .toList();
        if (searches.isEmpty()) {
            return;
        }
        var search = UserWordSearch.builder()
                .operation(Operations.OR)
                .searches(searches)
                .build();
        var words = userWordDao.findBy(search);
        if (words.isEmpty()) {
            return;
        }
        var userWordIds = words.stream().map(UserWordEntity::getId).toList();

        var deleteSearch = PinnedWordSearch.builder()
                .userWordIds(userWordIds)
                .build();
        pinnedWordDao.delete(deleteSearch);
        userWordDao.delete(words);

    }

}
