package words.com.wordservice.features.playlists.main.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.features.playlists.db.daos.PlayListDao;
import words.com.wordservice.features.playlists.db.entities.WordPlayListEntity;
import words.com.wordservice.features.playlists.db.searches.WordPlayListSearch;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;
import words.com.wordservice.features.playlists.main.exceptions.PlayListIdsRequiredException;
import words.com.wordservice.features.playlists.main.exceptions.PlayListsNotFoundException;
import words.com.wordservice.features.playlists.main.mappers.WordPlayListDomainMapper;
import words.com.wordservice.features.playlists.main.mappers.WordPlayListSearchMapper;
import words.com.wordservice.features.playlists.main.models.*;
import words.com.wordservice.features.playlists.main.services.WordPlayListService;
import words.com.wordservice.features.shared.main.events.LearningHistoryEvent;
import words.com.wordservice.features.words.db.actions.UserWordGradeUpdateAction;
import words.com.wordservice.features.words.db.daos.PinnedWordDao;
import words.com.wordservice.features.words.db.daos.UserWordDao;
import words.com.wordservice.features.words.db.entities.PinnedWordEntity;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.searches.PinnedWordSearch;
import words.com.wordservice.features.words.db.searches.UserWordSearch;
import words.com.wordservice.features.words.main.mappers.UserWordDomainMapper;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class WordPlayListServiceImpl implements WordPlayListService {
    private final WordPlayListSearchMapper searchMapper;
    private final PlayListDao dao;
    private final PinnedWordDao pinnedWordDao;
    private final WordPlayListDomainMapper wordPlayListDomainMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final UserWordDao userWordDao;
    private final UserWordDomainMapper userWordDomainMapper;

    @Override
    public Page<WordPlayList> findBy(WordPlayListFilter filter) {
        var paged = dao.findBy(
                searchMapper.toSearch(filter),
                searchMapper.toPageable(filter)
        );
        if (paged.isEmpty()) {
            return paged.map(wordPlayListDomainMapper::toModel);
        }

        var playListIds = paged.stream().map(WordPlayListEntity::getId).collect(Collectors.toSet());

        var pinSearch = PinnedWordSearch.builder().playListIds(playListIds).build();
        var pinsByPlayList = pinnedWordDao.findBy(pinSearch).stream()
                .collect(Collectors.groupingBy(it -> it.getId().getPlayListId()));

        return paged.map(it -> wordPlayListDomainMapper.toModel(it, pinsByPlayList));
    }

    @Override
    public Page<WordPlayListCount> findBy(WordPlayListCountFilter filter) {
        return dao.findBy(
                searchMapper.toSearch(filter),
                searchMapper.toPageable(filter)
        ).map(wordPlayListDomainMapper::toModel);
    }

    @Override
    public void save(Collection<PlayListTitle> playLists) {
        var entities = playLists.stream()
                .map(wordPlayListDomainMapper::toEntity)
                .collect(Collectors.toList());
        dao.save(entities);
    }

    @Override
    public void update(Collection<PlayListTitle> playLists) {
        var entities = playLists.stream()
                .map(wordPlayListDomainMapper::toUpdateEntity)
                .collect(Collectors.toList());
        dao.update(entities);
    }

    @Override
    @Transactional
    public Set<String> assign(Collection<String> playListIds, String userId) {
        if (CollectionUtils.isEmpty(playListIds)) {
            throw new PlayListIdsRequiredException("playListIds is empty");
        }
        var search = WordPlayListSearch.builder()
                .ids(playListIds)
                .visibility(PlayListVisibility.PUBLIC)
                .build();
        var entities = dao.findBy(search);
        if (entities.isEmpty()) {
            throw new PlayListsNotFoundException("No playlists found to assign");
        }
        var newPlayLists = entities.stream()
                .map(it -> wordPlayListDomainMapper.toAssignEntity(it, userId))
                .toList();

        var publicPlayListIds = entities.stream().map(WordPlayListEntity::getId).collect(Collectors.toSet());
        var pinnedWords = pinnedWordDao.findBy(PinnedWordSearch.builder().playListIds(publicPlayListIds).build());
        var wordIds = pinnedWords.stream()
                .map(it -> it.getWord().getWord().getId())
                .collect(Collectors.toSet());


        var userWordSearch = UserWordSearch.builder().userId(userId).wordIds(wordIds).build();
        var savedUserWords = userWordDao.findBy(userWordSearch);

        var unqWordIds = new HashSet<String>();
        var userWordByWordId = savedUserWords.stream()
                .filter(it -> unqWordIds.add(it.getWord().getId()))
                .collect(Collectors.toMap(it -> it.getWord().getId(), it -> it));

        var newPlatListByBaseId = newPlayLists.stream()
                .collect(Collectors.toMap(WordPlayListEntity::getBaseId, it -> it));

        var newPinnedWords = new ArrayList<PinnedWordEntity>();
        var newUserWords = new ArrayList<UserWordEntity>();
        for (var pin : pinnedWords) {
            var playListEntity = newPlatListByBaseId.get(pin.getId().getPlayListId());
            if (playListEntity == null) {
                continue;
            }
            var userWordEntity = userWordByWordId.get(pin.getWord().getWord().getId());
            if (userWordEntity == null) {
                userWordEntity = wordPlayListDomainMapper.toUserWordEntity(userId, pin.getWord().getWord());
                newUserWords.add(userWordEntity);
                userWordByWordId.put(userWordEntity.getWord().getId(), userWordEntity);
            }
            var newPin = wordPlayListDomainMapper.toPinnedWordEntity(playListEntity.getId(), pin, userWordEntity);
            newPinnedWords.add(newPin);
        }

        var historyEntries = newUserWords.stream()
                .map(userWordDomainMapper::toHistoryEntry)
                .toList();

        dao.save(newPlayLists);
        userWordDao.save(newUserWords);
        pinnedWordDao.save(newPinnedWords);
        eventPublisher.publishEvent(new LearningHistoryEvent(historyEntries));

        return newPlayLists.stream()
                .map(WordPlayListEntity::getId)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<AssignedPlaylist> findAssigned(String userId) {
        var search = WordPlayListSearch.builder()
                .userId(userId)
                .hasBaseId(true)
                .build();
        return dao.findBy(search)
                .stream()
                .map(WordPlayListEntity::getBaseId)
                .map(AssignedPlaylist::new)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public void updateGrades(Collection<PlayListGrade> grades) {
        var actions = grades.stream().map(wordPlayListDomainMapper::toAction).toList();
        var updated = userWordDao.addGrades(actions);
        pinnedWordDao.addGrades(updated);
        publishRepetitions(grades, updated);
    }

    private void publishRepetitions(Collection<PlayListGrade> grades, List<UserWordGradeUpdateAction> updated) {
        if (updated.isEmpty()) {
            return;
        }
        var search = UserWordSearch.builder()
                .userWordIds(updated.stream().map(UserWordGradeUpdateAction::userWordId).collect(Collectors.toSet()))
                .build();
        var wordIdByUserWordId = userWordDao.findBy(search).stream()
                .collect(Collectors.toMap(UserWordEntity::getId, it -> it.getWord().getId(), (a, b) -> b));
        var entries = grades.stream()
                .filter(grade -> wordIdByUserWordId.containsKey(grade.wordId()))
                .map(grade -> wordPlayListDomainMapper.toHistoryEntry(grade, wordIdByUserWordId.get(grade.wordId())))
                .toList();
        eventPublisher.publishEvent(new LearningHistoryEvent(entries));
    }

    @Override
    public Optional<WordPlayListCount> findRandom(String userId) {
        return dao.findRandomByUserId(userId).map(wordPlayListDomainMapper::toModel);
    }

    @Override
    public void delete(UserPlayLists playLists) {
        var search = WordPlayListSearch.builder()
                .userId(playLists.userId())
                .ids(playLists.ids())
                .build();
        dao.delete(search);
    }
}
