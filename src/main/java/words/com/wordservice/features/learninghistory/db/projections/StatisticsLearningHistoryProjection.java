package words.com.wordservice.features.learninghistory.db.projections;

import words.com.wordservice.features.shared.main.enums.LearningHistoryType;

import java.time.LocalDate;

public interface StatisticsLearningHistoryProjection {
    int getCount();

    long getGrades();

    LearningHistoryType getType();

    LocalDate getDate();
}
