package words.com.wordservice.features.playlists.main.models;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;
import words.com.wordservice.features.playlists.main.enums.WordPlaylistSortField;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.shared.utils.Range;

import java.util.Collection;
import java.util.Set;

@Builder(toBuilder = true)
public record WordPlayListCountFilter(
        @Singular(ignoreNullCollections = true)
        Collection<String> ids,
        @Singular(ignoreNullCollections = true)
        Collection<String> userIds,
        @Singular(ignoreNullCollections = true)
        Collection<String> notInIds,
        @Singular(ignoreNullCollections = true)
        Set<CEFR> cefrs,
        @Singular(ignoreNullCollections = true)
        Set<String> tags,
        Language language,
        Language translateLanguage,
        String name,
        Range<Long> count,
        PlayListVisibility visibility,
        String associationId,
        Boolean hasPin,

        WordPlaylistSortField sortField,
        boolean asc,
        int page,
        int size
) {
}
