package words.com.wordservice.features.playlists.db.actions;

import java.time.OffsetDateTime;

public record PinPlayListUpdateAction(
        String userId,
        String playListId,
        OffsetDateTime pinnedAt
) implements PlayListUpdateAction {
}
