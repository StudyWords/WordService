package words.com.wordservice.features.learninghistory.main.models;

import lombok.Builder;

import java.util.Collection;

@Builder(toBuilder = true)
public record CountLearningHistoryFilter(
        Collection<String> userIds
) {
}
