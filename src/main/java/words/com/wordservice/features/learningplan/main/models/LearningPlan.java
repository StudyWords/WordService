package words.com.wordservice.features.learningplan.main.models;

import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

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
