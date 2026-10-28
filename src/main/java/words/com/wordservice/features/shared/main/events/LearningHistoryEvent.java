package words.com.wordservice.features.shared.main.events;

import words.com.wordservice.features.shared.main.enums.LearningHistoryType;

import java.time.OffsetDateTime;
import java.util.Collection;

public record LearningHistoryEvent(Collection<Entry> entries) {

    public record Entry(
            String userId,
            String wordId,
            LearningHistoryType type,
            int grade,
            OffsetDateTime createdAt
    ) {
    }
}
