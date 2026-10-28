package words.com.wordservice.api.responds.history;

import words.com.wordservice.main.models.enums.LearningHistoryType;

import java.time.LocalDate;

public record StatisticsLearningHistoryRespond(
        int count,
        long grades,
        LearningHistoryType type,
        LocalDate date
) {
}
