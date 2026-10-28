package words.com.wordservice.api.responds.history;

import words.com.wordservice.main.models.enums.CEFR;
import words.com.wordservice.main.models.enums.Language;
import words.com.wordservice.main.models.enums.LearningHistoryType;

import java.time.LocalDate;

public record LearningHistoryRespond(
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
