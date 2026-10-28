package words.com.wordservice.db.searches;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.Language;
import words.com.wordservice.main.models.enums.PlayListVisibility;
import words.com.wordservice.utils.Range;

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