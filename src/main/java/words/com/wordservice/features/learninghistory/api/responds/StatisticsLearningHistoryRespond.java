package words.com.wordservice.features.learninghistory.api.responds;

import words.com.wordservice.features.shared.main.enums.LearningHistoryType;

import java.time.LocalDate;

public record StatisticsLearningHistoryRespond(
        int count,
        long grades,
        LearningHistoryType type,
        LocalDate date
) {
}
