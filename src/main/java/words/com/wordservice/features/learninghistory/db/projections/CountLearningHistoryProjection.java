package words.com.wordservice.features.learninghistory.db.projections;

import words.com.wordservice.features.shared.main.enums.LearningHistoryType;

public interface CountLearningHistoryProjection {
    int getCount();
    LearningHistoryType getType();
}
