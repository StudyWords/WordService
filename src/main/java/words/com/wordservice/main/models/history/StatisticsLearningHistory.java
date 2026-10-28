package words.com.wordservice.main.models.history;


import words.com.wordservice.main.models.enums.LearningHistoryType;

import java.time.LocalDate;

public record StatisticsLearningHistory(
        int count,
        long grades,
        LearningHistoryType type,
        LocalDate date
) {
}
