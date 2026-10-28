package words.com.wordservice.features.playlists.main.models;

import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.time.OffsetDateTime;
import java.util.TreeSet;

public record WordPlayListCount(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        Long count,
        @Nullable
        TreeSet<String> tags,
        @Nullable
        TreeSet<CEFR> cefrs,
        @Nullable
        Language language,
        @Nullable
        Language translateLanguage,
        @Nullable
        OffsetDateTime pinnedAt
) {
}
