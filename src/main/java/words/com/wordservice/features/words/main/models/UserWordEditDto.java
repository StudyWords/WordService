package words.com.wordservice.features.words.main.models;

import org.springframework.lang.Nullable;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

public record UserWordEditDto(
        String id,
        String userId,
        String original,
        Language lang,
        String translate,
        Language translateLang,
        @Nullable
        String category,
        @Nullable
        String soundFileName,
        @Nullable
        String imageFileName,
        @Nullable
        String description,
        CEFR cefr
) {
}
