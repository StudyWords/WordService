package words.com.wordservice.features.playlists.api.responds;

import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.time.OffsetDateTime;
import java.util.TreeSet;

public record PlayListCountRespond(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        Long count,
        TreeSet<String> tags,
        TreeSet<CEFR> cefrs,
        Language language,
        Language translateLanguage,
        @Nullable
        OffsetDateTime pinnedAt
) {
}