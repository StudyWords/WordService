package words.com.wordservice.features.playlists.main.models;

import words.com.wordservice.features.words.main.models.PinnedWordId;

import java.util.Collection;

public record UserPinIds(
        Collection<PinnedWordId> pins,
        String userId
) {
}
