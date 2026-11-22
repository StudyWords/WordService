package words.com.wordservice.features.learningplan.main.models;

import words.com.wordservice.features.learninghistory.main.models.LearningProgress;
import words.com.wordservice.features.learningstreak.main.models.LearningStreak;

public record LearningPlanDetails(
        LearningPlan plan,
        LearningProgress progress,
        LearningStreak streak
) {
}
