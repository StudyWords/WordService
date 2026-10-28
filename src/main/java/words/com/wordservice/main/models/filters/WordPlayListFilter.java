package words.com.wordservice.main.models.filters;

import lombok.Builder;
import words.com.wordservice.main.models.enums.PlayListVisibility;
import words.com.wordservice.main.models.enums.sortfields.WordPlaylistSortField;

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
