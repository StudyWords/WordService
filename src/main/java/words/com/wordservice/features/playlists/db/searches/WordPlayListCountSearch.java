package words.com.wordservice.features.playlists.db.searches;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.features.playlists.main.enums.PlayListVisibility;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.shared.utils.Range;

import java.util.Collection;
import java.util.Set;

@Builder
public record WordPlayListCountSearch(
        @Singular(ignoreNullCollections = true) Collection<String> ids,
        @Singular(ignoreNullCollections = true) Collection<String> userIds,
        String name,
        Range<Long> count,
        @Singular(ignoreNullCollections = true) Set<CEFR> cefrs,
        @Singular(ignoreNullCollections = true) Set<String> tags,
        Language language,
        Language translateLanguage,
        PlayListVisibility visibility,
        String associationId,
        @Singular(ignoreNullCollections = true) Collection<String> notInIds,
        Boolean hasPin
) {

}