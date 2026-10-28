package words.com.wordservice.features.playlists.db.daos.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.features.playlists.db.actions.PinPlayListUpdateAction;
import words.com.wordservice.features.playlists.db.actions.PlayListUpdateAction;
import words.com.wordservice.features.playlists.db.actions.UnPinPlayListUpdateAction;
import words.com.wordservice.features.playlists.db.daos.PlayListDao;
import words.com.wordservice.features.playlists.db.entities.WordPlayListEntity;
import words.com.wordservice.features.playlists.db.projections.PlayListCountProjection;
import words.com.wordservice.features.playlists.db.searches.WordPlayListCountSearch;
import words.com.wordservice.features.playlists.db.searches.WordPlayListSearch;
import words.com.wordservice.features.shared.utils.Range;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
class PlayListDaoImpl implements PlayListDao {
    private final WordPlayListRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Page<WordPlayListEntity> findBy(WordPlayListSearch search, Pageable pageable) {
        return repository.findAll(search, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlayListCountProjection> findBy(WordPlayListCountSearch search, Pageable pageable) {
        return repository.findPlayListsWithCount(
                CollectionUtils.isEmpty(search.ids()),
                search.ids(),
                CollectionUtils.isEmpty(search.userIds()),
                search.userIds(),
                search.name(),
                Range.to(search.count()),
                Range.from(search.count()),
                CollectionUtils.isEmpty(search.cefrs()),
                search.cefrs() != null ? search.cefrs().stream().map(Enum::name).toList() : List.of(),
                CollectionUtils.isEmpty(search.tags()),
                search.tags() != null ? search.tags() : Set.of(),
                search.language() != null ? search.language().name() : null,
                search.translateLanguage() != null ? search.translateLanguage().name() : null,
                search.visibility() != null ? search.visibility().name() : null,
                search.associationId(),
                CollectionUtils.isEmpty(search.notInIds()),
                search.notInIds() != null ? search.notInIds() : List.of(),
                search.hasPin(),
                pageable
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlayListCountProjection> findRandomByUserId(String userId) {
        return repository.findRandomByUserId(userId);
    }

    @Override
    @Transactional
    public void save(Collection<WordPlayListEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public void update(Collection<WordPlayListEntity> entities) {
        repository.saveAll(entities);
    }

    @Override
    @Transactional
    public int update(PlayListUpdateAction action) {
        switch (action) {
            case PinPlayListUpdateAction pinAction -> {
                return repository.pin(pinAction.playListId(), pinAction.userId(), pinAction.pinnedAt());
            }
            case UnPinPlayListUpdateAction unPinAction -> {
                return repository.unpin(unPinAction.playListId(), unPinAction.userId());
            }
            default -> throw new IllegalStateException("Unexpected value: " + action);
        }
    }

    @Override
    @Transactional
    public void delete(WordPlayListSearch search) {
        if (CollectionUtils.isEmpty(search.ids())) {
            return;
        }
        repository.delete(search);
    }
}
