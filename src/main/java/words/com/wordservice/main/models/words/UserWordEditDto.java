package words.com.wordservice.main.models.words;

import org.springframework.lang.Nullable;
import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.Language;

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
