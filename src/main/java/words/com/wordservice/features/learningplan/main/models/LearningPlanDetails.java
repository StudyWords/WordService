package words.com.wordservice.features.learningplan.main.models;

import words.com.wordservice.features.learninghistory.main.models.LearningProgress;

public record LearningPlanDetails(
        LearningPlan plan,
        LearningProgress progress
) {
}
