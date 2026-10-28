package words.com.wordservice.features.learninghistory.db.searches;

import lombok.Builder;
import lombok.Singular;

import java.util.Collection;

@Builder
public record CountLearningHistorySearch(
        @Singular(ignoreNullCollections = true) Collection<String> userIds
) {
}
