package words.com.wordservice.features.learninghistory.api.requests;

import java.util.Collection;

public record CountLearningHistoryFilterRequest(
        Collection<String> userIds
) {
}
