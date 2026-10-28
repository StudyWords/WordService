package words.com.wordservice.features.words.main.models;

import lombok.Builder;
import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;
import words.com.wordservice.features.words.main.enums.WordType;

import java.time.OffsetDateTime;

@Builder(toBuilder = true)
public record Word(
        String id,
        String original,
        Language lang,
        String translate,
        Language translateLang,
        @Nullable
        String category,
        @Nullable
        String soundLink,
        @Nullable
        String imageLink,
        WordType type,
        CEFR cefr,
        @Nullable
        String description,
        OffsetDateTime createdAt
) {

}
