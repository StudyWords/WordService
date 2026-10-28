package words.com.wordservice.main.services.impls;

import lombok.RequiredArgsConstructor;
import words.com.wordservice.db.daos.PinnedWordDao;
import words.com.wordservice.db.searches.PinnedWordSearch;
import words.com.wordservice.main.mappers.PinPlayListDomainMapper;
import words.com.wordservice.main.models.pins.UserPinIds;
import words.com.wordservice.main.models.pins.UserPins;
import words.com.wordservice.main.services.PinPlayListService;

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
