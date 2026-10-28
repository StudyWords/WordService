package words.com.wordservice.features.learninghistory.main.models;

import words.com.wordservice.features.learninghistory.main.enums.LearningHistoryType;

public record CountLearningHistory(
        int count,
        LearningHistoryType type
) {
}
