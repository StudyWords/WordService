package words.com.wordservice.features.learninghistory.main.models;

import lombok.Builder;
import words.com.wordservice.features.learninghistory.main.enums.LearningHistoryType;
import words.com.wordservice.features.shared.main.enums.CEFR;
import words.com.wordservice.features.shared.main.enums.Language;

import java.time.LocalDate;

@Builder
public record LearningHistory(
        String id,
        String userId,
        String wordId,
        String original,
        Language nativeLang,
        Language learningLang,
        CEFR cefr,
        LocalDate date,
        LearningHistoryType type,
        int grade
) {
}
