package words.com.wordservice.features.learninghistory.db.searches;

import lombok.Builder;
import lombok.Singular;
import words.com.wordservice.features.shared.utils.Range;

import java.time.OffsetDateTime;
import java.util.Collection;

@Builder
public record StatisticsLearningHistorySearch(
        @Singular(ignoreNullCollections = true) Collection<String> userIds,
        Range<OffsetDateTime> date
) {

}
