package words.com.wordservice.main.models.history;


import words.com.wordservice.main.models.enums.LearningHistoryType;

public record CountLearningHistory(
        int count,
        LearningHistoryType type
) {
}
