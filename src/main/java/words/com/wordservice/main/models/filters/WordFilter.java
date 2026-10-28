package words.com.wordservice.main.models.filters;

import lombok.Singular;
import org.springframework.lang.NonNull;
import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.Language;
import words.com.wordservice.main.models.enums.WordType;
import words.com.wordservice.main.models.enums.sortfields.WordSortField;

import java.util.Collection;

public record WordFilter(
        @Singular(ignoreNullCollections = true)
        Collection<String> wordIds,
        @Singular(ignoreNullCollections = true)
        Collection<Language> languages,
        @Singular(ignoreNullCollections = true)
        Collection<Language> translateLanguages,
        @Singular(ignoreNullCollections = true)
        Collection<String> categories,
        @Singular(ignoreNullCollections = true)
        Collection<CEFR> cefrs,
        @Singular(ignoreNullCollections = true)
        Collection<WordType> types,

        String translate,
        String original,
        UserId userId,

        WordSortField sortField,
        boolean asc,
        int page,
        int size
) {


    public record UserId(
            @NonNull
            String id,
            boolean isIn
    ) {
    }

}
