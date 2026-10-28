package words.com.wordservice.features.playlists.api.responds;

import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.words.api.responds.UserWordRespond;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TreeSet;

public record PlayListRespond(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        List<PinnedWordRespond> words,
        TreeSet<String> tags,
        TreeSet<CEFR> cefrs,
        @Nullable
        Language language,
        @Nullable
        Language translateLanguage,
        @Nullable
        String associationId,
        @Nullable
        OffsetDateTime pinnedAt
) {

    public record PinnedWordRespond(
            Long learningGrade,
            OffsetDateTime createdAt,
            OffsetDateTime lastReadDate,
            UserWordRespond word
    ) {
    }


}
