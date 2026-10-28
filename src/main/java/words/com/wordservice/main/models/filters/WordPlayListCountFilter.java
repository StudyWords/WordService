package words.com.wordservice.main.models.filters;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.Language;
import words.com.wordservice.main.models.enums.PlayListVisibility;
import words.com.wordservice.main.models.enums.sortfields.WordPlaylistSortField;
import words.com.wordservice.utils.Range;

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
