package words.com.wordservice.features.playlists.main.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import words.com.wordservice.features.playlists.main.models.PlayListWord;
import words.com.wordservice.features.playlists.main.models.UserPins;
import words.com.wordservice.features.words.db.daos.UserWordDao;
import words.com.wordservice.features.words.db.entities.PinnedWordEntity;
import words.com.wordservice.features.words.db.entities.UserWordEntity;
import words.com.wordservice.features.words.db.searches.UserWordSearch;
import words.com.wordservice.features.words.main.models.PinnedWordId;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PinPlayListDomainMapper {
    private final UserWordDao userWordDao;

    public PinnedWordEntity.PinnedWordId toEntity(PinnedWordId id) {
        return new PinnedWordEntity.PinnedWordId(id.playListId(), id.userWordId());
    }

    public List<PinnedWordEntity> toEntities(UserPins userPins) {
        if (CollectionUtils.isEmpty(userPins.pins())) {
            return Collections.emptyList();
        }
        var userWordIds = userPins.pins().stream().map(PlayListWord::wordId).toList();
        var search = UserWordSearch.builder().userWordIds(userWordIds).userId(userPins.userId()).build();
        var entities = userWordDao.findBy(search);
        var userWordById = entities.stream().collect(Collectors.toMap(UserWordEntity::getId, Function.identity()));

        return userPins.pins().stream()
                .map(pin -> toEntity(pin, userWordById))
                .filter(it -> it.getWord() != null)
                .toList();
    }

    private PinnedWordEntity toEntity(PlayListWord pin, Map<String, UserWordEntity> userWordById) {
        var id = new PinnedWordEntity.PinnedWordId(pin.playListId(), pin.wordId());

        return new PinnedWordEntity(
                id,
                pin.learningGrade(),
                pin.createdAt(),
                OffsetDateTime.now(),
                userWordById.get(pin.wordId())
        );
    }
}
