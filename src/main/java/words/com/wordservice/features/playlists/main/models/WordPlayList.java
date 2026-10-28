package words.com.wordservice.features.playlists.main.models;

import lombok.Builder;
import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.words.main.models.PinnedWord;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.TreeSet;

@Builder(toBuilder = true)
public record WordPlayList(
        String id,
        String userId,
        String name,
        OffsetDateTime createdAt,
        List<PinnedWord> words,
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
}
