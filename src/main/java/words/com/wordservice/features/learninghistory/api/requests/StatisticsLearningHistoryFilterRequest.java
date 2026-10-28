package words.com.wordservice.features.learninghistory.api.requests;

import words.com.wordservice.features.shared.api.requests.OffsetDateTimeRange;

import java.util.Collection;

public record StatisticsLearningHistoryFilterRequest(
        Collection<String> userIds,
        OffsetDateTimeRange date
) {
}