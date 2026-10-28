package words.com.wordservice.features.learninghistory.db.searches;

import lombok.Builder;
import words.com.wordservice.features.shared.main.enums.CEFR;

import java.time.OffsetDateTime;
import java.util.Set;

@Builder
public record LearningProgressSearch(
        String userId,
        Set<CEFR> cefrs,
        OffsetDateTime dayStart,
        OffsetDateTime dayEnd
) {
}
