package words.com.wordservice.features.learninghistory.api.requests;

import jakarta.validation.constraints.Min;
import words.com.wordservice.features.shared.api.requests.LocalDateRange;

import java.util.Collection;

public record LearningHistoryFilterRequest(
        Collection<String> userIds,
        Collection<String> wordIds,
        LocalDateRange date,
        @Min(value = 0, message = "Page must be greater than or equal to 0")
        int page,
        @Min(value = 1, message = "Size must be greater than or equal to 1")
        int size
) {
}
