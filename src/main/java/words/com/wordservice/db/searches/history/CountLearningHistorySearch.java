package words.com.wordservice.db.searches.history;

import lombok.Builder;
import lombok.Singular;

import java.util.Collection;

@Builder
public record CountLearningHistorySearch(
        @Singular(ignoreNullCollections = true) Collection<String> userIds
) {
}
