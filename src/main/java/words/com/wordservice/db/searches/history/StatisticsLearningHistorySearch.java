package words.com.wordservice.db.searches.history;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.utils.Range;

import java.time.LocalDate;
import java.util.Collection;

@Builder
public record StatisticsLearningHistorySearch(
        @Singular(ignoreNullCollections = true) Collection<String> userIds,
        Range<LocalDate> date
) {

}
