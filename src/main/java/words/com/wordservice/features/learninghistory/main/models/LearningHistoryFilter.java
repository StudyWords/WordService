package words.com.wordservice.features.learninghistory.main.models;

import lombok.Builder;
import words.com.wordservice.features.shared.utils.Range;

import java.time.LocalDate;
import java.util.Collection;

@Builder(toBuilder = true)
public record LearningHistoryFilter(
        Collection<String> userIds,
        Collection<String> wordIds,
        Range<LocalDate> date,
        int page,
        int size
) {
}
