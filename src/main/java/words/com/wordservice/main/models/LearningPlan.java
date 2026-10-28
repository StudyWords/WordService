package words.com.wordservice.main.models;

import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.Language;

import java.time.OffsetDateTime;

public record LearningPlan(
        String userId,
        int wordsPerDay,
        Language nativeLang,
        Language learningLang,
        CEFR cefr,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
