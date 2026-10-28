package words.com.wordservice.db.projections;


import words.com.wordservice.domain.models.enums.LearningHistoryType;


public interface CountLearningHistoryProjection {
    int getCount();
    LearningHistoryType getType();
}
