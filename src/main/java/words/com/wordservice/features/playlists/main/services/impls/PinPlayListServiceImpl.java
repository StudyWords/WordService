package words.com.wordservice.features.playlists.main.services.impls;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import words.com.wordservice.features.playlists.main.mappers.PinPlayListDomainMapper;
import words.com.wordservice.features.playlists.main.models.UserPinIds;
import words.com.wordservice.features.playlists.main.models.UserPins;
import words.com.wordservice.features.playlists.main.services.PinPlayListService;
import words.com.wordservice.features.words.db.daos.PinnedWordDao;
import words.com.wordservice.features.words.db.searches.PinnedWordSearch;

@Service
@RequiredArgsConstructor
class PinPlayListServiceImpl implements PinPlayListService {
    private final PinnedWordDao pinnedWordDao;
    private final PinPlayListDomainMapper pinPlayListDomainMapper;

    @Override
    public void pin(UserPins userPins) {
        var entities = pinPlayListDomainMapper.toEntities(userPins);
        pinnedWordDao.save(entities);
    }

    @Override
    public void unpin(UserPinIds userPins) {
        var ids = userPins.pins().stream().map(pinPlayListDomainMapper::toEntity).toList();
        if (ids.isEmpty()) {
            return;
        }
        var search = PinnedWordSearch.builder().pinnedIds(ids).userId(userPins.userId()).build();
        pinnedWordDao.delete(search);
    }
}
