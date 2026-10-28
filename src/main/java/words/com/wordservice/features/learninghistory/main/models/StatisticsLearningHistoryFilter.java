package words.com.wordservice.features.learninghistory.main.models;

import lombok.Builder;
import words.com.wordservice.features.shared.utils.Range;

import java.time.OffsetDateTime;
import java.util.Collection;

@Builder(toBuilder = true)
public record StatisticsLearningHistoryFilter(
        Collection<String> userIds,
        Range<OffsetDateTime> date
) {
}
