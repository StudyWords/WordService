package words.com.wordservice.features.learningplan.api.responds;

import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.time.OffsetDateTime;
import java.util.Set;

public record LearningPlanRespond(
        int wordsPerDay,
        Language nativeLang,
        Language learningLang,
        Set<CEFR> cefrs,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt
) {
}
