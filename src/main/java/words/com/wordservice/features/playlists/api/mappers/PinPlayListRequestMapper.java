package words.com.wordservice.features.playlists.api.mappers;

import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.features.playlists.api.requests.PinPlayRequest;
import words.com.wordservice.features.playlists.main.models.PlayListWord;
import words.com.wordservice.features.playlists.main.models.UserPinIds;
import words.com.wordservice.features.playlists.main.models.UserPins;
import words.com.wordservice.features.words.main.models.PinnedWordId;

import java.time.OffsetDateTime;
import java.util.Collection;

@Component
public class PinPlayListRequestMapper {

    public UserPins toUserPins(User user, Collection<PinPlayRequest> requests) {
        return new UserPins(
                requests.stream().map(this::toPlayListWord).toList(),
                user.id()
        );
    }

    public UserPinIds toUserPinIds(User user, Collection<PinPlayRequest> requests) {
        return new UserPinIds(
                requests.stream().map(this::toPinnedWordId).toList(),
                user.id()
        );
    }

    private PlayListWord toPlayListWord(PinPlayRequest request) {
        return new PlayListWord(
                request.playListId(),
                request.wordId(),
                0L,
                OffsetDateTime.now(),
                OffsetDateTime.now()
        );
    }

    private PinnedWordId toPinnedWordId(PinPlayRequest request) {
        return new PinnedWordId(
                request.playListId(),
                request.wordId()
        );
    }
}
