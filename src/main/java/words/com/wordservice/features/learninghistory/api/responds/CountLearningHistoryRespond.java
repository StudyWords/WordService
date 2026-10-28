package words.com.wordservice.features.learninghistory.api.responds;

import words.com.wordservice.features.shared.main.enums.LearningHistoryType;

public record CountLearningHistoryRespond(
        int count,
        LearningHistoryType type
) {
}
