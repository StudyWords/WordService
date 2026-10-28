package words.com.wordservice.features.playlists.main.models;

import java.util.List;

public record UserPlayLists(
        String userId,
        List<String> ids
) {
}
