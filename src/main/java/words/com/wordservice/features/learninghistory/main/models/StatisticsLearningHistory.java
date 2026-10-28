package words.com.wordservice.features.learninghistory.main.models;

import words.com.wordservice.features.shared.main.enums.LearningHistoryType;

import java.time.LocalDate;

public record StatisticsLearningHistory(
        int count,
        long grades,
        LearningHistoryType type,
        LocalDate date
) {
}
