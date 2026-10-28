package words.com.wordservice.db.projections;


import words.com.wordservice.main.models.enums.LearningHistoryType;


public interface CountLearningHistoryProjection {
    int getCount();
    LearningHistoryType getType();
}
