package words.com.wordservice.main.models.pins;

import words.com.wordservice.main.models.playlist.PinPlayList;

import java.util.Collection;

public record PinOptions(
        Collection<PinPlayList> pins,
        String userId
) {
}
