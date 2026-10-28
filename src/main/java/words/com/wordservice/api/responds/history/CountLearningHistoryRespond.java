package words.com.wordservice.api.responds.history;

import words.com.wordservice.main.models.enums.LearningHistoryType;

public record CountLearningHistoryRespond(
        int count,
        LearningHistoryType type
) {
}
