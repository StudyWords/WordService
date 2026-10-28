package words.com.wordservice.features.playlists.main.models;

import lombok.Builder;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;
import words.com.wordservice.features.playlists.main.enums.WordPlaylistSortField;

import java.util.Collection;

@Builder(toBuilder = true)
public record WordPlayListFilter(
        Collection<String> ids,
        Collection<String> userIds,
        String name,
        Boolean hasPin,
        PlayListVisibility visibility,

        WordPlaylistSortField sortField,
        boolean asc,
        int page,
        int size
) {
}
