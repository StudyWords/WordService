package words.com.wordservice.features.learningstreak.main.services;

import words.com.wordservice.features.learninghistory.main.models.LearningProgress;
import words.com.wordservice.features.learningplan.main.models.LearningPlan;
import words.com.wordservice.features.learningstreak.main.models.LearningStreak;

import java.time.ZoneId;

public interface LearningStreakService {

    /**
     * Consecutive completed days ending yesterday, plus today if it is completed too.
     */
    LearningStreak find(String userId, ZoneId zone);

    boolean isCompletedToday(String userId, ZoneId zone);

    /**
     * Stores today as completed when the progress satisfies the plan.
     */
    void markCompletedIfDone(LearningPlan plan, LearningProgress progress, ZoneId zone);
}
