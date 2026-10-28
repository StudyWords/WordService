package words.com.wordservice.features.playlists.main.models;

import java.util.Collection;

public record UserPins(
        Collection<PlayListWord> pins,
        String userId
) {
}
