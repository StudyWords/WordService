package words.com.wordservice.api.mappers;

import org.springframework.stereotype.Component;
import words.backend.authmodule.net.models.User;
import words.com.wordservice.api.requests.pins.PinPlayRequest;
import words.com.wordservice.main.models.pins.PinnedWordId;
import words.com.wordservice.main.models.pins.UserPinIds;
import words.com.wordservice.main.models.pins.UserPins;
import words.com.wordservice.main.models.playlist.PlayListWord;

import java.time.OffsetDateTime;
import java.util.Collection;

@Component
public class PinPlayListApiMapper {

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
