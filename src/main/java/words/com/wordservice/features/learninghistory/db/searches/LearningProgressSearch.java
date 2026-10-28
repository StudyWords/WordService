package words.com.wordservice.features.learninghistory.db.searches;

import lombok.Builder;
import words.com.wordservice.features.shared.main.enums.CEFR;

import java.time.OffsetDateTime;

@Builder
public record LearningProgressSearch(
        String userId,
        CEFR cefr,
        OffsetDateTime dayStart,
        OffsetDateTime dayEnd
) {
}
