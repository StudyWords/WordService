package words.com.wordservice.db.projections;

import words.com.wordservice.main.models.enums.LearningHistoryType;

import java.time.LocalDate;

public interface StatisticsLearningHistoryProjection {
    int getCount();

    long getGrades();

    LearningHistoryType getType();

    LocalDate getDate();
}
